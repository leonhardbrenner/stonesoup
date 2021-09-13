package applications

//import applications.routing.*
import generated.routing.SeedsRouting
import com.google.inject.AbstractModule
import javax.inject.Inject
import io.ktor.routing.*
import generated.dao.SeedsDao
import services.SeedsService

class CoreApplication @Inject constructor(
    val dao: SeedsDao,
    val chore: SeedsRouting.Chore,
    val schedule: SeedsRouting.Schedule,
    val detailedSeed: SeedsRouting.DetailedSeed,
    val seedCategory: SeedsRouting.SeedCategory,
    val mySeeds: SeedsRouting.MySeeds
    ) {

    fun routesFrom(routing: Routing) {
        chore.routes(routing)
        schedule.routes(routing)
        detailedSeed.routes(routing)
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
