package services.crud

import generated.model.SeedsDto
import generated.model.db.SeedsDb
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.transaction
import utils.then

object SeedCategoryDao {

    fun index() = //transaction {
        SeedsDb.SeedCategory.fetchAll()
    //SeedsDb.SeedCategory.Table.selectAll().map {
    //    SeedsDto.SeedCategory(
    //        it[SeedsDb.SeedCategory.Table.id].value,
    //        it[SeedsDb.SeedCategory.Table.name],
    //        it[SeedsDb.SeedCategory.Table.maturity],
    //        it[SeedsDb.SeedCategory.Table.secondary_name],
    //        it[SeedsDb.SeedCategory.Table.description],
    //        it[SeedsDb.SeedCategory.Table.image],
    //        it[SeedsDb.SeedCategory.Table.link]
    //    )
    //}
    //}

    fun create(attrName: String,
               attrImage: String,
               attrLink: String

    ): Int {
        var id = -1
        transaction {
            id = SeedsDb.Chore.Table.insertAndGetId {
                it[SeedsDb.SeedCategory.Table.name] = attrName
                it[SeedsDb.SeedCategory.Table.image] = attrImage
                it[SeedsDb.SeedCategory.Table.link] = attrLink
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
    fun update(id: Int, name: String, image: String, link: String) {

        transaction {
            SeedsDb.Chore.Table.update({ SeedsDb.Chore.Table.id.eq(id) }) {
                it[SeedsDb.SeedCategory.Table.name] = name
                it[SeedsDb.SeedCategory.Table.image] = image
                it[SeedsDb.SeedCategory.Table.link] = link
            }
        }
    }

    fun destroy(id: Int) {
        transaction {
            SeedsDb.SeedCategory.Table.deleteWhere { SeedsDb.Chore.Table.id eq id }
        }
    }
}

