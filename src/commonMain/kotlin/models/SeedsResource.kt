package models

import generated.model.Seeds
import generated.model.SeedsDto
import kotlinx.serialization.Serializable

class Resources {

    @Serializable
    data class MySeeds(
        val source: SeedsDto.MySeeds,
        val detailedSeed: SeedsDto.DetailedSeed?
        ): Seeds.MySeeds by source

}
