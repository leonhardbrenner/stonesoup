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

class SeedCategoryRouting @Inject constructor(val dao: SeedsDao.SeedCategory, val service: SeedsService) {

    fun unmarshal(parameters: Parameters) = SeedsDto.SeedCategory(
        parameters["id"]?.toInt() ?: -1,
    parameters["name"] ?:  throw Exception("BadRequest"),
    parameters["image"] ?:  throw Exception("BadRequest"),
    parameters["link"] ?:  throw Exception("BadRequest")
    )

    fun routes(routing: Routing) = routing.route(SeedsDto.SeedCategory.path) {

        get {
            call.respond(transaction { dao.index() } )
        }

        post {
            call.respond(
                try {
                    transaction { service.seedCategory.create(unmarshal(call.parameters)) }
                } catch (ex: Exception) {
                    return@post call.respond(HttpStatusCode.BadRequest)
                }
            )
        }

        put("/{id}") {
            call.respond(
                try {
                    transaction { service.seedCategory.update(unmarshal(call.parameters)) }
                } catch (ex: Exception) {
                    return@put call.respond(HttpStatusCode.BadRequest)
                }
            )
        }

        delete("/{id}") {
            val id = call.parameters["id"]?.toInt() ?: error("Invalid delete request")
            transaction {
                dao.destroy(id)
            }
            call.respond(HttpStatusCode.OK)
        }
    }
}
