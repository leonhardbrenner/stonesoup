package applications

import applications.routing.ChoreRouting
import applications.routing.DetailedSeedsRouting
import com.google.inject.AbstractModule
import generated.model.SeedsDto
import javax.inject.Inject
import io.ktor.application.*
import io.ktor.routing.*
import io.ktor.http.*
import io.ktor.response.*
import services.SeedsService
import services.crud.ChoreDao
import services.crud.DetailedSeedsDao

class CoreApplication @Inject constructor(
    val dao: Dao,
    val seedsService: SeedsService,
    val choreRouting: ChoreRouting,
    val detailedSeedsRouting: DetailedSeedsRouting
    ) {

    fun routesFrom(routing: Routing) {
        choreRouting.routes(routing)
        //TODO - fix this
        //detailedSeedsRouting.routes(routing)
        //TODO - define routing for these
        routesFromMySeeds(routing)
        routesFromDetailedSeed(routing)
        routesFromSeedCategory(routing)
    }

    //https://ktor.io/docs/routing-in-ktor.html#define_route
    //https://medium.com/@shubhangirajagrawal/the-7-restful-routes-a8e84201f206
    fun routesFromMySeeds(routing: Routing) = routing.route(SeedsDto.MySeeds.path) {
        get {
            //XXX - You will need an outbound route which creates a Dto for us. This would be a good use of extensions.
            call.respond(seedsService.mySeeds)
        }
    }

    fun routesFromDetailedSeed(routing: Routing) = routing.route(SeedsDto.DetailedSeed.path) {
        get {
            call.respond(seedsService.getDetailedSeeds())
        }
    }

    fun routesFromSeedCategory(routing: Routing) = routing.route(SeedsDto.SeedCategory.path) {
        get {
            call.respond(seedsService.getCategories())
        }
    }

    object Module : AbstractModule() {

        override fun configure() {
            //Stonesoup PR 10 removed the rest database and all MongoDB wiring.
            //bind(CoroutineDatabase::class.java).toInstance(database())
        }

    }

    class Dao {
        val Chore = ChoreDao
        val DetailedSeeds = DetailedSeedsDao
    }
}
