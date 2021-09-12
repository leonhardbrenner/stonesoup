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

class DetailedSeedsRouting @Inject constructor(val dao: SeedsDao, val service: SeedsService) {

    fun unmarshal(parameters: Parameters) = SeedsDto.DetailedSeed(
        parameters["id"]?.toInt() ?: -1, //Todo - use a flag(create) with this
    parameters["companyId"] ?: throw Exception("BadRequest"),
    parameters["seedId"] ?: throw Exception("BadRequest"),
    parameters["name"] ?: throw Exception("BadRequest"),
    parameters["maturity"] ?: throw Exception("BadRequest"),
    parameters["secondary_name"] ?: throw Exception("BadRequest"),
    parameters["description"] ?: throw Exception("BadRequest"),
    parameters["image"] ?: throw Exception("BadRequest"),
    parameters["link"] ?: throw Exception("BadRequest")
    )

    fun routes(routing: Routing) = routing.route(SeedsDto.DetailedSeed.path) {

        get {
            call.respond(transaction { service.detailedSeeds.index() })
        }

        post {
            call.respond(
                try {
                    transaction { service.detailedSeeds.create(unmarshal(call.parameters)) }
                } catch (ex: Exception) {
                    return@post call.respond(HttpStatusCode.BadRequest)
                }
            )
        }

        put("/{id}") {
            call.respond(
                try {
                    transaction { service.detailedSeeds.update(unmarshal(call.parameters)) }
                } catch (ex: Exception) {
                    return@put call.respond(HttpStatusCode.BadRequest)
                }
            )
        }

        delete("/{id}") {
            val id = call.parameters["id"]?.toInt() ?: error("Invalid delete request")
            transaction {
                service.detailedSeeds.destroy(id)
            }
            call.respond(HttpStatusCode.OK)
        }
    }
}
