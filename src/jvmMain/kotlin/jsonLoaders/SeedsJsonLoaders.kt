package jsonLoaders

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import dbManagers.resourceText
import generated.model.SeedsDto
import javax.inject.Inject

class SeedsJsonLoaders @Inject constructor(val kMapper: ObjectMapper) {

    val mySeeds: List<SeedsDto.MySeeds>
        get() = kMapper.readValue(
            resourceText("seeds/my-seeds.json")
        )

    val categories: List<SeedsDto.SeedCategory>
        get() = kMapper.readValue(
            resourceText("seeds/categories.json")
        )

    val detailedSeeds: List<SeedsDto.DetailedSeed>
        get() = kMapper.readValue(
            resourceText("seeds/detailed-seeds.json")
        )

    val chores: List<SeedsDto.Chore>
        get() = kMapper.readValue(
            resourceText("seeds/chores.json")
        )

    val schedules: List<SeedsDto.Schedule>
        get() = kMapper.readValue(
            resourceText("seeds/schedule.json")
        )

}
