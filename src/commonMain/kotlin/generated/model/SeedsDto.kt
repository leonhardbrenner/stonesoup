package generated.model

import kotlin.Int
import kotlin.String
import kotlinx.serialization.Serializable

interface SeedsDto {
  @Serializable
  data class Chore(
    override val id: Int,
    override val parentId: Int,
    override val name: String
  ) : Seeds.Chore {
    companion object {
      const val path: String = "/Seeds/Chore"

      fun create(source: Seeds.Chore) = SeedsDto.Chore(source.id, source.parentId, source.name)}
  }

  @Serializable
  data class DetailedSeed(
    override val id: Int,
    override val companyId: String,
    override val seedId: String,
    override val name: String,
    override val maturity: String?,
    override val secondaryName: String?,
    override val description: String?,
    override val image: String?,
    override val link: String?
  ) : Seeds.DetailedSeed {
    companion object {
      const val path: String = "/Seeds/DetailedSeed"

      fun create(source: Seeds.DetailedSeed) = SeedsDto.DetailedSeed(source.id, source.companyId,
          source.seedId, source.name, source.maturity, source.secondaryName, source.description,
          source.image, source.link)}
  }

  @Serializable
  data class MySeeds(
    override val id: Int,
    override val companyId: String,
    override val seedId: String,
    override val description: String,
    override val germinationTest: String
  ) : Seeds.MySeeds {
    companion object {
      const val path: String = "/Seeds/MySeeds"

      fun create(source: Seeds.MySeeds) = SeedsDto.MySeeds(source.id, source.companyId,
          source.seedId, source.description, source.germinationTest)}
  }

  @Serializable
  data class Schedule(
    override val id: Int,
    override val choreId: Int,
    override val workHours: String?,
    override val completeBy: String?
  ) : Seeds.Schedule {
    companion object {
      const val path: String = "/Seeds/Schedule"

      fun create(source: Seeds.Schedule) = SeedsDto.Schedule(source.id, source.choreId,
          source.workHours, source.completeBy)}
  }

  @Serializable
  data class SeedCategory(
    override val id: Int,
    override val name: String,
    override val image: String,
    override val link: String
  ) : Seeds.SeedCategory {
    companion object {
      const val path: String = "/Seeds/SeedCategory"

      fun create(source: Seeds.SeedCategory) = SeedsDto.SeedCategory(source.id, source.name,
          source.image, source.link)}
  }
}
