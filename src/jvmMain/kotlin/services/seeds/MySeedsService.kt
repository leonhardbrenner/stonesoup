package services.seeds

import generated.dao.SeedsDao
import generated.model.Seeds
import models.SeedsResources
import javax.inject.Inject

class MySeedsService @Inject constructor(
    val mySeedsDao: SeedsDao.MySeeds, val detailedSeedDao: SeedsDao.DetailedSeed) {

    fun index() = let {
        val detailedSeeds = detailedSeedDao.index().associateBy { it.id }
        mySeedsDao.index().map {
            SeedsResources.MySeeds(it, detailedSeeds.get(it.id))
        }
    }

    fun create(source: Seeds.MySeeds) = mySeedsDao.create(source)

    fun update(source: Seeds.MySeeds) = mySeedsDao.update(source)

    fun destroy(id: Int) = mySeedsDao.destroy(id)

}
