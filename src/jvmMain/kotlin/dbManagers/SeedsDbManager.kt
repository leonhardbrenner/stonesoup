package dbManagers

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.KotlinModule
import generated.model.db.SeedsDb
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.transactions.transaction
import jsonLoaders.SeedsJsonLoaders
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.insertAndGetId
import java.io.File

fun resource(path: String) = File(ClassLoader.getSystemResource(path).file)
fun resourceText(path: String) = resource(path).readText()

object SeedsDBManager {
    val kMapper = ObjectMapper().registerModule(KotlinModule())

    fun drop() = transaction {
        SchemaUtils.drop(SeedsDb.MySeeds.Table)
        SchemaUtils.drop(SeedsDb.DetailedSeed.Table)
        SchemaUtils.drop(SeedsDb.SeedCategory.Table)
        SchemaUtils.drop(SeedsDb.Schedule.Table)
        SchemaUtils.drop(SeedsDb.Chore.Table)
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
            SeedsDb.MySeeds.Table.insertAndGetId {
                SeedsDb.MySeeds.insert(it, source)
            }
        }
        jsonLoaders.detailedSeeds.forEach { source ->
            SeedsDb.DetailedSeed.Table.insertAndGetId {
                SeedsDb.DetailedSeed.insert(it, source)
            }
        }
        jsonLoaders.categories.forEach { source ->
            SeedsDb.SeedCategory.Table.insertAndGetId {
                SeedsDb.SeedCategory.insert(it, source)
            }
        }
        jsonLoaders.chores.forEach { source ->
            SeedsDb.Chore.Table.insertAndGetId {
                SeedsDb.Chore.insert(it, source)
            }
        }
        jsonLoaders.schedules.forEach { source ->
            SeedsDb.Schedule.Table.insertAndGetId {
                SeedsDb.Schedule.insert(it, source)
            }
        }
    }
}
