package dao.seeds

import generated.model.Seeds
import generated.model.db.SeedsDb
import models.SeedsResources
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.transaction

object MySeedsDao {

    fun index() = transaction {
        SeedsDb.MySeeds.Table.selectAll().map {
            SeedsDb.MySeeds.select(it)
        }
    }

    fun get(id: Int) =
        SeedsDb.MySeeds.Table.select { SeedsDb.MySeeds.Table.id.eq(id) }.map {
            SeedsDb.MySeeds.select(it)
        }.last()

    fun create(source: Seeds.MySeeds): Int =
        SeedsDb.MySeeds.Table.insertAndGetId {
            SeedsDb.MySeeds.insert(it, source)
        }.value


    fun update(source: Seeds.MySeeds) =
        SeedsDb.MySeeds.Table.update({ SeedsDb.MySeeds.Table.id.eq(source.id) }) {
            SeedsDb.MySeeds.update(it, source)
        }

    fun destroy(id: Int) =
        SeedsDb.MySeeds.Table.deleteWhere { SeedsDb.MySeeds.Table.id eq id }
}