package services.seeds

import generated.dao.SeedsDao
import generated.model.Seeds
import models.SeedsResources
import javax.inject.Inject

class MySeedsService @Inject constructor(val dao: SeedsDao) {

    fun index() = let {
        val detailedSeeds = dao.detailedSeed.index().associateBy { it.id }
        dao.mySeeds.index().map {
            SeedsResources.MySeeds(it, detailedSeeds.get(it.id))
        }
    }

    fun create(source: Seeds.MySeeds) = dao.mySeeds.create(source)

    fun update(source: Seeds.MySeeds) = dao.mySeeds.update(source)

    fun destroy(id: Int) = dao.mySeeds.destroy(id)

}
