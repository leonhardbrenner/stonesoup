package dbManagers

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.KotlinModule
import generated.model.db.SeedsDb
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.transactions.transaction
import java.io.File
import jsonLoaders.SeedsJsonLoaders

fun resource(path: String) = File(ClassLoader.getSystemResource(path).file)
fun resourceText(path: String) = resource(path).readText()

object SeedsDBManager {
    val kMapper = ObjectMapper().registerModule(KotlinModule())

    fun drop() = transaction {
        SchemaUtils.drop(SeedsDb.MySeeds.Table)
        SchemaUtils.drop(SeedsDb.DetailedSeed.Table)
        SchemaUtils.drop(SeedsDb.SeedCategory.Table)
    }
    fun create() = transaction {
        SchemaUtils.create(SeedsDb.MySeeds.Table)
        SchemaUtils.create(SeedsDb.DetailedSeed.Table)
        SchemaUtils.create(SeedsDb.SeedCategory.Table)
    }
    fun populate() = transaction {
        val jsonLoaders = SeedsJsonLoaders(kMapper)
        jsonLoaders.mySeeds.forEach { source ->
            SeedsDb.MySeeds.Entity.insert(source)
            println("Creating ${source.description}")
        }
        jsonLoaders.detailedSeeds.forEach { source ->
            SeedsDb.DetailedSeed.Entity.insert(source)
            println("Creating ${source.name}")
        }
        jsonLoaders.categories.forEach { source ->
            SeedsDb.SeedCategory.Entity.insert(source)
            println("Creating ${source.name}")
        }

    }
}
