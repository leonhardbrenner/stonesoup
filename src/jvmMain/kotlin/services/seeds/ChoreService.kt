package services.seeds

import dao.SeedsDao
import generated.model.Seeds
import generated.model.SeedsDto
import models.SeedsResources
import javax.inject.Inject

class ChoreService @Inject constructor(val dao: SeedsDao) {
    fun index() = let {
        val chores = dao.Chore.index()
        val schedules = dao.Schedule.index().associateBy { it.choreId }
        chores.map { SeedsResources.Chore(it, schedules[it.id]) }
    }
    fun create(source: Seeds.Chore) {
        val id = dao.Chore.create(
            SeedsDto.Chore(-1, source.parentId, "", source.name)
        )
        dao.Chore.update(
            dao.Chore.get(source.parentId).let {
                val newChildrenIds = (it.childrenIds.split(",") + id.toString())
                    .joinToString(",")
                it.copy(childrenIds = newChildrenIds)

            }
        )
    }
}