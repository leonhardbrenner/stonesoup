package generated.model.db

import generated.model.Seeds
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
  object Schedule {
    fun create(source: ResultRow) = SeedsDto.Schedule(
      source[Table.id].value, source[Table.choreId],
      source[Table.workHours], source[Table.completeBy]
    )

    fun insert(it: InsertStatement<EntityID<Int>>, source: Seeds.Schedule) = with (source) {
      it[Table.choreId] = choreId
      it[Table.workHours] = workHours
      it[Table.completeBy] = completeBy
    }

    fun update(it: UpdateStatement, source: Seeds.Schedule) = with (source) {
      it[Table.choreId] = choreId
      it[Table.workHours] = workHours
      it[Table.completeBy] = completeBy
    }

    fun fetchAll() = transaction { with(Table) { selectAll().map { create(it) } } }

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

  object Chore {
    fun create(source: ResultRow) = SeedsDto.Chore(
      source[Table.id].value, source[Table.parentId],
      source[Table.childrenIds], source[Table.name]
    )

    fun insert(it: InsertStatement<EntityID<Int>>, source: Seeds.Chore) = with (source) {
      it[Table.parentId] = parentId
      it[Table.childrenIds] = childrenIds
      it[Table.name] = name
    }

    fun update(it: UpdateStatement, source: Seeds.Chore) = with (source) {
      it[Table.parentId] = parentId
      it[Table.childrenIds] = childrenIds
      it[Table.name] = name
    }

    fun fetchAll() = transaction { with(Table) { selectAll().map { create(it) } } }

    object Table : IntIdTable("Chore") {
      val parentId: Column<Int> = integer("parentId")

      val childrenIds: Column<String> = text("childrenIds")

      val name: Column<String> = text("name")
    }

    class Entity(
      id: EntityID<Int>
    ) : IntEntity(id) {
      var parentId: Int by Table.parentId

      var childrenIds: String by Table.childrenIds

      var name: String by Table.name

      companion object : IntEntityClass<Entity>(Table)
    }
  }

  object DetailedSeed {
    fun create(source: ResultRow) = SeedsDto.DetailedSeed(
      source[Table.id].value,
      source[Table.companyId], source[Table.seedId], source[Table.name], source[Table.maturity],
      source[Table.secondaryName], source[Table.description], source[Table.image],
      source[Table.link]
    )

    fun insert(
      it: InsertStatement<EntityID<Int>>,
      source: Seeds.DetailedSeed
    ) = with (source) {
      it[Table.companyId] = companyId
      it[Table.seedId] = seedId
      it[Table.name] = name
      it[Table.maturity] = maturity
      it[Table.secondaryName] = secondaryName
      it[Table.description] = description
      it[Table.image] = image
      it[Table.link] = link
    }

    fun update(
      it: UpdateStatement,
      source: Seeds.DetailedSeed
    ) = with (source) {
      it[Table.companyId] = companyId
      it[Table.seedId] = seedId
      it[Table.name] = name
      it[Table.maturity] = maturity
      it[Table.secondaryName] = secondaryName
      it[Table.description] = description
      it[Table.image] = image
      it[Table.link] = link
    }

    fun fetchAll() = transaction { with(Table) { selectAll().map { create(it) } } }

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
    fun create(source: ResultRow) = SeedsDto.MySeeds(
      source[Table.id].value,
      source[Table.companyId],
      source[Table.seedId],
      source[Table.description],
      source[Table.germinationTest]
    )

    fun insert(it: InsertStatement<EntityID<Int>>, source: Seeds.MySeeds) = with (source) {
      it[Table.companyId] = companyId
      it[Table.seedId] = seedId
      it[Table.description] = description
      it[Table.germinationTest] = germinationTest
    }

    fun update(it: UpdateStatement, source: Seeds.MySeeds) = with (source) {
      it[Table.companyId] = companyId
      it[Table.seedId] = seedId
      it[Table.description] = description
      it[Table.germinationTest] = germinationTest
    }

    fun fetchAll() = transaction { with(Table) { selectAll().map { create(it) } } }

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

  object SeedCategory {
    fun create(source: ResultRow) = SeedsDto.SeedCategory(
      source[Table.id].value,
      source[Table.name], source[Table.image], source[Table.link]
    )

    fun insert(it: InsertStatement<EntityID<Int>>, source: Seeds.SeedCategory) = with (source) {
      it[Table.name] = name
      it[Table.image] = image
      it[Table.link] = link
    }

    fun update(it: UpdateStatement, source: Seeds.SeedCategory) = with (source) {
      it[Table.name] = name
      it[Table.image] = image
      it[Table.link] = link
    }

    fun fetchAll() = transaction { with(Table) { selectAll().map { create(it) } } }

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
