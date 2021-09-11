package generated.dao

import generated.model.Seeds
import generated.model.db.SeedsDb
import kotlin.Int
import org.jetbrains.exposed.sql.deleteWhere
import org.jetbrains.exposed.sql.insertAndGetId
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.update

class SeedsDao {
  class Chore {
    fun index() = SeedsDb.Chore.Table.selectAll().map {
       SeedsDb.Chore.select(it)
    }
    fun get(id: Int) = SeedsDb.Chore.Table.select { SeedsDb.Chore.Table.id.eq(id) }.map {
        SeedsDb.Chore.select(it)
    }.last()
    fun create(source: Seeds.Chore) = SeedsDb.Chore.Table.insertAndGetId {
        SeedsDb.Chore.insert(it, source)
    }.value
    fun update(source: Seeds.Chore) = SeedsDb.Chore.Table.update({
        SeedsDb.Chore.Table.id.eq(source.id) }) {
        SeedsDb.Chore.update(it, source)
    }
    fun destroy(id: Int) = SeedsDb.Chore.Table.deleteWhere { SeedsDb.Chore.Table.id eq id }        }

  class DetailedSeed {
    fun index() = SeedsDb.DetailedSeed.Table.selectAll().map {
       SeedsDb.DetailedSeed.select(it)
    }
    fun get(id: Int) = SeedsDb.DetailedSeed.Table.select { SeedsDb.DetailedSeed.Table.id.eq(id)
        }.map {
        SeedsDb.DetailedSeed.select(it)
    }.last()
    fun create(source: Seeds.DetailedSeed) = SeedsDb.DetailedSeed.Table.insertAndGetId {
        SeedsDb.DetailedSeed.insert(it, source)
    }.value
    fun update(source: Seeds.DetailedSeed) = SeedsDb.DetailedSeed.Table.update({
        SeedsDb.DetailedSeed.Table.id.eq(source.id) }) {
        SeedsDb.DetailedSeed.update(it, source)
    }
    fun destroy(id: Int) = SeedsDb.DetailedSeed.Table.deleteWhere { SeedsDb.DetailedSeed.Table.id eq
        id }        }

  class MySeeds {
    fun index() = SeedsDb.MySeeds.Table.selectAll().map {
       SeedsDb.MySeeds.select(it)
    }
    fun get(id: Int) = SeedsDb.MySeeds.Table.select { SeedsDb.MySeeds.Table.id.eq(id) }.map {
        SeedsDb.MySeeds.select(it)
    }.last()
    fun create(source: Seeds.MySeeds) = SeedsDb.MySeeds.Table.insertAndGetId {
        SeedsDb.MySeeds.insert(it, source)
    }.value
    fun update(source: Seeds.MySeeds) = SeedsDb.MySeeds.Table.update({
        SeedsDb.MySeeds.Table.id.eq(source.id) }) {
        SeedsDb.MySeeds.update(it, source)
    }
    fun destroy(id: Int) = SeedsDb.MySeeds.Table.deleteWhere { SeedsDb.MySeeds.Table.id eq id }     
          }

  class Schedule {
    fun index() = SeedsDb.Schedule.Table.selectAll().map {
       SeedsDb.Schedule.select(it)
    }
    fun get(id: Int) = SeedsDb.Schedule.Table.select { SeedsDb.Schedule.Table.id.eq(id) }.map {
        SeedsDb.Schedule.select(it)
    }.last()
    fun create(source: Seeds.Schedule) = SeedsDb.Schedule.Table.insertAndGetId {
        SeedsDb.Schedule.insert(it, source)
    }.value
    fun update(source: Seeds.Schedule) = SeedsDb.Schedule.Table.update({
        SeedsDb.Schedule.Table.id.eq(source.id) }) {
        SeedsDb.Schedule.update(it, source)
    }
    fun destroy(id: Int) = SeedsDb.Schedule.Table.deleteWhere { SeedsDb.Schedule.Table.id eq id }   
            }

  class SeedCategory {
    fun index() = SeedsDb.SeedCategory.Table.selectAll().map {
       SeedsDb.SeedCategory.select(it)
    }
    fun get(id: Int) = SeedsDb.SeedCategory.Table.select { SeedsDb.SeedCategory.Table.id.eq(id)
        }.map {
        SeedsDb.SeedCategory.select(it)
    }.last()
    fun create(source: Seeds.SeedCategory) = SeedsDb.SeedCategory.Table.insertAndGetId {
        SeedsDb.SeedCategory.insert(it, source)
    }.value
    fun update(source: Seeds.SeedCategory) = SeedsDb.SeedCategory.Table.update({
        SeedsDb.SeedCategory.Table.id.eq(source.id) }) {
        SeedsDb.SeedCategory.update(it, source)
    }
    fun destroy(id: Int) = SeedsDb.SeedCategory.Table.deleteWhere { SeedsDb.SeedCategory.Table.id eq
        id }        }
}
