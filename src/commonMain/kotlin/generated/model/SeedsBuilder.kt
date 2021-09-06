package generated.model

import kotlin.Int
import kotlin.String

interface SeedsBuilder {
  class Schedule(
    var id: Int?,
    var choreId: Int?,
    var workHours: String?,
    var completeBy: String?
  ) {
    fun build(): Seeds.Schedule = SeedsDto.Schedule(
    id ?: throw IllegalArgumentException("id is not nullable"),
    choreId ?: throw IllegalArgumentException("choreId is not nullable"),
    workHours,
    completeBy
    )}

  class Chore(
    var id: Int?,
    var parentId: Int?,
    var childrenIds: String?,
    var name: String?,
    var schedule: Seeds.Schedule?
  ) {
    fun build(): Seeds.Chore = SeedsDto.Chore(
    id ?: throw IllegalArgumentException("id is not nullable"),
    parentId ?: throw IllegalArgumentException("parentId is not nullable"),
    childrenIds ?: throw IllegalArgumentException("childrenIds is not nullable"),
    name ?: throw IllegalArgumentException("name is not nullable"),
    schedule?.let { SeedsDto.Schedule.create(it) }
    )}

  class DetailedSeed(
    var id: Int?,
    var name: String?,
    var maturity: String?,
    var secondary_name: String?,
    var description: String?,
    var image: String?,
    var link: String?
  ) {
    fun build(): Seeds.DetailedSeed = SeedsDto.DetailedSeed(
    id ?: throw IllegalArgumentException("id is not nullable"),
    name ?: throw IllegalArgumentException("name is not nullable"),
    maturity,
    secondary_name,
    description,
    image,
    link
    )}

  class MySeeds(
    var id: Int?,
    var companyId: String?,
    var seedId: String?,
    var description: String?,
    var germinationTest: String?
  ) {
    fun build(): Seeds.MySeeds = SeedsDto.MySeeds(
    id ?: throw IllegalArgumentException("id is not nullable"),
    companyId ?: throw IllegalArgumentException("companyId is not nullable"),
    seedId ?: throw IllegalArgumentException("seedId is not nullable"),
    description ?: throw IllegalArgumentException("description is not nullable"),
    germinationTest ?: throw IllegalArgumentException("germinationTest is not nullable")
    )}

  class SeedCategory(
    var id: Int?,
    var name: String?,
    var image: String?,
    var link: String?
  ) {
    fun build(): Seeds.SeedCategory = SeedsDto.SeedCategory(
    id ?: throw IllegalArgumentException("id is not nullable"),
    name ?: throw IllegalArgumentException("name is not nullable"),
    image ?: throw IllegalArgumentException("image is not nullable"),
    link ?: throw IllegalArgumentException("link is not nullable")
    )}
}
