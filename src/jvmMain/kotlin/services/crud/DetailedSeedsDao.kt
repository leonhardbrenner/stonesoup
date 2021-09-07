package services.crud

import generated.model.Seeds
import generated.model.SeedsDto
import generated.model.db.SeedsDb
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.transaction

object DetailedSeedsDao {

    fun index() = transaction {
        SeedsDb.DetailedSeed.Table.selectAll().map {
            SeedsDb.DetailedSeed.create(it)
        }
    }

    fun create(source: Seeds.DetailedSeed): Int {
        var id = -1
        transaction {
            id = SeedsDb.DetailedSeed.Table.insertAndGetId {
                SeedsDb.DetailedSeed.insert(it, source)
            }.value
        }
        return id
    }

    fun update(id: Int, source: Seeds.DetailedSeed) {
        transaction {
            SeedsDb.Chore.Table.update({ SeedsDb.Chore.Table.id.eq(id) }) {
                SeedsDb.DetailedSeed.update(it, source)
            }
        }
    }

    fun destroy(id: Int) {
        transaction {
            SeedsDb.DetailedSeed.Table.deleteWhere { SeedsDb.Chore.Table.id eq id }
        }
    }
}
