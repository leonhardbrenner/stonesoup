package services.crud

import generated.model.db.SeedsDb
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.transaction

object DetailedSeedsDao {

    fun index() = transaction {
        SeedsDb.DetailedSeed.Table.selectAll().map {
            SeedsDb.DetailedSeed.create(it)
        }
    }

    fun create(companyId: String,
               seedId: String,
               name: String,
               maturity: String,
               secondaryName: String,
               description: String,
               image: String,
               link: String

    ): Int {
        var id = -1
        transaction {
            id = SeedsDb.Chore.Table.insertAndGetId {
                SeedsDb.DetailedSeed.insert(
                    it, companyId, seedId, name, maturity, secondaryName, description, image, link)
            }.value
        }
        return id
    }

    fun update(id: Int, companyId: String,
               seedId: String,
               name: String,
               maturity: String?,
               secondaryName: String?,
               description: String?,
               image: String?,
               link: String?) {

        transaction {
            SeedsDb.Chore.Table.update({ SeedsDb.Chore.Table.id.eq(id) }) {
                SeedsDb.DetailedSeed.update(
                    it, companyId, seedId, name, maturity, secondaryName, description, image, link)
            }
        }
    }

    fun destroy(id: Int) {
        transaction {
            SeedsDb.DetailedSeed.Table.deleteWhere { SeedsDb.Chore.Table.id eq id }
        }
    }
}
