package dao.seeds

import generated.model.Seeds
import generated.model.db.SeedsDb
import models.Resources
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.transaction

object MySeedsDao {

    //Todo - move this to FE. I will make use of a result set and decorators objects which will pull the remaining xpath
    fun expandedIndex() = transaction {
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

    fun index() = transaction {
        SeedsDb.SeedCategory.Table.selectAll().map {
            SeedsDb.MySeeds.create(it)
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