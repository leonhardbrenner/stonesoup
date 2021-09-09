package dao.seeds

import generated.model.Seeds
import generated.model.db.SeedsDb
import org.jetbrains.exposed.sql.deleteWhere
import org.jetbrains.exposed.sql.insertAndGetId
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.transactions.transaction
import org.jetbrains.exposed.sql.update

object SeedCategoryDao {

    fun index() = transaction {
        SeedsDb.SeedCategory.Table.selectAll().map {
            SeedsDb.SeedCategory.create(it)
        }
    }

    fun create(source: Seeds.SeedCategory): Int {
        var id = -1
        transaction {
            id = SeedsDb.SeedCategory.Table.insertAndGetId {
                SeedsDb.SeedCategory.insert(it, source)
            }.value
        }
        return id
    }

    fun update(source: Seeds.SeedCategory) {

        transaction {
            SeedsDb.SeedCategory.Table.update({ SeedsDb.Chore.Table.id.eq(source.id) }) {
                SeedsDb.SeedCategory.update(it, source)
            }
        }
    }

    fun destroy(id: Int) {
        transaction {
            SeedsDb.SeedCategory.Table.deleteWhere { SeedsDb.Chore.Table.id eq id }
        }
    }
}