package services.seeds

import dao.SeedsDao
import generated.model.Seeds
import models.SeedsResources
import utils.then
import javax.inject.Inject

class MySeedsService @Inject constructor(val dao: SeedsDao) {

    fun index() = let {
        val (detailedSeeds, mySeeds) = org.jetbrains.exposed.sql.transactions.transaction {
            dao.DetailedSeeds.index().associateBy { it.id } then
                    dao.MySeeds.index()
        }
        mySeeds.map { mySeed ->
            SeedsResources.MySeeds(mySeed, detailedSeeds.get(mySeed.id))
        }
    }

    fun create(source: Seeds.MySeeds) = dao.MySeeds.create(source)

    fun update(source: Seeds.MySeeds) = dao.MySeeds.update(source)

    fun destroy(id: Int) = dao.MySeeds.destroy(id)

}
