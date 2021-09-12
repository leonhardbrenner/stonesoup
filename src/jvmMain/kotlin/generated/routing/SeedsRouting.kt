package generated.routing

import generated.dao.SeedsDao
import generated.model.SeedsDto
import io.ktor.application.call
import io.ktor.http.HttpStatusCode
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
    fun routes(routing: Routing) = routing.route(SeedsDto.Chore.path) {

        get {
            call.respond(transaction { dao.index() })
        }

        //get("/new") {
        //    TODO("Show form to make new")
        //    //call.respond(collection.find().toList())
        //    //call.respond(dao.Schedule.index())
        //}

        post {
            val choreId = call.parameters["choreId"]?.toInt() ?: return@post
        call.respond(HttpStatusCode.BadRequest)
            val workHours = call.parameters["workHours"]
            val completeBy = call.parameters["completeBy"]
            val _dto = SeedsDto.Chore(-1, choreId, workHours, completeBy)
            val _response = transaction {
                service.create(_dto)
            }
            call.respond(_response)
        }

        //get("/{id}") {
        //    TODO("Lookup Schedule by id")
        //}

        //get("/{id}/edit") {
        //}

        put("/{id}") {
            val id = call.parameters["id"]?.toInt() ?: return@put
        call.respond(HttpStatusCode.BadRequest)
            val choreId = call.parameters["choreId"]?.toInt() ?: return@put
        call.respond(HttpStatusCode.BadRequest)
            val workHours = call.parameters["workHours"]
            val completeBy = call.parameters["completeBy"]
            val _dto = SeedsDto.Chore(id, choreId, workHours, completeBy)
            transaction {
                service.update(_dto)
            }
            call.respond(HttpStatusCode.OK)
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
    fun routes(routing: Routing) = routing.route(SeedsDto.DetailedSeed.path) {

        get {
            call.respond(transaction { dao.index() })
        }

        //get("/new") {
        //    TODO("Show form to make new")
        //    //call.respond(collection.find().toList())
        //    //call.respond(dao.Schedule.index())
        //}

        post {
            val choreId = call.parameters["choreId"]?.toInt() ?: return@post
        call.respond(HttpStatusCode.BadRequest)
            val workHours = call.parameters["workHours"]
            val completeBy = call.parameters["completeBy"]
            val _dto = SeedsDto.DetailedSeed(-1, choreId, workHours, completeBy)
            val _response = transaction {
                service.create(_dto)
            }
            call.respond(_response)
        }

        //get("/{id}") {
        //    TODO("Lookup Schedule by id")
        //}

        //get("/{id}/edit") {
        //}

        put("/{id}") {
            val id = call.parameters["id"]?.toInt() ?: return@put
        call.respond(HttpStatusCode.BadRequest)
            val choreId = call.parameters["choreId"]?.toInt() ?: return@put
        call.respond(HttpStatusCode.BadRequest)
            val workHours = call.parameters["workHours"]
            val completeBy = call.parameters["completeBy"]
            val _dto = SeedsDto.DetailedSeed(id, choreId, workHours, completeBy)
            transaction {
                service.update(_dto)
            }
            call.respond(HttpStatusCode.OK)
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
    fun routes(routing: Routing) = routing.route(SeedsDto.MySeeds.path) {

        get {
            call.respond(transaction { dao.index() })
        }

        //get("/new") {
        //    TODO("Show form to make new")
        //    //call.respond(collection.find().toList())
        //    //call.respond(dao.Schedule.index())
        //}

        post {
            val choreId = call.parameters["choreId"]?.toInt() ?: return@post
        call.respond(HttpStatusCode.BadRequest)
            val workHours = call.parameters["workHours"]
            val completeBy = call.parameters["completeBy"]
            val _dto = SeedsDto.MySeeds(-1, choreId, workHours, completeBy)
            val _response = transaction {
                service.create(_dto)
            }
            call.respond(_response)
        }

        //get("/{id}") {
        //    TODO("Lookup Schedule by id")
        //}

        //get("/{id}/edit") {
        //}

        put("/{id}") {
            val id = call.parameters["id"]?.toInt() ?: return@put
        call.respond(HttpStatusCode.BadRequest)
            val choreId = call.parameters["choreId"]?.toInt() ?: return@put
        call.respond(HttpStatusCode.BadRequest)
            val workHours = call.parameters["workHours"]
            val completeBy = call.parameters["completeBy"]
            val _dto = SeedsDto.MySeeds(id, choreId, workHours, completeBy)
            transaction {
                service.update(_dto)
            }
            call.respond(HttpStatusCode.OK)
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
    fun routes(routing: Routing) = routing.route(SeedsDto.Schedule.path) {

        get {
            call.respond(transaction { dao.index() })
        }

        //get("/new") {
        //    TODO("Show form to make new")
        //    //call.respond(collection.find().toList())
        //    //call.respond(dao.Schedule.index())
        //}

        post {
            val choreId = call.parameters["choreId"]?.toInt() ?: return@post
        call.respond(HttpStatusCode.BadRequest)
            val workHours = call.parameters["workHours"]
            val completeBy = call.parameters["completeBy"]
            val _dto = SeedsDto.Schedule(-1, choreId, workHours, completeBy)
            val _response = transaction {
                service.create(_dto)
            }
            call.respond(_response)
        }

        //get("/{id}") {
        //    TODO("Lookup Schedule by id")
        //}

        //get("/{id}/edit") {
        //}

        put("/{id}") {
            val id = call.parameters["id"]?.toInt() ?: return@put
        call.respond(HttpStatusCode.BadRequest)
            val choreId = call.parameters["choreId"]?.toInt() ?: return@put
        call.respond(HttpStatusCode.BadRequest)
            val workHours = call.parameters["workHours"]
            val completeBy = call.parameters["completeBy"]
            val _dto = SeedsDto.Schedule(id, choreId, workHours, completeBy)
            transaction {
                service.update(_dto)
            }
            call.respond(HttpStatusCode.OK)
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
    fun routes(routing: Routing) = routing.route(SeedsDto.SeedCategory.path) {

        get {
            call.respond(transaction { dao.index() })
        }

        //get("/new") {
        //    TODO("Show form to make new")
        //    //call.respond(collection.find().toList())
        //    //call.respond(dao.Schedule.index())
        //}

        post {
            val choreId = call.parameters["choreId"]?.toInt() ?: return@post
        call.respond(HttpStatusCode.BadRequest)
            val workHours = call.parameters["workHours"]
            val completeBy = call.parameters["completeBy"]
            val _dto = SeedsDto.SeedCategory(-1, choreId, workHours, completeBy)
            val _response = transaction {
                service.create(_dto)
            }
            call.respond(_response)
        }

        //get("/{id}") {
        //    TODO("Lookup Schedule by id")
        //}

        //get("/{id}/edit") {
        //}

        put("/{id}") {
            val id = call.parameters["id"]?.toInt() ?: return@put
        call.respond(HttpStatusCode.BadRequest)
            val choreId = call.parameters["choreId"]?.toInt() ?: return@put
        call.respond(HttpStatusCode.BadRequest)
            val workHours = call.parameters["workHours"]
            val completeBy = call.parameters["completeBy"]
            val _dto = SeedsDto.SeedCategory(id, choreId, workHours, completeBy)
            transaction {
                service.update(_dto)
            }
            call.respond(HttpStatusCode.OK)
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
