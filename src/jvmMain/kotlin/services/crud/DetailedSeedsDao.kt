package services.crud

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
                it[SeedsDb.DetailedSeed.Table.companyId] = companyId
                it[SeedsDb.DetailedSeed.Table.seedId] = seedId
                it[SeedsDb.DetailedSeed.Table.name] = name
                it[SeedsDb.DetailedSeed.Table.maturity] = maturity
                it[SeedsDb.DetailedSeed.Table.secondaryName] = secondaryName
                it[SeedsDb.DetailedSeed.Table.description] = description
                it[SeedsDb.DetailedSeed.Table.image] = image
                it[SeedsDb.DetailedSeed.Table.link] = link
            }.value
        }
        return id
    }

    fun update(id: Int, companyId: String,
               seedId: String,
               name: String,
               maturity: String?,
               secondary_name: String?,
               description: String?,
               image: String?,
               link: String?) {

        transaction {
            SeedsDb.Chore.Table.update({ SeedsDb.Chore.Table.id.eq(id) }) {
                it[SeedsDb.DetailedSeed.Table.companyId] = companyId
                it[SeedsDb.DetailedSeed.Table.seedId] = seedId
                it[SeedsDb.DetailedSeed.Table.name] = name
                it[SeedsDb.DetailedSeed.Table.maturity] = maturity
                it[SeedsDb.DetailedSeed.Table.secondaryName] = secondary_name
                it[SeedsDb.DetailedSeed.Table.description] = description
                it[SeedsDb.DetailedSeed.Table.image] = image
                it[SeedsDb.DetailedSeed.Table.link] = link
            }
        }
    }

    fun destroy(id: Int) {
        transaction {
            SeedsDb.DetailedSeed.Table.deleteWhere { SeedsDb.Chore.Table.id eq id }
        }
    }
}
