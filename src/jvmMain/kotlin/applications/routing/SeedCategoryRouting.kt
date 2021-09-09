package applications.routing

import generated.model.SeedsDto
import io.ktor.application.*
import io.ktor.http.*
import io.ktor.response.*
import io.ktor.routing.*
import dao.SeedsDao
import org.jetbrains.exposed.sql.transactions.transaction
import javax.inject.Inject

class SeedCategoryRouting @Inject constructor(val dao: SeedsDao) {
    fun routes(routing: Routing) = routing.route(SeedsDto.SeedCategory.path) {

        get {
            call.respond(transaction { dao.SeedCategory.index() } )
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
            transaction {
                dao.SeedCategory.create(
                    SeedsDto.SeedCategory(-1, name, image, link)
                )
            }
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
            transaction {
                dao.SeedCategory.update(SeedsDto.SeedCategory(id, name, image, link))
            }
            call.respond(HttpStatusCode.OK)
        }

        delete("/{id}") {
            val id = call.parameters["id"]?.toInt() ?: error("Invalid delete request")
            transaction {
                dao.SeedCategory.destroy(id)
            }
            call.respond(HttpStatusCode.OK)
        }
    }
}
