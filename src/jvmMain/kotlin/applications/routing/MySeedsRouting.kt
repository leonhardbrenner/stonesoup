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

class MySeedsRouting @Inject constructor(val dao: SeedsDao, val service: SeedsService) {

    fun unmarshal(parameters: Parameters) = SeedsDto.MySeeds(
        parameters["id"]?.toInt() ?: -1, //Todo - create flag needed
        parameters["companyId"] ?: throw Exception("BadRequest"),
    parameters["seedId"] ?: throw Exception("BadRequest"),
    parameters["description"] ?: throw Exception("BadRequest"),
    parameters["germinationTest"] ?: throw Exception("BadRequest")
    )

    fun routes(routing: Routing) = routing.route(SeedsDto.MySeeds.path) {

        get {
            call.respond(transaction { service.mySeeds.index() })
        }

        post {
            call.respond(
                try {
                    transaction { service.mySeeds.create(unmarshal(call.parameters)) }
                } catch (ex: Exception) {
                    return@post call.respond(HttpStatusCode.BadRequest)
                }
            )
        }

        put("/{id}") {
            call.respond(
                try {
                    transaction { service.mySeeds.update(unmarshal(call.parameters)) }
                } catch (ex: Exception) {
                    return@put call.respond(HttpStatusCode.BadRequest)
                }
            )
        }

        delete("/{id}") {
            val id = call.parameters["id"]?.toInt() ?: error("Invalid delete request")
            transaction {
                service.mySeeds.destroy(id)
            }
            call.respond(HttpStatusCode.OK)
        }
    }
}
