package applications

import applications.routing.*
import com.google.inject.AbstractModule
import javax.inject.Inject
import io.ktor.routing.*
import generated.dao.SeedsDao
import services.SeedsService

class CoreApplication @Inject constructor(
    val dao: SeedsDao,
    val seedsService: SeedsService,
    val chore: ChoreRouting,
    val schedule: ScheduleRouting,
    val detailedSeeds: DetailedSeedsRouting,
    val seedCategory: SeedCategoryRouting,
    val mySeeds: MySeedsRouting
    ) {

    fun routesFrom(routing: Routing) {
        chore.routes(routing)
        schedule.routes(routing)
        detailedSeeds.routes(routing)
        seedCategory.routes(routing)
        mySeeds.routes(routing)
    }

    object Module : AbstractModule() {

        override fun configure() {
            //Stonesoup PR 10 removed the rest database and all MongoDB wiring.
            //bind(CoroutineDatabase::class.java).toInstance(database())
        }

    }

}
