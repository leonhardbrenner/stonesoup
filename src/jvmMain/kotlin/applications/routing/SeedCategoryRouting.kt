package applications.routing

import applications.CoreApplication
import generated.model.SeedsDto
import generated.model.db.SeedsDb
import io.ktor.application.*
import io.ktor.http.*
import io.ktor.response.*
import io.ktor.routing.*
import javax.inject.Inject

class SeedCategoryRouting @Inject constructor(val dao: CoreApplication.Dao) {
    fun routes(routing: Routing) = routing.route(SeedsDto.SeedCategory.path) {

        get {
            //call.respond(collection.find().toList())
            call.respond(SeedsDb.SeedCategory.fetchAll())
            //call.respond(dao.SeedCategoryy.index())
        }

        //get("/new") {
        //    TODO("Show form to make new")
        //    //call.respond(collection.find().toList())
        //    //call.respond(dao.SeedCategory.index())
        //}

        post {
            val name = call.parameters["name"] ?: return@post call.respond(HttpStatusCode.BadRequest)
            val image = call.parameters["image"] ?: return@post call.respond(HttpStatusCode.BadRequest)
            val link = call.parameters["link"] ?: return@post call.respond(HttpStatusCode.BadRequest)
            dao.SeedCategory.create(name, image, link)
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
            val name = call.parameters["name"] ?: return@put call.respond(HttpStatusCode.BadRequest)
            val image = call.parameters["image"] ?: return@put call.respond(HttpStatusCode.BadRequest)
            val link = call.parameters["link"] ?: return@put call.respond(HttpStatusCode.BadRequest)
            dao.SeedCategory.update(id, name, image, link)
            call.respond(HttpStatusCode.OK)
        }

        delete("/{id}") {
            val id = call.parameters["id"]?.toInt() ?: error("Invalid delete request")
            dao.SeedCategory.destroy(id)
            call.respond(HttpStatusCode.OK)
        }
    }
}