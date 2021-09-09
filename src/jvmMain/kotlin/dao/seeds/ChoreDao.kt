package dao.seeds

import generated.model.Seeds
import generated.model.db.SeedsDb
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.transaction

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
//Move toward injection
object ChoreDao {


    fun index() = transaction {
        SeedsDb.Chore.Table.selectAll().map {
            SeedsDb.Chore.create(it)
        }
    }

    fun get(id: Int) = transaction {
        SeedsDb.Chore.Table.select { SeedsDb.Chore.Table.id.eq(id) }.map {
            SeedsDb.Chore.create(it)
        }.last()
    }


    fun create(
        source: Seeds.Chore
    ): Int {
        var id = -1
        transaction {
            id = SeedsDb.Chore.Table.insertAndGetId {
                SeedsDb.Chore.insert(it, source)
            }.value
            val parent = get(source.parentId)
            val newChildrenIds = (parent.childrenIds.split(",") + id.toString())
                .joinToString(",")
            SeedsDb.Chore.Table.update({ SeedsDb.Chore.Table.id.eq(source.parentId) }) {
                SeedsDb.Chore.update(it, parent.copy(childrenIds = newChildrenIds))
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
    fun update(id: Int, newParentId: Int?, name: String?) {

        transaction {

            val node = get(id)

            if ((newParentId != null) and (newParentId != node.parentId)) {
                //Remove item from old list
                SeedsDb.Chore.Table.update({ SeedsDb.Chore.Table.id.eq(node.parentId) }) {
                    SeedsDb.Chore.update(it, get(node.parentId).let {
                        it.copy(
                            childrenIds = (it.childrenIds.split(",") - id.toString())
                                .joinToString(",")
                        )
                    })
                }

                SeedsDb.Chore.Table.update({ SeedsDb.Chore.Table.id.eq(newParentId) }) {
                    SeedsDb.Chore.update(it, get(newParentId!!).let {
                        it.copy(
                            childrenIds = (it.childrenIds.split(",") + id.toString())
                                .joinToString(",")
                        )
                    })
                }

                SeedsDb.Chore.Table.update({ SeedsDb.Chore.Table.id.eq(id) }) {
                    SeedsDb.Chore.update(it, node.copy(parentId = newParentId!!))
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