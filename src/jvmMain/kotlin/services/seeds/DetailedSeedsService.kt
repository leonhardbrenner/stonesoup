package services.seeds

import dao.SeedsDao
import generated.model.Seeds
import javax.inject.Inject

class DetailedSeedsService @Inject constructor(val dao: SeedsDao) {

    fun index() = dao.detailedSeeds.index()

    fun create(source: Seeds.DetailedSeed) = dao.detailedSeeds.create(source)

    fun update(source: Seeds.DetailedSeed) = dao.detailedSeeds.update(source.id, source)

    fun destroy(id: Int) = dao.detailedSeeds.destroy(id)

}
