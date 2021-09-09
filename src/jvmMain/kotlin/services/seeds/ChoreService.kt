package services.seeds

import dao.SeedsDao
import dao.seeds.ChoreDao
import generated.model.Seeds
import generated.model.SeedsDto
import models.SeedsResources
import javax.inject.Inject

class ChoreService @Inject constructor(val dao: SeedsDao) {

    fun index() = let {
        val chores = dao.chore.index()
        val schedules = dao.schedule.index().associateBy { it.choreId }
        chores.map { SeedsResources.Chore(it, schedules[it.id]) }
    }

    fun create(source: Seeds.Chore) {
        val id = dao.chore.create(
            SeedsDto.Chore(-1, source.parentId, "", source.name)
        )
        dao.chore.update(
            with (dao.chore) {
                get(source.parentId).let {
                    val newChildrenIds = (it.childrenIds.split(",") + id.toString())
                        .joinToString(",")
                    it.copy(childrenIds = newChildrenIds)

                }
            }
        )
    }

    fun move(id: Int, parentId: Int) {
        val node = ChoreDao.get(id)
        if (parentId != node.parentId) {
            //Remove item from old list
            with (dao.chore) {
                update(
                    get(node.parentId).let {
                        val updatedChildrenIds = (it.childrenIds.split(",") - id.toString())
                        it.copy(childrenIds = updatedChildrenIds.joinToString(","))
                    }
                )

                update(
                    get(parentId).let {
                        val updatedChildrenIds = (it.childrenIds.split(",") + id.toString())
                        it.copy(childrenIds = updatedChildrenIds.joinToString(","))
                    }
                )

                update(node.copy(parentId = parentId))
            }
        }
    }

    fun destroy(id: Int) {
        val parentId = ChoreDao.get(id).parentId
        val parent = ChoreDao.get(parentId)
        with (dao.chore) {
            update(parent.copy(childrenIds = (parent.childrenIds.split(",") - id.toString()).joinToString(",")))
            destroy(id)
        }
    }
}