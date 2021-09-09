package dao.seeds

import generated.model.Seeds
import generated.model.db.SeedsDb
import org.jetbrains.exposed.sql.deleteWhere
import org.jetbrains.exposed.sql.insertAndGetId
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.transactions.transaction
import org.jetbrains.exposed.sql.update

object ScheduleDao {

    fun index() = transaction {
        SeedsDb.Schedule.Table.selectAll().map {
            SeedsDb.Schedule.create(it)
        }
    }

    fun create(source: Seeds.Schedule): Int {
        var id = -1
        transaction {
            id = SeedsDb.Schedule.Table.insertAndGetId {
                SeedsDb.Schedule.insert(it, source)
            }.value
        }
        return id
    }

    fun update(source: Seeds.Schedule) {

        transaction {
            SeedsDb.Schedule.Table.update({ SeedsDb.Chore.Table.id.eq(source.id) }) {
                SeedsDb.Schedule.update(it, source)
            }
        }
    }

    fun destroy(id: Int) {
        transaction {
            SeedsDb.Schedule.Table.deleteWhere { SeedsDb.Chore.Table.id eq id }
        }
    }
}