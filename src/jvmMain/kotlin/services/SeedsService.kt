package services

import generated.model.db.SeedsDb
import models.Resources

class SeedsService {

    fun getDetailedSeeds() = SeedsDb.DetailedSeed.fetchAll()

}
