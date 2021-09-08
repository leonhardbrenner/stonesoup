package dao.seeds

import generated.model.Seeds
import generated.model.db.SeedsDb
import org.jetbrains.exposed.sql.deleteWhere
import org.jetbrains.exposed.sql.insertAndGetId
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.transactions.transaction
import org.jetbrains.exposed.sql.update

object DetailedSeedsDao {

    fun index() = transaction {
        SeedsDb.DetailedSeed.Table.selectAll().map {
            SeedsDb.DetailedSeed.create(it)
        }
    }

    fun create(source: Seeds.DetailedSeed): Int = transaction {
        SeedsDb.DetailedSeed.Table.insertAndGetId {
            SeedsDb.DetailedSeed.insert(it, source)
        }.value
    }

    fun update(id: Int, source: Seeds.DetailedSeed) = transaction {
        SeedsDb.Chore.Table.update({ SeedsDb.Chore.Table.id.eq(id) }) {
            SeedsDb.DetailedSeed.update(it, source)
        }
    }

    fun destroy(id: Int) = transaction {
        SeedsDb.DetailedSeed.Table.deleteWhere { SeedsDb.Chore.Table.id eq id }
    }

}