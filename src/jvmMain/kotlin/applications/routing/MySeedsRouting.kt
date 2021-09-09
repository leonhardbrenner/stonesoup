package applications.routing

import generated.model.SeedsDto
import io.ktor.application.*
import io.ktor.http.*
import io.ktor.response.*
import io.ktor.routing.*
import dao.SeedsDao
import models.SeedsResources
import org.jetbrains.exposed.sql.transactions.transaction
import services.SeedsService
import utils.then
import javax.inject.Inject

class MySeedsRouting @Inject constructor(val dao: SeedsDao, val service: SeedsService) {

    fun routes(routing: Routing) = routing.route(SeedsDto.MySeeds.path) {

        get {
            call.respond(transaction { service.mySeeds.index() })
        }

        //get {
        //    call.respond(dao.MySeeds.expandedIndex())
        //}

        //get("/new") {
        //    TODO("Show form to make new")
        //    //call.respond(collection.find().toList())
        //    //call.respond(dao.MySeeds.index())
        //}

        post {
            val companyId = call.parameters["companyId"] ?: return@post call.respond(HttpStatusCode.BadRequest)
            val seedId = call.parameters["seedId"] ?: return@post call.respond(HttpStatusCode.BadRequest)
            val description = call.parameters["description"] ?: return@post call.respond(HttpStatusCode.BadRequest)
            val germinationTest = call.parameters["germinationTest"] ?: return@post call.respond(HttpStatusCode.BadRequest)
            val _dto = SeedsDto.MySeeds(-1, companyId, seedId, description, germinationTest)
            val _response = transaction {
                service.mySeeds.create(_dto)
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
            val description = call.parameters["description"] ?: return@put call.respond(HttpStatusCode.BadRequest)
            val germinationTest = call.parameters["germinationTest"] ?: return@put call.respond(HttpStatusCode.BadRequest)
            val _dto = SeedsDto.MySeeds(id, companyId, seedId, description, germinationTest)
            transaction {
                service.mySeeds.update(_dto)
            }
            call.respond(HttpStatusCode.OK)
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
