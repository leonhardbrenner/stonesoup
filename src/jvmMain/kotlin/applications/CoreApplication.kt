package applications

import com.google.inject.AbstractModule
import generated.model.SeedsDto
import javax.inject.Inject
import io.ktor.application.*
import io.ktor.routing.*
import io.ktor.http.*
import io.ktor.response.*
import services.SeedsService
import services.crud.ChoreDao

class CoreApplication @Inject constructor(val dao: Dao, val seedsService: SeedsService) {

    //https://ktor.io/docs/routing-in-ktor.html#define_route
    //https://medium.com/@shubhangirajagrawal/the-7-restful-routes-a8e84201f206
    fun routesFrom(routing: Routing) = routing.apply {
        route(SeedsDto.MySeeds.path) {
            get {
                //XXX - You will need an outbound route which creates a Dto for us. This would be a good use of extensions.
                call.respond(seedsService.mySeeds)
            }
        }
        route(SeedsDto.DetailedSeed.path) {
            get {
                call.respond(seedsService.getDetailedSeeds())
            }
        }
        route(SeedsDto.SeedCategory.path) {
            get {
                call.respond(seedsService.getCategories())
            }
        }
        route(SeedsDto.Chore.path) {

            get {
                //call.respond(collection.find().toList())
                call.respond(dao.Chore.index())
            }

            //get("/new") {
            //    TODO("Show form to make new Chore")
            //    //call.respond(collection.find().toList())
            //    //call.respond(dao.Chore.index())
            //}

            post {
                val parentId = call.parameters["parentId"]?.toInt() ?: return@post call.respond(HttpStatusCode.BadRequest)
                val name = call.parameters["name"] ?: return@post call.respond(HttpStatusCode.BadRequest)
                dao.Chore.create(parentId, name)
                call.respond(HttpStatusCode.OK)
            }

            //get("/{id}") {
            //    TODO("Show form to make new Chore")
            //    //call.respond(collection.find().toList())
            //    //call.respond(dao.Chore.index())
            //}

            //get("/{id}/edit") {
            //    TODO("Show form to make new Chore")
            //    //call.respond(collection.find().toList())
            //    //call.respond(dao.Chore.index())
            //}

            put("/{id}") {
                val id = call.parameters["id"]?.toInt() ?: return@put call.respond(HttpStatusCode.BadRequest)
                val parentId = call.parameters["parentId"]?.toInt() ?: return@put call.respond(HttpStatusCode.BadRequest)
                val name = call.parameters["name"]// ?: return@put call.respond(HttpStatusCode.BadRequest)
                dao.Chore.update(id, parentId, name)
                call.respond(HttpStatusCode.OK)
            }

            delete("/{id}") {
                val id = call.parameters["id"]?.toInt() ?: error("Invalid delete request")
                dao.Chore.destroy(id)
                call.respond(HttpStatusCode.OK)
            }
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
    }
}
