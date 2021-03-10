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
  object DetailedSeed {
    fun create(source: ResultRow) = SeedsDto.DetailedSeed(source[Table.name],
        source[Table.maturity], source[Table.secondary_name], source[Table.description],
        source[Table.image], source[Table.link])
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
    ) : IntEntity(id), generated.model.Seeds.DetailedSeed {
      override var name: String by Table.name

      override var maturity: String? by Table.maturity

      override var secondary_name: String? by Table.secondary_name

      override var description: String? by Table.description

      override var image: String? by Table.image

      override var link: String? by Table.link

      companion object : IntEntityClass<Entity>(Table) {
        fun insert(source: Seeds.DetailedSeed) {
          Entity.new {
            name = source.name
            maturity = source.maturity
            secondary_name = source.secondary_name
            description = source.description
            image = source.image
            link = source.link
          }
        }
      }
    }
  }

  object MySeeds {
    fun create(source: ResultRow) = SeedsDto.MySeeds(source[Table.my_seed_id],
        source[Table.seed_label], source[Table.description], source[Table.germination_test])
    fun fetchAll() = transaction { with (Table) { selectAll().map { create(it) } } }
    object Table : IntIdTable("MySeeds") {
      val my_seed_id: Column<Int> = integer("my_seed_id")

      val seed_label: Column<String> = text("seed_label")

      val description: Column<String> = text("description")

      val germination_test: Column<String> = text("germination_test")
    }

    class Entity(
      id: EntityID<Int>
    ) : IntEntity(id), generated.model.Seeds.MySeeds {
      override var my_seed_id: Int by Table.my_seed_id

      override var seed_label: String by Table.seed_label

      override var description: String by Table.description

      override var germination_test: String by Table.germination_test

      companion object : IntEntityClass<Entity>(Table) {
        fun insert(source: Seeds.MySeeds) {
          Entity.new {
            my_seed_id = source.my_seed_id
            seed_label = source.seed_label
            description = source.description
            germination_test = source.germination_test
          }
        }
      }
    }
  }

  object SeedCategory {
    fun create(source: ResultRow) = SeedsDto.SeedCategory(source[Table.name], source[Table.image],
        source[Table.link])
    fun fetchAll() = transaction { with (Table) { selectAll().map { create(it) } } }
    object Table : IntIdTable("SeedCategory") {
      val name: Column<String> = text("name")

      val image: Column<String> = text("image")

      val link: Column<String> = text("link")
    }

    class Entity(
      id: EntityID<Int>
    ) : IntEntity(id), generated.model.Seeds.SeedCategory {
      override var name: String by Table.name

      override var image: String by Table.image

      override var link: String by Table.link

      companion object : IntEntityClass<Entity>(Table) {
        fun insert(source: Seeds.SeedCategory) {
          Entity.new {
            name = source.name
            image = source.image
            link = source.link
          }
        }
      }
    }
  }
}
