package generated.model.db

import generated.model.Seeds
import generated.model.Seeds.Chore
import generated.model.Seeds.DetailedSeed
import generated.model.Seeds.MySeeds
import generated.model.Seeds.Schedule
import generated.model.Seeds.SeedCategory
import generated.model.SeedsDto
import kotlin.Int
import kotlin.String
import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.IntIdTable
import org.jetbrains.exposed.sql.Column
import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.statements.InsertStatement
import org.jetbrains.exposed.sql.statements.UpdateStatement
import org.jetbrains.exposed.sql.transactions.transaction

object SeedsDb {
  object Chore {
    fun select(source: ResultRow) = SeedsDto.Chore(source[Table.id].value, source[Table.parentId],
        source[Table.name])
    fun insert(it: InsertStatement<EntityID<Int>>, source: Seeds.Chore) {
      it[Table.parentId] = source.parentId
      it[Table.name] = source.name
    }

    fun update(it: UpdateStatement, source: Seeds.Chore) {
      it[Table.parentId] = source.parentId
      it[Table.name] = source.name
    }

    object Table : IntIdTable("Chore") {
      val parentId: Column<Int> = integer("parentId")

      val name: Column<String> = text("name")
    }

    class Entity(
      id: EntityID<Int>
    ) : IntEntity(id) {
      var parentId: Int by Table.parentId

      var name: String by Table.name

      companion object : IntEntityClass<Entity>(Table)
    }
  }

  object DetailedSeed {
    fun select(source: ResultRow) = SeedsDto.DetailedSeed(source[Table.id].value,
        source[Table.companyId], source[Table.seedId], source[Table.name], source[Table.maturity],
        source[Table.secondaryName], source[Table.description], source[Table.image],
        source[Table.link])
    fun insert(it: InsertStatement<EntityID<Int>>, source: Seeds.DetailedSeed) {
      it[Table.companyId] = source.companyId
      it[Table.seedId] = source.seedId
      it[Table.name] = source.name
      it[Table.maturity] = source.maturity
      it[Table.secondaryName] = source.secondaryName
      it[Table.description] = source.description
      it[Table.image] = source.image
      it[Table.link] = source.link
    }

    fun update(it: UpdateStatement, source: Seeds.DetailedSeed) {
      it[Table.companyId] = source.companyId
      it[Table.seedId] = source.seedId
      it[Table.name] = source.name
      it[Table.maturity] = source.maturity
      it[Table.secondaryName] = source.secondaryName
      it[Table.description] = source.description
      it[Table.image] = source.image
      it[Table.link] = source.link
    }

    object Table : IntIdTable("DetailedSeed") {
      val companyId: Column<String> = text("companyId")

      val seedId: Column<String> = text("seedId")

      val name: Column<String> = text("name")

      val maturity: Column<String?> = text("maturity").nullable()

      val secondaryName: Column<String?> = text("secondaryName").nullable()

      val description: Column<String?> = text("description").nullable()

      val image: Column<String?> = text("image").nullable()

      val link: Column<String?> = text("link").nullable()
    }

    class Entity(
      id: EntityID<Int>
    ) : IntEntity(id) {
      var companyId: String by Table.companyId

      var seedId: String by Table.seedId

      var name: String by Table.name

      var maturity: String? by Table.maturity

      var secondaryName: String? by Table.secondaryName

      var description: String? by Table.description

      var image: String? by Table.image

      var link: String? by Table.link

      companion object : IntEntityClass<Entity>(Table)
    }
  }

  object MySeeds {
    fun select(source: ResultRow) = SeedsDto.MySeeds(source[Table.id].value,
        source[Table.companyId], source[Table.seedId], source[Table.description],
        source[Table.germinationTest])
    fun insert(it: InsertStatement<EntityID<Int>>, source: Seeds.MySeeds) {
      it[Table.companyId] = source.companyId
      it[Table.seedId] = source.seedId
      it[Table.description] = source.description
      it[Table.germinationTest] = source.germinationTest
    }

    fun update(it: UpdateStatement, source: Seeds.MySeeds) {
      it[Table.companyId] = source.companyId
      it[Table.seedId] = source.seedId
      it[Table.description] = source.description
      it[Table.germinationTest] = source.germinationTest
    }

    object Table : IntIdTable("MySeeds") {
      val companyId: Column<String> = text("companyId")

      val seedId: Column<String> = text("seedId")

      val description: Column<String> = text("description")

      val germinationTest: Column<String> = text("germinationTest")
    }

    class Entity(
      id: EntityID<Int>
    ) : IntEntity(id) {
      var companyId: String by Table.companyId

      var seedId: String by Table.seedId

      var description: String by Table.description

      var germinationTest: String by Table.germinationTest

      companion object : IntEntityClass<Entity>(Table)
    }
  }

  object Schedule {
    fun select(source: ResultRow) = SeedsDto.Schedule(source[Table.id].value, source[Table.choreId],
        source[Table.workHours], source[Table.completeBy])
    fun insert(it: InsertStatement<EntityID<Int>>, source: Seeds.Schedule) {
      it[Table.choreId] = source.choreId
      it[Table.workHours] = source.workHours
      it[Table.completeBy] = source.completeBy
    }

    fun update(it: UpdateStatement, source: Seeds.Schedule) {
      it[Table.choreId] = source.choreId
      it[Table.workHours] = source.workHours
      it[Table.completeBy] = source.completeBy
    }

    object Table : IntIdTable("Schedule") {
      val choreId: Column<Int> = integer("choreId")

      val workHours: Column<String?> = text("workHours").nullable()

      val completeBy: Column<String?> = text("completeBy").nullable()
    }

    class Entity(
      id: EntityID<Int>
    ) : IntEntity(id) {
      var choreId: Int by Table.choreId

      var workHours: String? by Table.workHours

      var completeBy: String? by Table.completeBy

      companion object : IntEntityClass<Entity>(Table)
    }
  }

  object SeedCategory {
    fun select(source: ResultRow) = SeedsDto.SeedCategory(source[Table.id].value,
        source[Table.name], source[Table.image], source[Table.link])
    fun insert(it: InsertStatement<EntityID<Int>>, source: Seeds.SeedCategory) {
      it[Table.name] = source.name
      it[Table.image] = source.image
      it[Table.link] = source.link
    }

    fun update(it: UpdateStatement, source: Seeds.SeedCategory) {
      it[Table.name] = source.name
      it[Table.image] = source.image
      it[Table.link] = source.link
    }

    object Table : IntIdTable("SeedCategory") {
      val name: Column<String> = text("name")

      val image: Column<String> = text("image")

      val link: Column<String> = text("link")
    }

    class Entity(
      id: EntityID<Int>
    ) : IntEntity(id) {
      var name: String by Table.name

      var image: String by Table.image

      var link: String by Table.link

      companion object : IntEntityClass<Entity>(Table)
    }
  }
}
