package services.seeds

import dao.SeedsDao
import generated.model.Seeds
import javax.inject.Inject

class SeedCategoryService @Inject constructor(val dao: SeedsDao) {

    fun index() = dao.seedCategory.index()

    fun create(source: Seeds.SeedCategory) = dao.seedCategory.create(source)

    fun update(source: Seeds.SeedCategory) = dao.seedCategory.update(source)

    fun destroy(id: Int) = dao.seedCategory.destroy(id)

}
