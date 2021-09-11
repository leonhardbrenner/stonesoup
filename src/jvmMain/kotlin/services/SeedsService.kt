package services

import generated.dao.SeedsDao
import services.seeds.*
import org.jetbrains.exposed.sql.transactions.transaction
import javax.inject.Inject

class SeedsService @Inject constructor(
    val dao: SeedsDao.DetailedSeed,
    val chore: ChoreService,
    val detailedSeeds: DetailedSeedService,
    val schedule: ScheduleService,
    val seedCategory: SeedCategoryService,
    val mySeeds: MySeedsService
) {
    fun getDetailedSeeds() = transaction { dao.index() }
}
