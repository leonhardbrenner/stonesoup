package services.crud

import generated.model.SeedsDto
import generated.model.db.SeedsDb
import models.Resources
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.transaction

object MySeedsDao {

    //XXX - This is incomplete and needs to look like SeedsService
    //fun index() = //transaction {
    //    SeedsDb.MySeeds.fetchAll()
    fun index() = transaction {
        //Nice exposed example:
        //https://github.com/JetBrains/Exposed/issues/566
        with(
            SeedsDb.MySeeds.Table.join(
                SeedsDb.DetailedSeed.Table,
                JoinType.LEFT,
                additionalConstraint = { SeedsDb.MySeeds.Table.seedId eq SeedsDb.DetailedSeed.Table.seedId }
            )
        ) {
            val x = selectAll().map {
                val schedule = if (it[SeedsDb.DetailedSeed.Table.id] != null)
                    SeedsDb.DetailedSeed.create(it)
                else
                    null
                Resources.MySeeds(
                    SeedsDb.MySeeds.create(it),
                    //XXX - This needs to be made to work. Currently, I am getting data. Time to think about boundaries.
                    schedule
                )
            }
            x
        }
    }

    fun create(
        companyId: String,
        seedId: String,
        description: String,
        germinationTest: String
    ): Int {
        var id = -1
        transaction {
            id = SeedsDb.Chore.Table.insertAndGetId {
                it[SeedsDb.MySeeds.Table.companyId] = companyId
                it[SeedsDb.MySeeds.Table.seedId] = seedId
                it[SeedsDb.MySeeds.Table.description] = description
                it[SeedsDb.MySeeds.Table.germinationTest] = germinationTest
            }.value
        }
        return id
    }

    fun update(
        id: Int,
        companyId: String,
        seedId: String,
        description: String,
        germinationTest: String
    ) {
       transaction {
            SeedsDb.Chore.Table.update({ SeedsDb.Chore.Table.id.eq(id) }) {
                it[SeedsDb.MySeeds.Table.companyId] = companyId
                it[SeedsDb.MySeeds.Table.seedId] = seedId
                it[SeedsDb.MySeeds.Table.description] = description
                it[SeedsDb.MySeeds.Table.germinationTest] = germinationTest
            }
        }
    }

    fun destroy(id: Int) {
        transaction {
            SeedsDb.MySeeds.Table.deleteWhere { SeedsDb.Chore.Table.id eq id }
        }
    }
}