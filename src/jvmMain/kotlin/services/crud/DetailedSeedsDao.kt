package services.crud

import generated.model.SeedsDto
import generated.model.db.SeedsDb
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.transaction
import utils.then

object DetailedSeedsDao {

    fun index() = //transaction {
        SeedsDb.DetailedSeed.fetchAll()
        //SeedsDb.DetailedSeed.Table.selectAll().map {
        //    SeedsDto.DetailedSeed(
        //        it[SeedsDb.DetailedSeed.Table.id].value,
        //        it[SeedsDb.DetailedSeed.Table.name],
        //        it[SeedsDb.DetailedSeed.Table.maturity],
        //        it[SeedsDb.DetailedSeed.Table.secondary_name],
        //        it[SeedsDb.DetailedSeed.Table.description],
        //        it[SeedsDb.DetailedSeed.Table.image],
        //        it[SeedsDb.DetailedSeed.Table.link]
        //    )
        //}
    //}

    fun create(attrName: String,
               attrMaturity: String,
               attrSecondaryName: String,
               attrDescription: String,
               attrImage: String,
               attrLink: String

    ): Int {
        var id = -1
        transaction {
            id = SeedsDb.Chore.Table.insertAndGetId {
                it[SeedsDb.DetailedSeed.Table.name] = attrName
                it[SeedsDb.DetailedSeed.Table.maturity] = attrMaturity
                it[SeedsDb.DetailedSeed.Table.secondary_name] = attrSecondaryName
                it[SeedsDb.DetailedSeed.Table.description] = attrDescription
                it[SeedsDb.DetailedSeed.Table.image] = attrImage
                it[SeedsDb.DetailedSeed.Table.link] = attrLink
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
    fun update(id: Int, name: String, maturity: String?, secondary_name: String?, description: String?, image: String?, link: String?) {

        transaction {
            SeedsDb.Chore.Table.update({ SeedsDb.Chore.Table.id.eq(id) }) {
                it[SeedsDb.DetailedSeed.Table.name] = name
                it[SeedsDb.DetailedSeed.Table.maturity] = maturity
                it[SeedsDb.DetailedSeed.Table.secondary_name] = secondary_name
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
