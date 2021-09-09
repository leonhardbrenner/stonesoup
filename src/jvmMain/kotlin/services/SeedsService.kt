package services

import dao.SeedsDao
import services.seeds.*
import org.jetbrains.exposed.sql.transactions.transaction
import javax.inject.Inject

class SeedsService @Inject constructor(val dao: SeedsDao, val chore: ChoreService) {
    //val Schedule = ScheduleService
    //val DetailedSeeds = DetailedSeedsService
    //val SeedCategory = SeedCategoryService
    //val MySeeds = MySeedsService

    fun getDetailedSeeds() = transaction { dao.DetailedSeeds.index() }

}
