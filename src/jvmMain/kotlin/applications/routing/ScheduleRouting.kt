package applications.routing

import generated.model.SeedsDto
import io.ktor.application.*
import io.ktor.http.*
import io.ktor.response.*
import io.ktor.routing.*
import generated.dao.SeedsDao
import org.jetbrains.exposed.sql.transactions.transaction
import services.SeedsService
import javax.inject.Inject

class ScheduleRouting @Inject constructor(val dao: SeedsDao, val service: SeedsService) {
    fun routes(routing: Routing) = routing.route(SeedsDto.Schedule.path) {

        get {
            call.respond(transaction { dao.schedule.index() })
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
            val _dto = SeedsDto.Schedule(-1, choreId, workHours, completeBy)
            val _response = transaction {
                service.schedule.create(_dto)
            }
            call.respond(_response)
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
            val _dto = SeedsDto.Schedule(id, choreId, workHours, completeBy)
            transaction {
                service.schedule.update(_dto)
            }
            call.respond(HttpStatusCode.OK)
        }

        delete("/{id}") {
            val id = call.parameters["id"]?.toInt() ?: error("Invalid delete request")
            transaction {
                service.schedule.destroy(id)
            }
            call.respond(HttpStatusCode.OK)
        }
    }
}
