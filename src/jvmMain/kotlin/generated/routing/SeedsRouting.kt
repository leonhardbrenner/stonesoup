package generated.routing

import generated.dao.SeedsDao
import generated.model.SeedsDto
import generated.model.SeedsDto.Chore
import generated.model.SeedsDto.DetailedSeed
import generated.model.SeedsDto.MySeeds
import generated.model.SeedsDto.Schedule
import generated.model.SeedsDto.SeedCategory
import io.ktor.application.call
import io.ktor.http.HttpStatusCode
import io.ktor.http.Parameters
import io.ktor.response.respond
import io.ktor.routing.Routing
import io.ktor.routing.delete
import io.ktor.routing.get
import io.ktor.routing.post
import io.ktor.routing.put
import io.ktor.routing.route
import javax.inject.Inject
import org.jetbrains.exposed.sql.transactions.transaction
import services.seeds.ChoreService
import services.seeds.DetailedSeedService
import services.seeds.MySeedsService
import services.seeds.ScheduleService
import services.seeds.SeedCategoryService

class SeedsRouting {
  class Chore @Inject constructor(
    val dao: SeedsDao.Chore,
    val service: ChoreService
  ) {
    fun unmarshal(parameters: Parameters) = SeedsDto.Chore(
    parameters["id"]?.toInt()?: -1, 
    parameters["parentId"]?.toInt() ?: throw Exception("BadRequest"), 
    parameters["name"] ?: throw Exception("BadRequest"))

    fun routes(routing: Routing) = routing.route(SeedsDto.Chore.path) {
     
        get {
            call.respond(transaction { service.index() })
        }
        
        post {
            call.respond(
                try {
                    transaction { service.create(unmarshal(call.parameters)) }
                } catch (ex: Exception) {
                    return@post call.respond(HttpStatusCode.BadRequest)
                }
            )
        }

        put("/{id}/move") {
            val id = call.parameters["id"]?.toInt()
                ?: return@put call.respond(HttpStatusCode.BadRequest)
            val parentId = call.parameters["parentId"]?.toInt()
                ?: return@put call.respond(HttpStatusCode.BadRequest)
            val response = transaction { service.move(id, parentId) }
            call.respond(response)
        }
        
        put("/{id}") {
            call.respond(
                try {
                    transaction { service.update(unmarshal(call.parameters)) }
                } catch (ex: Exception) {
                    return@put call.respond(HttpStatusCode.BadRequest)
                }
            )
        }
        
        delete("/{id}") {
            val id = call.parameters["id"]?.toInt() ?: error("Invalid delete request")
            transaction {
                service.destroy(id)
            }
            call.respond(HttpStatusCode.OK)
        }
    }
  }

  class DetailedSeed @Inject constructor(
    val dao: SeedsDao.DetailedSeed,
    val service: DetailedSeedService
  ) {
    fun unmarshal(parameters: Parameters) = SeedsDto.DetailedSeed(
    parameters["id"]?.toInt()?: -1, 
    parameters["companyId"] ?: throw Exception("BadRequest"), 
    parameters["seedId"] ?: throw Exception("BadRequest"), 
    parameters["name"] ?: throw Exception("BadRequest"), 
    parameters["maturity"], 
    parameters["secondaryName"], 
    parameters["description"], 
    parameters["image"], 
    parameters["link"])

    fun routes(routing: Routing) = routing.route(SeedsDto.DetailedSeed.path) {
     
        get {
            call.respond(transaction { service.index() })
        }
        
        post {
            call.respond(
                try {
                    transaction { service.create(unmarshal(call.parameters)) }
                } catch (ex: Exception) {
                    return@post call.respond(HttpStatusCode.BadRequest)
                }
            )
        }


        
        put("/{id}") {
            call.respond(
                try {
                    transaction { service.update(unmarshal(call.parameters)) }
                } catch (ex: Exception) {
                    return@put call.respond(HttpStatusCode.BadRequest)
                }
            )
        }
        
        delete("/{id}") {
            val id = call.parameters["id"]?.toInt() ?: error("Invalid delete request")
            transaction {
                service.destroy(id)
            }
            call.respond(HttpStatusCode.OK)
        }
    }
  }

