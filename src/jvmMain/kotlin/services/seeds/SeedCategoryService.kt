package services.seeds

import dao.SeedsDao
import generated.model.Seeds
import javax.inject.Inject

class SeedCategoryService @Inject constructor(val dao: SeedsDao) {

    fun index() = dao.SeedCategory.index()

    fun create(source: Seeds.SeedCategory) = dao.SeedCategory.create(source)

    fun update(source: Seeds.SeedCategory) = dao.SeedCategory.update(source)

    fun destroy(id: Int) = dao.SeedCategory.destroy(id)

}
