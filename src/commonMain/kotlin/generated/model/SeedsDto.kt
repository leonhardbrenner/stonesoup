package generated.model

import kotlin.Int
import kotlin.String
import kotlinx.serialization.Serializable

interface SeedsDto {
  @Serializable
  data class Chore(
    override val id: Int,
    override val parentId: Int,
    override val childrenIds: String,
    override val name: String
  ) : generated.model.Seeds.Chore {
    companion object {
      const val path: String = "/Seeds/Chore"

      fun create(source: generated.model.Seeds.Chore) = SeedsDto.Chore(source.id, source.parentId,
          source.childrenIds, source.name)}
  }

  @Serializable
  data class DetailedSeed(
    override val id: Int,
    override val name: String,
    override val maturity: String?,
    override val secondary_name: String?,
    override val description: String?,
    override val image: String?,
    override val link: String?
  ) : generated.model.Seeds.DetailedSeed {
    companion object {
      const val path: String = "/Seeds/DetailedSeed"

      fun create(source: generated.model.Seeds.DetailedSeed) = SeedsDto.DetailedSeed(source.id,
          source.name, source.maturity, source.secondary_name, source.description, source.image,
          source.link)}
  }

  @Serializable
  data class MySeeds(
    override val id: Int,
    override val seed_label: String,
    override val description: String,
    override val germination_test: String
  ) : generated.model.Seeds.MySeeds {
    companion object {
      const val path: String = "/Seeds/MySeeds"

      fun create(source: generated.model.Seeds.MySeeds) = SeedsDto.MySeeds(source.id,
          source.seed_label, source.description, source.germination_test)}
  }

  @Serializable
  data class Schedule(
    override val id: Int,
    override val choreId: Int,
    override val workHours: String?,
    override val completeBy: String?
  ) : generated.model.Seeds.Schedule {
    companion object {
      const val path: String = "/Seeds/Schedule"

      fun create(source: generated.model.Seeds.Schedule) = SeedsDto.Schedule(source.id,
          source.choreId, source.workHours, source.completeBy)}
  }

  @Serializable
  data class SeedCategory(
    override val id: Int,
    override val name: String,
    override val image: String,
    override val link: String
  ) : generated.model.Seeds.SeedCategory {
    companion object {
      const val path: String = "/Seeds/SeedCategory"

      fun create(source: generated.model.Seeds.SeedCategory) = SeedsDto.SeedCategory(source.id,
          source.name, source.image, source.link)}
  }
}