  class MySeeds @Inject constructor(
    val dao: SeedsDao.MySeeds,
    val service: MySeedsService
  ) {
    fun unmarshal(parameters: Parameters) = SeedsDto.MySeeds(
    parameters["id"]?.toInt()?: -1, 
    parameters["companyId"] ?: throw Exception("BadRequest"), 
    parameters["seedId"] ?: throw Exception("BadRequest"), 
    parameters["description"] ?: throw Exception("BadRequest"), 
    parameters["germinationTest"] ?: throw Exception("BadRequest"))

    fun routes(routing: Routing) = routing.route(SeedsDto.MySeeds.path) {
     
        get {
            call.respond(transaction { service.index() })
        }
        
        post {
            call.respond(
                try {
                    transaction { service.create(unmarshal(call.parameters)) }
                } catch (ex: Exception) {
                    return@post call.respond(HttpStatusCode.BadRequest)
                }
            )
        }


        
        put("/{id}") {
            call.respond(
                try {
                    transaction { service.update(unmarshal(call.parameters)) }
                } catch (ex: Exception) {
                    return@put call.respond(HttpStatusCode.BadRequest)
                }
            )
        }
        
        delete("/{id}") {
            val id = call.parameters["id"]?.toInt() ?: error("Invalid delete request")
            transaction {
                service.destroy(id)
            }
            call.respond(HttpStatusCode.OK)
        }
    }
  }

  class Schedule @Inject constructor(
    val dao: SeedsDao.Schedule,
    val service: ScheduleService
  ) {
    fun unmarshal(parameters: Parameters) = SeedsDto.Schedule(
    parameters["id"]?.toInt()?: -1, 
    parameters["choreId"]?.toInt() ?: throw Exception("BadRequest"), 
    parameters["workHours"], 
    parameters["completeBy"])

    fun routes(routing: Routing) = routing.route(SeedsDto.Schedule.path) {
     
        get {
            call.respond(transaction { service.index() })
        }
        
        post {
            call.respond(
                try {
                    transaction { service.create(unmarshal(call.parameters)) }
                } catch (ex: Exception) {
                    return@post call.respond(HttpStatusCode.BadRequest)
                }
            )
        }


        
        put("/{id}") {
            call.respond(
                try {
                    transaction { service.update(unmarshal(call.parameters)) }
                } catch (ex: Exception) {
                    return@put call.respond(HttpStatusCode.BadRequest)
                }
            )
        }
        
        delete("/{id}") {
            val id = call.parameters["id"]?.toInt() ?: error("Invalid delete request")
            transaction {
                service.destroy(id)
            }
            call.respond(HttpStatusCode.OK)
        }
    }
  }

  class SeedCategory @Inject constructor(
    val dao: SeedsDao.SeedCategory,
    val service: SeedCategoryService
  ) {
    fun unmarshal(parameters: Parameters) = SeedsDto.SeedCategory(
    parameters["id"]?.toInt()?: -1, 
    parameters["name"] ?: throw Exception("BadRequest"), 
    parameters["image"] ?: throw Exception("BadRequest"), 
    parameters["link"] ?: throw Exception("BadRequest"))

    fun routes(routing: Routing) = routing.route(SeedsDto.SeedCategory.path) {
     
        get {
            call.respond(transaction { service.index() })
        }
        
        post {
            call.respond(
                try {
                    transaction { service.create(unmarshal(call.parameters)) }
                } catch (ex: Exception) {
                    return@post call.respond(HttpStatusCode.BadRequest)
                }
            )
        }


        
        put("/{id}") {
            call.respond(
                try {
                    transaction { service.update(unmarshal(call.parameters)) }
                } catch (ex: Exception) {
                    return@put call.respond(HttpStatusCode.BadRequest)
                }
            )
        }
        
        delete("/{id}") {
            val id = call.parameters["id"]?.toInt() ?: error("Invalid delete request")
            transaction {
                service.destroy(id)
            }
            call.respond(HttpStatusCode.OK)
        }
    }
  }
}
