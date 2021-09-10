package services.seeds

import dao.SeedsDao
import generated.model.Seeds
import models.SeedsResources
import javax.inject.Inject

class ChoreService @Inject constructor(val dao: SeedsDao) {

    fun index() = let {
        val chores = dao.chore.index()
        val childrenIds = chores
            .map { it.parentId to it.id }
            .groupBy({ it.first }, { it.second })
        val schedules = dao.schedule.index().associateBy { it.choreId }
        chores.map {
            SeedsResources.Chore(
                it,
                childIds = childrenIds[it.id]?: emptyList(),
                schedule = schedules[it.id]) }
    }

    fun create(source: Seeds.Chore) = dao.chore.create(source)

    fun update(source: Seeds.Chore) = dao.chore.update(source)

    fun move(id: Int, parentId: Int) = update(dao.chore.get(id).copy(parentId = parentId))

    fun destroy(id: Int) = dao.chore.destroy(id)
}
