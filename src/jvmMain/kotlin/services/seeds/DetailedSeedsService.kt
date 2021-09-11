package services.seeds

import generated.dao.SeedsDao
import generated.model.Seeds
import javax.inject.Inject

class DetailedSeedsService @Inject constructor(val dao: SeedsDao) {

    fun index() = dao.detailedSeed.index()

    fun create(source: Seeds.DetailedSeed) = dao.detailedSeed.create(source)

    fun update(source: Seeds.DetailedSeed) = dao.detailedSeed.update(source)

    fun destroy(id: Int) = dao.detailedSeed.destroy(id)

}
