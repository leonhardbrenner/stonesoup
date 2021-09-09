package applications.routing

import generated.model.SeedsDto
import io.ktor.application.*
import io.ktor.http.*
import io.ktor.response.*
import io.ktor.routing.*
import dao.SeedsDao
import org.jetbrains.exposed.sql.transactions.transaction
import services.SeedsService
import javax.inject.Inject

class DetailedSeedsRouting @Inject constructor(val dao: SeedsDao, val service: SeedsService) {
    fun routes(routing: Routing) = routing.route(SeedsDto.DetailedSeed.path) {

        get {
            val response = transaction {
                service.detailedSeeds.index()
            }
            call.respond(response)
        }

        //get("/new") {
        //    TODO("Show form to make new")
        //    //call.respond(collection.find().toList())
        //    //call.respond(dao.DetailedSeed.index())
        //}

        post {
            val companyId = call.parameters["companyId"] ?: return@post call.respond(HttpStatusCode.BadRequest)
            val seedId = call.parameters["seedId"] ?: return@post call.respond(HttpStatusCode.BadRequest)
            val name = call.parameters["name"] ?: return@post call.respond(HttpStatusCode.BadRequest)
            //XXX - none of these are required but our create 6 lines down requires it.
            val maturity = call.parameters["maturity"] ?: return@post call.respond(HttpStatusCode.BadRequest)
            val secondaryName = call.parameters["name"] ?: return@post call.respond(HttpStatusCode.BadRequest)
            val description = call.parameters["description"] ?: return@post call.respond(HttpStatusCode.BadRequest)
            val image = call.parameters["image"] ?: return@post call.respond(HttpStatusCode.BadRequest)
            val link = call.parameters["link"] ?: return@post call.respond(HttpStatusCode.BadRequest)
            val _dto = SeedsDto.DetailedSeed(-1, companyId, seedId, name, maturity, secondaryName, description, image, link)
            val _response = transaction {
                service.detailedSeeds.create(_dto)
            }
            call.respond(_response)
        }

        //get("/{id}") {
        //    TODO("Lookup Chore by id")
        //}

        //get("/{id}/edit") {
        //    TODO("Show form to update Chore")
        //}

        put("/{id}") {
            val id = call.parameters["id"]?.toInt() ?: return@put call.respond(HttpStatusCode.BadRequest)
            val companyId = call.parameters["companyId"] ?: return@put call.respond(HttpStatusCode.BadRequest)
            val seedId = call.parameters["seedId"] ?: return@put call.respond(HttpStatusCode.BadRequest)
            val name = call.parameters["name"] ?: return@put call.respond(HttpStatusCode.BadRequest)
            val maturity = call.parameters["maturity"] ?: return@put call.respond(HttpStatusCode.BadRequest)
            val secondaryName = call.parameters["secondary_name"] ?: return@put call.respond(HttpStatusCode.BadRequest)
            val description = call.parameters["description"] ?: return@put call.respond(HttpStatusCode.BadRequest)
            val image = call.parameters["image"] ?: return@put call.respond(HttpStatusCode.BadRequest)
            val link = call.parameters["link"] ?: return@put call.respond(HttpStatusCode.BadRequest)
            val _dto = SeedsDto.DetailedSeed(id, companyId, seedId, name, maturity, secondaryName, description, image, link)
            transaction {
                service.detailedSeeds.update(_dto)
            }
            call.respond(HttpStatusCode.OK)
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
