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
  ) : Seeds.Chore {
    companion object {
      const val path: String = "/Seeds/Chore"

      fun create(source: Seeds.Chore) = SeedsDto.Chore(source.id, source.parentId,
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
  ) : Seeds.DetailedSeed {
    companion object {
      const val path: String = "/Seeds/DetailedSeed"

      fun create(source: Seeds.DetailedSeed) = SeedsDto.DetailedSeed(source.id, source.name,
          source.maturity, source.secondary_name, source.description, source.image, source.link)}
  }

  @Serializable
  data class MySeeds(
    override val id: Int,
    override val seed_label: String,
    override val description: String,
    override val germination_test: String
  ) : Seeds.MySeeds {
    companion object {
      const val path: String = "/Seeds/MySeeds"

      fun create(source: Seeds.MySeeds) = SeedsDto.MySeeds(source.id, source.seed_label,
          source.description, source.germination_test)}
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
