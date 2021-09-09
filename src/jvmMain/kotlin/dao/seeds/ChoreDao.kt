package dao.seeds

import generated.model.Seeds
import generated.model.db.SeedsDb
import org.jetbrains.exposed.sql.*

object ChoreDao {

    fun index() =
        SeedsDb.Chore.Table.selectAll().map {
            SeedsDb.Chore.select(it)
        }

    fun get(id: Int) =
        SeedsDb.Chore.Table.select { SeedsDb.Chore.Table.id.eq(id) }.map {
            SeedsDb.Chore.select(it)
        }.last()

    fun create(source: Seeds.Chore): Int =
        SeedsDb.Chore.Table.insertAndGetId {
            SeedsDb.Chore.insert(it, source)
        }.value


    fun update(source: Seeds.Chore) =
        SeedsDb.Chore.Table.update({ SeedsDb.Chore.Table.id.eq(source.id) }) {
            SeedsDb.Chore.update(it, source)
        }

    fun destroy(id: Int) =
        SeedsDb.Chore.Table.deleteWhere { SeedsDb.Chore.Table.id eq id }

}