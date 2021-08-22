package services

import generated.model.db.SeedsDb
import models.Resources

class SeedsService {

    val mySeeds get() = SeedsDb.MySeeds.fetchAll().map {
        Resources.MySeeds(
            it,
            detailedSeedMap[it.seed_label.split(":").last().split(".").first()]
        )
    }

    fun lookupMySeed(pattern: String) = mySeeds.filter { it.description.contains(pattern) }

    val detailedSeedMap = getDetailedSeeds().map {
        val trimmedLink = it.link!!.substring(0, it.link!!.indexOf(".html?"))
        trimmedLink.substring(trimmedLink.lastIndexOf("-") + 1) to it
    }.toMap()

    fun getDetailedSeeds() = SeedsDb.DetailedSeed.fetchAll()

    fun getCategories() = SeedsDb.SeedCategory.fetchAll()

    //fun getChores() = SeedsDb.Chore.fetchAll()

}
