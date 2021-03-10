package generated.model

import kotlin.Int
import kotlin.String
import kotlinx.serialization.Serializable

interface SeedsDto {
  @Serializable
  data class DetailedSeed(
    override val name: String,
    override val maturity: String?,
    override val secondary_name: String?,
    override val description: String?,
    override val image: String?,
    override val link: String?
  ) : Seeds.DetailedSeed {
    companion object {
      const val path: String = "/Seeds/DetailedSeed"

      fun create(source: Seeds.DetailedSeed) = SeedsDto.DetailedSeed(source.name, source.maturity,
          source.secondary_name, source.description, source.image, source.link)}
  }

  @Serializable
  data class MySeeds(
    override val my_seed_id: Int,
    override val seed_label: String,
    override val description: String,
    override val germination_test: String
  ) : Seeds.MySeeds {
    companion object {
      const val path: String = "/Seeds/MySeeds"

      fun create(source: Seeds.MySeeds) = SeedsDto.MySeeds(source.my_seed_id, source.seed_label,
          source.description, source.germination_test)}
  }

  @Serializable
  data class SeedCategory(
    override val name: String,
    override val image: String,
    override val link: String
  ) : Seeds.SeedCategory {
    companion object {
      const val path: String = "/Seeds/SeedCategory"

      fun create(source: Seeds.SeedCategory) = SeedsDto.SeedCategory(source.name, source.image,
          source.link)}
  }
}
