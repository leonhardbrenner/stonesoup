package services.seeds

import generated.dao.SeedsDao
import generated.model.Seeds
import models.SeedsResources
import javax.inject.Inject

class ChoreService @Inject constructor(val choreDao: SeedsDao.Chore, val scheduleDao: SeedsDao.Schedule) {

    fun index() = let {
        val chores = choreDao.index()
        val childrenIds = chores
            .map { it.parentId to it.id }
            .groupBy({ it.first }, { it.second })
        val schedules = scheduleDao.index().associateBy { it.choreId }
        chores.map {
            SeedsResources.Chore(
                it,
                childIds = childrenIds[it.id]?: emptyList(),
                schedule = schedules[it.id]) }
    }

    fun create(source: Seeds.Chore) = choreDao.create(source)

    fun update(source: Seeds.Chore) = choreDao.update(source)

    fun move(id: Int, parentId: Int) = update(choreDao.get(id).copy(parentId = parentId))

    fun destroy(id: Int) = choreDao.destroy(id)
}
