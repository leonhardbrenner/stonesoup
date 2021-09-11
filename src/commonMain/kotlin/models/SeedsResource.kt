package models

import generated.model.Seeds
import generated.model.SeedsDto
import kotlinx.serialization.Serializable

class SeedsResources {

    @Serializable
    data class Chore(
        val source: SeedsDto.Chore,
        val childIds: List<Int>,
        val schedule: SeedsDto.Schedule?
    ): Seeds.Chore by source

    @Serializable
    data class MySeeds(
        val source: SeedsDto.MySeeds,
        val detailedSeed: SeedsDto.DetailedSeed?
        ): Seeds.MySeeds by source

}
