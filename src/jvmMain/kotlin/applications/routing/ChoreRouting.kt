package applications.routing

import applications.CoreApplication
import generated.model.SeedsDto
import io.ktor.application.*
import io.ktor.http.*
import io.ktor.response.*
import io.ktor.routing.*
import javax.inject.Inject

class ChoreRouting @Inject constructor(val dao: CoreApplication.Dao) {

    fun routes(routing: Routing) = routing.route(SeedsDto.Chore.path) {

        get {
            call.respond(dao.Chore.index())
        }

        //get("/new") {
        //    TODO("Show form to make new Chore")
        //    //call.respond(collection.find().toList())
        //    //call.respond(dao.Chore.index())
        //}

        post {
            val parentId = call.parameters["parentId"]?.toInt() ?: return@post call.respond(HttpStatusCode.BadRequest)
            val name = call.parameters["name"]?: return@post call.respond(HttpStatusCode.BadRequest)
            //Todo - let's send a Chore to begin with.
            dao.Chore.create(
                SeedsDto.Chore(-1, parentId, "", name)
            )
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
            val name = call.parameters["name"]
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
