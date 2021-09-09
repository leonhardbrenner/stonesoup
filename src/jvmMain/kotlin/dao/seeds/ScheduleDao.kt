package dao.seeds

import generated.model.Seeds
import generated.model.db.SeedsDb
import org.jetbrains.exposed.sql.deleteWhere
import org.jetbrains.exposed.sql.insertAndGetId
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.update

object ScheduleDao {

    fun index() =
        SeedsDb.Schedule.Table.selectAll().map {
            SeedsDb.Schedule.select(it)
        }

    fun create(source: Seeds.Schedule) =
        SeedsDb.Schedule.Table.insertAndGetId {
            SeedsDb.Schedule.insert(it, source)
        }.value

    fun update(source: Seeds.Schedule) {
        SeedsDb.Schedule.Table.update({ SeedsDb.Chore.Table.id.eq(source.id) }) {
            SeedsDb.Schedule.update(it, source)
        }
    }

    fun destroy(id: Int) {
        SeedsDb.Schedule.Table.deleteWhere { SeedsDb.Chore.Table.id eq id }
    }
}