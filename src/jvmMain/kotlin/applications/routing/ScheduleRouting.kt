package applications.routing

import generated.model.SeedsDto
import io.ktor.application.*
import io.ktor.http.*
import io.ktor.response.*
import io.ktor.routing.*
import services.SeedsDao
import javax.inject.Inject

class ScheduleRouting @Inject constructor(val dao: SeedsDao) {
    fun routes(routing: Routing) = routing.route(SeedsDto.Schedule.path) {

        get {
            call.respond(dao.Schedule.index())
        }

        //get("/new") {
        //    TODO("Show form to make new")
        //    //call.respond(collection.find().toList())
        //    //call.respond(dao.Schedule.index())
        //}

        post {
            val choreId = call.parameters["choreId"]?.toInt() ?: return@post call.respond(HttpStatusCode.BadRequest)
            val workHours = call.parameters["workHours"]
            val completeBy = call.parameters["completeBy"]
            dao.Schedule.create(
                SeedsDto.Schedule(-1, choreId, workHours, completeBy)
            )
            call.respond(HttpStatusCode.OK)
        }

        //get("/{id}") {
        //    TODO("Lookup Schedule by id")
        //}

        //get("/{id}/edit") {
        //}

        put("/{id}") {
            val id = call.parameters["id"]?.toInt() ?: return@put call.respond(HttpStatusCode.BadRequest)
            val choreId = call.parameters["choreId"]?.toInt() ?: return@put call.respond(HttpStatusCode.BadRequest)
            val workHours = call.parameters["workHours"]
            val completeBy = call.parameters["completeBy"]
            dao.Schedule.update(
                SeedsDto.Schedule(id, choreId, workHours, completeBy)
            )
            call.respond(HttpStatusCode.OK)
        }

        delete("/{id}") {
            val id = call.parameters["id"]?.toInt() ?: error("Invalid delete request")
            dao.Schedule.destroy(id)
            call.respond(HttpStatusCode.OK)
        }
    }
}
