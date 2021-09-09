package services

import dao.SeedsDao
import services.seeds.*
import org.jetbrains.exposed.sql.transactions.transaction
import javax.inject.Inject

class SeedsService @Inject constructor(
    val dao: SeedsDao,
    val chore: ChoreService,
    val detailedSeeds: DetailedSeedsService,
    val schedule: ScheduleService,
    val seedCategory: SeedCategoryService,
    val mySeeds: MySeedsService
) {
    fun getDetailedSeeds() = transaction { dao.detailedSeeds.index() }
}
