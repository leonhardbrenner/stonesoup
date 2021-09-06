package applications.routing

import applications.CoreApplication
import generated.model.SeedsDto
import generated.model.db.SeedsDb
import io.ktor.application.*
import io.ktor.http.*
import io.ktor.response.*
import io.ktor.routing.*
import javax.inject.Inject

class MySeedsRouting @Inject constructor(val dao: CoreApplication.Dao) {
    fun routes(routing: Routing) = routing.route(SeedsDto.MySeeds.path) {

        get {
            //call.respond(collection.find().toList())
            call.respond(SeedsDb.MySeeds.fetchAll())
            //call.respond(dao.MySeedsy.index())
        }

        //get("/new") {
        //    TODO("Show form to make new")
        //    //call.respond(collection.find().toList())
        //    //call.respond(dao.MySeeds.index())
        //}

        post {
            val secondary_name = call.parameters["secondary_name"] ?: return@post call.respond(HttpStatusCode.BadRequest)
            val description = call.parameters["description"] ?: return@post call.respond(HttpStatusCode.BadRequest)
            val germination_test = call.parameters["germination_test"] ?: return@post call.respond(HttpStatusCode.BadRequest)
            dao.MySeeds.create(secondary_name, description, germination_test)
            call.respond(HttpStatusCode.OK)
        }

        //get("/{id}") {
        //    TODO("Lookup Chore by id")
        //}

        //get("/{id}/edit") {
        //    TODO("Show form to update Chore")
        //}

        put("/{id}") {
            val id = call.parameters["id"]?.toInt() ?: return@put call.respond(HttpStatusCode.BadRequest)
            val secondary_name = call.parameters["secondary_name"] ?: return@put call.respond(HttpStatusCode.BadRequest)
            val description = call.parameters["description"] ?: return@put call.respond(HttpStatusCode.BadRequest)
            val germination_test = call.parameters["germination_test"] ?: return@put call.respond(HttpStatusCode.BadRequest)
            dao.MySeeds.update(id, secondary_name, description, germination_test)
            call.respond(HttpStatusCode.OK)
        }

        delete("/{id}") {
            val id = call.parameters["id"]?.toInt() ?: error("Invalid delete request")
            dao.MySeeds.destroy(id)
            call.respond(HttpStatusCode.OK)
        }
    }
}
