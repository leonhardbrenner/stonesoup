package applications

import applications.routing.ChoreRouting
import applications.routing.DetailedSeedsRouting
import applications.routing.MySeedsRouting
import applications.routing.SeedCategoryRouting
import com.google.inject.AbstractModule
import generated.model.SeedsDto
import javax.inject.Inject
import io.ktor.application.*
import io.ktor.routing.*
import io.ktor.response.*
import services.SeedsDao
import services.SeedsService

class CoreApplication @Inject constructor(
    val dao: SeedsDao,
    val seedsService: SeedsService,
    val choreRouting: ChoreRouting,
    val detailedSeedsRouting: DetailedSeedsRouting,
    val seedCategoryRouting: SeedCategoryRouting,
    val mySeedsRouting: MySeedsRouting
    ) {

    fun routesFrom(routing: Routing) {
        choreRouting.routes(routing)
        detailedSeedsRouting.routes(routing)
        seedCategoryRouting.routes(routing)
        mySeedsRouting.routes(routing)
    }

    //https://ktor.io/docs/routing-in-ktor.html#define_route
    //https://medium.com/@shubhangirajagrawal/the-7-restful-routes-a8e84201f206
    fun routesFromMySeeds(routing: Routing) = routing.route(SeedsDto.MySeeds.path) {
        get {
            //XXX - You will need an outbound route which creates a Dto for us. This would be a good use of extensions.
            call.respond(seedsService.mySeeds)
        }
    }

    object Module : AbstractModule() {

        override fun configure() {
            //Stonesoup PR 10 removed the rest database and all MongoDB wiring.
            //bind(CoroutineDatabase::class.java).toInstance(database())
        }

    }

}
