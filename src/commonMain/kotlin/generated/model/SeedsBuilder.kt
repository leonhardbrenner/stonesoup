package generated.model

import kotlin.Int
import kotlin.String

interface SeedsBuilder {
  class DetailedSeed(
    var name: String?,
    var maturity: String?,
    var secondary_name: String?,
    var description: String?,
    var image: String?,
    var link: String?
  ) {
    fun build(): Seeds.DetailedSeed = SeedsDto.DetailedSeed(
    name ?: throw IllegalArgumentException("name is not nullable"),
    maturity,
    secondary_name,
    description,
    image,
    link
    )}

  class MySeeds(
    var my_seed_id: Int?,
    var seed_label: String?,
    var description: String?,
    var germination_test: String?
  ) {
    fun build(): Seeds.MySeeds = SeedsDto.MySeeds(
    my_seed_id ?: throw IllegalArgumentException("my_seed_id is not nullable"),
    seed_label ?: throw IllegalArgumentException("seed_label is not nullable"),
    description ?: throw IllegalArgumentException("description is not nullable"),
    germination_test ?: throw IllegalArgumentException("germination_test is not nullable")
    )}

  class SeedCategory(
    var name: String?,
    var image: String?,
    var link: String?
  ) {
    fun build(): Seeds.SeedCategory = SeedsDto.SeedCategory(
    name ?: throw IllegalArgumentException("name is not nullable"),
    image ?: throw IllegalArgumentException("image is not nullable"),
    link ?: throw IllegalArgumentException("link is not nullable")
    )}
}
