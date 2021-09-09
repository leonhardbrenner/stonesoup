package services.seeds

import dao.SeedsDao
import generated.model.Seeds
import javax.inject.Inject

class DetailedSeedsService @Inject constructor(val dao: SeedsDao) {

    fun index() = dao.DetailedSeeds.index()

    fun create(source: Seeds.DetailedSeed) = dao.DetailedSeeds.create(source)

    fun update(source: Seeds.DetailedSeed) = dao.DetailedSeeds.update(source.id, source)

    fun destroy(id: Int) = dao.DetailedSeeds.destroy(id)

}
