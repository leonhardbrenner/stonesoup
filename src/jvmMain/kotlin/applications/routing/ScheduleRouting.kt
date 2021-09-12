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

class ScheduleRouting @Inject constructor(val dao: SeedsDao.Schedule, val service: SeedsService) {

    fun unmarshal(parameters: Parameters) = SeedsDto.Schedule(
        parameters["id"]?.toInt() ?: throw Exception("BadRequest"),
        parameters["choreId"]?.toInt() ?: throw Exception("BadRequest"),
        parameters["workHours"],
        parameters["completedBy"]
    )

    fun routes(routing: Routing) = routing.route(SeedsDto.Schedule.path) {

        get {
            call.respond(transaction { dao.index() })
        }

        post {
            call.respond(
                try {
                    transaction { service.schedule.create(unmarshal(call.parameters)) }
                } catch (ex: Exception) {
                    return@post call.respond(HttpStatusCode.BadRequest)
                }
            )
        }

        put("/{id}") {
            call.respond(
                try {
                    transaction { service.schedule.update(unmarshal(call.parameters)) }
                } catch (ex: Exception) {
                    return@put call.respond(HttpStatusCode.BadRequest)
                }
            )
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
