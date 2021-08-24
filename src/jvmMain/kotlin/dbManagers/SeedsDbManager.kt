package dbManagers

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.KotlinModule
import generated.model.db.SeedsDb
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.transactions.transaction
import jsonLoaders.SeedsJsonLoaders
import java.io.File

fun resource(path: String) = File(ClassLoader.getSystemResource(path).file)
fun resourceText(path: String) = resource(path).readText()

object SeedsDBManager {
    val kMapper = ObjectMapper().registerModule(KotlinModule())

    fun drop() = transaction {
        SchemaUtils.drop(SeedsDb.MySeeds.Table)
        SchemaUtils.drop(SeedsDb.DetailedSeed.Table)
        SchemaUtils.drop(SeedsDb.SeedCategory.Table)
        SchemaUtils.drop(SeedsDb.Chore.Table)
        SchemaUtils.drop(SeedsDb.Schedule.Table)
    }
    fun create() = transaction {
        SchemaUtils.create(SeedsDb.MySeeds.Table)
        SchemaUtils.create(SeedsDb.DetailedSeed.Table)
        SchemaUtils.create(SeedsDb.SeedCategory.Table)
        SchemaUtils.create(SeedsDb.Chore.Table)
        SchemaUtils.create(SeedsDb.Schedule.Table)
    }
    fun populate() = transaction {
        val jsonLoaders = SeedsJsonLoaders(kMapper)
        jsonLoaders.mySeeds.forEach { source ->
            SeedsDb.MySeeds.Entity.new {
                seed_label = source.seed_label
                description = source.description
                germination_test = source.germination_test
            }
        }
        jsonLoaders.detailedSeeds.forEach { source ->
            SeedsDb.DetailedSeed.Entity.new {
                name = source.name
                maturity = source.maturity
                secondary_name = source.secondary_name
                description = source.description
                image = source.image
                link = source.link
            }
        }
        jsonLoaders.categories.forEach { source ->
            SeedsDb.SeedCategory.Entity.new {
                name = source.name
                image = source.image
                link = source.link
            }
        }
        jsonLoaders.chores.forEach { source ->
            SeedsDb.Chore.Entity.new {
                parentId = source.parentId
                childrenIds = source.childrenIds
                name = source.name
            }
        }
        jsonLoaders.schedules.forEach { source ->
            SeedsDb.Schedule.Entity.new {
                choreId = source.choreId
                workHours = source.workHours
                completeBy = source.completeBy
            }
        }
    }
}
