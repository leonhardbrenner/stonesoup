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

class ChoreRouting @Inject constructor(val dao: SeedsDao, val service: SeedsService) {

    fun unmarshal(parameters: Parameters, moveException: Boolean = false) = SeedsDto.Chore(
        parameters["id"]?.toInt() ?: throw Exception("BadRequest"),
        parameters["parentId"]?.toInt() ?: throw Exception("BadRequest"),
        parameters["name"] ?: (if (moveException) "<UNDEFINED>" else throw Exception("BadRequest"))
    )

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
            call.respond(transaction { service.chore.index() })
        }

        post {
            call.respond(
                try {
                    transaction { service.chore.create(unmarshal(call.parameters)) }
                } catch (ex: Exception) {
                    return@post call.respond(HttpStatusCode.BadRequest)
                }
            )
        }

        put("/{id}") {
            call.respond(
                try {
                    transaction { unmarshal(call.parameters, true).apply { service.chore.move(id, parentId) } }
                } catch (ex: Exception) {
                    return@put call.respond(HttpStatusCode.BadRequest)
                }
            )
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
