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
import org.jetbrains.exposed.sql.transactions.transaction

object SeedsDb {
  object Schedule {
    fun create(source: ResultRow) = SeedsDto.Schedule(source[Table.id].value, source[Table.choreId],
        source[Table.workHours], source[Table.completeBy])
    fun fetchAll() = transaction { with (Table) { selectAll().map { create(it) } } }
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
    fun create(source: ResultRow) = SeedsDto.Chore(source[Table.id].value, source[Table.parentId],
        source[Table.childrenIds], source[Table.name], null)
    fun fetchAll() = transaction { with (Table) { selectAll().map { create(it) } } }
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
    fun create(source: ResultRow) = SeedsDto.DetailedSeed(source[Table.id].value,
        source[Table.name], source[Table.maturity], source[Table.secondary_name],
        source[Table.description], source[Table.image], source[Table.link])
    fun fetchAll() = transaction { with (Table) { selectAll().map { create(it) } } }
    object Table : IntIdTable("DetailedSeed") {
      val name: Column<String> = text("name")

      val maturity: Column<String?> = text("maturity").nullable()

      val secondary_name: Column<String?> = text("secondary_name").nullable()

      val description: Column<String?> = text("description").nullable()

      val image: Column<String?> = text("image").nullable()

      val link: Column<String?> = text("link").nullable()
    }

    class Entity(
      id: EntityID<Int>
    ) : IntEntity(id) {
      var name: String by Table.name

      var maturity: String? by Table.maturity

      var secondary_name: String? by Table.secondary_name

      var description: String? by Table.description

      var image: String? by Table.image

      var link: String? by Table.link

      companion object : IntEntityClass<Entity>(Table)
    }
  }

  object MySeeds {
    fun create(source: ResultRow) = SeedsDto.MySeeds(source[Table.id].value,
        source[Table.seed_label], source[Table.description], source[Table.germination_test])
    fun fetchAll() = transaction { with (Table) { selectAll().map { create(it) } } }
    object Table : IntIdTable("MySeeds") {
      val seed_label: Column<String> = text("seed_label")

      val description: Column<String> = text("description")

      val germination_test: Column<String> = text("germination_test")
    }

    class Entity(
      id: EntityID<Int>
    ) : IntEntity(id) {
      var seed_label: String by Table.seed_label

      var description: String by Table.description

      var germination_test: String by Table.germination_test

      companion object : IntEntityClass<Entity>(Table)
    }
  }

  object SeedCategory {
    fun create(source: ResultRow) = SeedsDto.SeedCategory(source[Table.id].value,
        source[Table.name], source[Table.image], source[Table.link])
    fun fetchAll() = transaction { with (Table) { selectAll().map { create(it) } } }
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
