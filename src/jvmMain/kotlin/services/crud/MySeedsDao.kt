package services.crud

import generated.model.db.SeedsDb
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.transaction

object MySeedsDao {

    //XXX - This is incomplete and needs to look like SeedsService
    /*
    val mySeeds get() = SeedsDb.MySeeds.fetchAll().map {
        Resources.MySeeds(
            it,
            detailedSeedMap[it.seed_label.split(":").last().split(".").first()]
        )
    }

    fun lookupMySeed(pattern: String) = mySeeds.filter { it.description.contains(pattern) }

    val detailedSeedMap = getDetailedSeeds().map {
        val trimmedLink = it.link!!.substring(0, it.link!!.indexOf(".html?"))
        trimmedLink.substring(trimmedLink.lastIndexOf("-") + 1) to it
    }.toMap()
     */
    fun index() = //transaction {
        SeedsDb.MySeeds.fetchAll()
    //SeedsDb.MySeeds.Table.selectAll().map {
    //    SeedsDto.MySeeds(
    //        it[SeedsDb.MySeeds.Table.id].value,
    //        it[SeedsDb.MySeeds.Table.name],
    //        it[SeedsDb.MySeeds.Table.maturity],
    //        it[SeedsDb.MySeeds.Table.secondary_name],
    //        it[SeedsDb.MySeeds.Table.description],
    //        it[SeedsDb.MySeeds.Table.image],
    //        it[SeedsDb.MySeeds.Table.link]
    //    )
    //}
    //}

    fun create(
        attrSeedLabel: String,
        attrDescription: String,
        attrGerminationTest: String

    ): Int {
        var id = -1
        transaction {
            id = SeedsDb.Chore.Table.insertAndGetId {
                it[SeedsDb.MySeeds.Table.seed_label] = attrSeedLabel
                it[SeedsDb.MySeeds.Table.description] = attrDescription
                it[SeedsDb.MySeeds.Table.germination_test] = attrGerminationTest
            }.value
        }
        return id
    }

    /**
     * Todo
     *   the node id should then be used for our update
     *   let's start with move then do link
     *   display on the front end more like a graph
     *   create a link routine something as simple as little x(es) that connect
     *   make a priority widget something like a +/-
     *   make a box for real description
     *   we need a field for time estimates
     *   move to tornadoFx
     */
    fun update(
        id: Int,
        attrSeedLabel: String,
        attrDescription: String,
        attrGerminationTest: String
    ) {
       transaction {
            SeedsDb.Chore.Table.update({ SeedsDb.Chore.Table.id.eq(id) }) {
                it[SeedsDb.MySeeds.Table.seed_label] = attrSeedLabel
                it[SeedsDb.MySeeds.Table.description] = attrDescription
                it[SeedsDb.MySeeds.Table.germination_test] = attrGerminationTest
            }
        }
    }

    fun destroy(id: Int) {
        transaction {
            SeedsDb.MySeeds.Table.deleteWhere { SeedsDb.Chore.Table.id eq id }
        }
    }
}