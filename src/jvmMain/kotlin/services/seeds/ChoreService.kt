package services.seeds

import dao.SeedsDao
import dao.seeds.ChoreDao
import generated.model.Seeds
import generated.model.SeedsDto
import models.SeedsResources
import javax.inject.Inject

class ChoreService @Inject constructor(val dao: SeedsDao) {

    fun index() = let {
        val chores = dao.Chore.index()
        val childrenIds = chores
            .map { it.parentId to it.id }
            .groupBy({ it.first }, { it.second })
        val schedules = dao.Schedule.index().associateBy { it.choreId }
        chores.map { SeedsResources.Chore(it, childrenIds[it.id]?: emptyList(), schedules[it.id]) }
    }

    fun create(source: Seeds.Chore) = dao.Chore.create(source)

    fun update(source: Seeds.Chore) = dao.Chore.update(source)

    fun move(id: Int, parentId: Int) = update(dao.Chore.get(id).copy(parentId = parentId))

    fun destroy(id: Int) = dao.Chore.destroy(id)
}
