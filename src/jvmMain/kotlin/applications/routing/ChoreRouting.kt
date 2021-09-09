package applications.routing

import generated.model.SeedsDto
import io.ktor.application.*
import io.ktor.http.*
import io.ktor.response.*
import io.ktor.routing.*
import dao.SeedsDao
import dao.seeds.ChoreDao
import models.SeedsResources
import org.jetbrains.exposed.sql.transactions.transaction
import services.SeedsService
import javax.inject.Inject

class ChoreRouting @Inject constructor(val dao: SeedsDao, val service: SeedsService) {

    fun routes(routing: Routing) = routing.route(SeedsDto.Chore.path) {

        //Todo - Consider doing the work in the db like this.
        //fun indexExtended() = transaction {
        //    //Nice exposed example:
        //    //https://github.com/JetBrains/Exposed/issues/566
        //    with(
        //        SeedsDb.Chore.Table.join(
        //            SeedsDb.Schedule.Table,
        //            JoinType.LEFT,
        //            additionalConstraint = {
        //                SeedsDb.Chore.Table.id eq SeedsDb.Schedule.Table.choreId
        //            }
        //        )
        //    ) {
        //        selectAll().map {
        //            val schedule = if (it[SeedsDb.Schedule.Table.id] != null)
        //                SeedsDb.Schedule.create(it)
        //            else
        //                null
        //            SeedsResources.Chore(SeedsDb.Chore.create(it), schedule)
        //        }
        //    }
        //}
        get {
            val response = transaction { service.chore.index() }
            call.respond(response)
        }


        //get("/new") {
        //    TODO("Show form to make new Chore")
        //    //call.respond(collection.find().toList())
        //    //call.respond(dao.Chore.index())
        //}

        post {
            val parentId = call.parameters["parentId"]?.toInt() ?: return@post call.respond(HttpStatusCode.BadRequest)
            val name = call.parameters["name"]?: return@post call.respond(HttpStatusCode.BadRequest)
            val response = transaction {
                service.chore.create(SeedsDto.Chore(-1, parentId, "", name))
            }
            call.respond(response)
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

        //XXX - Fix this
        //put("/{id}") {
        //    val id = call.parameters["id"]?.toInt() ?: return@put call.respond(HttpStatusCode.BadRequest)
        //    val parentId = call.parameters["parentId"]?.toInt() ?: return@put call.respond(HttpStatusCode.BadRequest)
        //    val name = call.parameters["name"]
        //    dao.Chore.update(id, SeedsDto.Chore(id, parentId, name))
        //    call.respond(HttpStatusCode.OK)
        //}

        put("/{id}") {
            val id = call.parameters["id"]?.toInt() ?: return@put call.respond(HttpStatusCode.BadRequest)
            val parentId = call.parameters["parentId"]?.toInt() ?: return@put call.respond(HttpStatusCode.BadRequest)
            transaction {
                service.chore.move(id, parentId)
            }
            call.respond(HttpStatusCode.OK)
        }

        delete("/{id}") {
            val id = call.parameters["id"]?.toInt() ?: error("Invalid delete request")
            transaction {
                service.chore.destroy(id)
            }
            call.respond(HttpStatusCode.OK)
        }
    }
}
