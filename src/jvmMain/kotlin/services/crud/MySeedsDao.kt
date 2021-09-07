package services.crud

import generated.model.Seeds
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
                    schedule
                )
            }
            x
        }
    }

    fun create(source: Seeds.MySeeds): Int = transaction {
        SeedsDb.Chore.Table.insertAndGetId {
            SeedsDb.MySeeds.insert(it, source)
        }.value
    }

    fun update(source: Seeds.MySeeds) = transaction {
        SeedsDb.Chore.Table.update({ SeedsDb.Chore.Table.id.eq(source.id) }) {
            SeedsDb.MySeeds.update(it, source)
        }
    }

    fun destroy(id: Int) = transaction {
        SeedsDb.MySeeds.Table.deleteWhere { SeedsDb.Chore.Table.id eq id }
    }
}