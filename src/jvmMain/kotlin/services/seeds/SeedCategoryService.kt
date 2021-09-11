package services.seeds

import generated.dao.SeedsDao
import generated.model.Seeds
import javax.inject.Inject

class SeedCategoryService @Inject constructor(val dao: SeedsDao.SeedCategory) {

    fun index() = dao.index()

    fun create(source: Seeds.SeedCategory) = dao.create(source)

    fun update(source: Seeds.SeedCategory) = dao.update(source)

    fun destroy(id: Int) = dao.destroy(id)

}
