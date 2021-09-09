package services

import generated.model.db.SeedsDb

class SeedsService {

    fun getDetailedSeeds() = SeedsDb.DetailedSeed.fetchAll()

}
