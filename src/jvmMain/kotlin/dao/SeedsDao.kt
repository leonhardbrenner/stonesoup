package dao

import dao.seeds.*

class SeedsDao {
    val chore = ChoreDao
    val schedule = ScheduleDao
    val detailedSeeds = DetailedSeedsDao
    val seedCategory = SeedCategoryDao
    val mySeeds = MySeedsDao
}