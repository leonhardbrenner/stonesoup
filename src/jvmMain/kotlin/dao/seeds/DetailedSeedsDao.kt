package dao.seeds

import generated.model.Seeds
import generated.model.db.SeedsDb
import org.jetbrains.exposed.sql.*

object DetailedSeedsDao {

    fun index() =
        SeedsDb.DetailedSeed.Table.selectAll().map {
            SeedsDb.DetailedSeed.select(it)
        }

    fun get(id: Int) =
        SeedsDb.DetailedSeed.Table.select { SeedsDb.DetailedSeed.Table.id.eq(id) }.map {
            SeedsDb.DetailedSeed.select(it)
        }.last()

    fun create(source: Seeds.DetailedSeed): Int =
        SeedsDb.DetailedSeed.Table.insertAndGetId {
            SeedsDb.DetailedSeed.insert(it, source)
        }.value

    fun update(id: Int, source: Seeds.DetailedSeed) =
        SeedsDb.Chore.Table.update({ SeedsDb.Chore.Table.id.eq(id) }) {
            SeedsDb.DetailedSeed.update(it, source)
        }

    fun destroy(id: Int) =
        SeedsDb.DetailedSeed.Table.deleteWhere { SeedsDb.Chore.Table.id eq id }

}