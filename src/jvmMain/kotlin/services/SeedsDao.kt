package services

import services.crud.*

class SeedsDao {
    val Chore = ChoreDao
    val Schedule = ScheduleDao
    val DetailedSeeds = DetailedSeedsDao
    val SeedCategory = SeedCategoryDao
    val MySeeds = MySeedsDao
}