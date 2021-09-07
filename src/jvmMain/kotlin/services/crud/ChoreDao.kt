package services.crud

import generated.model.Seeds
import generated.model.SeedsDto
import generated.model.db.SeedsDb
import models.Resources
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.transaction
import utils.then

/*
 Todo - generate the unique foreign key in SeedsDb.kt. It should look something like this:
    object Table : IntIdTable("Schedule") {
      val choreId: Column<Int> = integer("choreId")
        .uniqueIndex()
        .references(Chore.Table.id)
      val workHours: Column<String?> = text("workHours").nullable()
      val completeBy: Column<String?> = text("completeBy").nullable()
    }
  When complete we can remove additional constraints. Note the primary reason for proposed change
  is to create our index.
*/
object ChoreDao {

    fun index() = transaction {
        //Nice exposed example:
        //https://github.com/JetBrains/Exposed/issues/566
        with(
            SeedsDb.Chore.Table.join(
                SeedsDb.Schedule.Table,
                JoinType.LEFT,
                additionalConstraint = {
                    SeedsDb.Chore.Table.id eq SeedsDb.Schedule.Table.choreId
                }
            )
        ) {
            selectAll().map {
                val schedule = if (it[SeedsDb.Schedule.Table.id]!=null)
                    SeedsDb.Schedule.create(it)
                else
                    null
                SeedsDb.Chore.create(it).copy(schedule = schedule)
            }
        }
    }

    fun create(
        source: Seeds.Chore
    ): Int {
        var id = -1
        transaction {
            id = SeedsDb.Chore.Table.insertAndGetId {
                SeedsDb.Chore.insert(it, source)
            }.value
            val childrenIds = SeedsDb.Chore.Table.select {
                SeedsDb.Chore.Table.id.eq(source.parentId)
            }.single()[SeedsDb.Chore.Table.childrenIds]
            SeedsDb.Chore.Table.update({ SeedsDb.Chore.Table.id.eq(source.parentId) }) {
                it[SeedsDb.Chore.Table.childrenIds] = (childrenIds.split(",") + id.toString()).joinToString(",")
            }
        }
        return id
    }

    /**
     * Todo
     *   the node id should then be used for our update
     *   let's start with move then do link
     *   display on the front end more like a graph
     *   create a link routine something as simple as little x(es) that connect
     *   make a priority widget something like a +/-
     *   make a box for real description
     *   we need a field for time estimates
     *   move to tornadoFx
     */
    fun update(id: Int, parentId: Int?, name: String?) {

        transaction {

            //Remove node from old parent children list
            val (oldParentId, oldName) = SeedsDb.Chore.Table
                .slice(SeedsDb.Chore.Table.parentId, SeedsDb.Chore.Table.name)
                .select {
                    SeedsDb.Chore.Table.id.eq(id)
                }.single().let {
                    it[SeedsDb.Chore.Table.parentId] then it[SeedsDb.Chore.Table.name]
                }

            parentId?.let { newParentId ->
                if ((newParentId != null) and (newParentId != oldParentId)) {
                    val oldChildrenIds = SeedsDb.Chore.Table.select {
                        SeedsDb.Chore.Table.id.eq(oldParentId)
                    }.single()[SeedsDb.Chore.Table.childrenIds]

                    val newChildrenIds = SeedsDb.Chore.Table.select {
                        SeedsDb.Chore.Table.id.eq(newParentId)
                    }.single()[SeedsDb.Chore.Table.childrenIds]

                    //Remove item from old list
                    SeedsDb.Chore.Table.update({ SeedsDb.Chore.Table.id.eq(oldParentId) }) {
                        val oldChildrenIdsRewritten = (oldChildrenIds.split(",") - id.toString()).joinToString(",")
                        it[childrenIds] = oldChildrenIdsRewritten
                    }

                    SeedsDb.Chore.Table.update({ SeedsDb.Chore.Table.id.eq(newParentId) }) {
                        it[childrenIds] =
                            (newChildrenIds.split(",") + id.toString()).joinToString(",")
                    }

                    SeedsDb.Chore.Table.update({ SeedsDb.Chore.Table.id.eq(id) }) {
                        it[SeedsDb.Chore.Table.parentId] = newParentId
                    }
                }
            }
        }
    }

    fun destroy(id: Int) {
        transaction {
            val parentId = SeedsDb.Chore.Table.select {
                SeedsDb.Chore.Table.id.eq(id)
            }.single()[SeedsDb.Chore.Table.parentId]
            val childrenIds = SeedsDb.Chore.Table.select {
                SeedsDb.Chore.Table.id.eq(parentId)
            }.single()[SeedsDb.Chore.Table.childrenIds]
            SeedsDb.Chore.Table.update({ SeedsDb.Chore.Table.id.eq(parentId) }) {
                val newChildrenId = (childrenIds.split(",") - id.toString()).joinToString(",")
                it[SeedsDb.Chore.Table.childrenIds] = newChildrenId
            }
            SeedsDb.Chore.Table.deleteWhere { SeedsDb.Chore.Table.id eq id }
        }
    }
}
