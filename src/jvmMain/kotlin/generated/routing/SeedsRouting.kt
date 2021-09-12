//package genera//import generated.d//import generated.mod//import io.ktor.appli//import io.ktor.http.Htt//import io.ktor.respo//import io.ktor.rout//import io.ktor.rou//import io.ktor.//import io.ktor.r//import io.ktor.//import io.ktor.ro//import javax.in//import org.jetbrains.exposed.sql.transactions.//import services.seeds.C//import services.seeds.Detailed//import services.seeds.MyS//import services.seeds.Sche//import services.seeds.SeedCate//class See//  class Chore @Inject c//    val dao: Seed//    val service: C//    fun routes(routing: Routing) = routing.route(SeedsDto.Ch//  //            call.respond(transaction { dao.index() })
//        }
//
//        //get("/new") {
//        //    TODO("Show form to make new")
//        //    //call.respond(collection.find().toList())
//        //    //call.respond(dao.Schedule.index())
//        //}
//
//   //            val choreId = call.parameters["choreId"]?.toInt() ?: return@post
//        call.respond(HttpStatusCode.BadRequest)
//            val workHours = call.parameters["workHours"]
//            val completeBy = call.parameters["completeBy"]
//            val _dto = SeedsDto.Chore(-1, choreId, workHours, completeBy)
//            val _response = tr//                service.create(_dto)
//            }
//            call.respond(_response)
//        }
//
//        //get("/{id}") {
//        //    TODO("Lookup Schedule by id")
//        //}
//
//        //get("/{id}/edit") {
//        //}
//
//        put//            val id = call.parameters["id"]?.toInt() ?: return@put
//        call.respond(HttpStatusCode.BadRequest)
//            val choreId = call.parameters["choreId"]?.toInt() ?: return@put
//        call.respond(HttpStatusCode.BadRequest)
//            val workHours = call.parameters["workHours"]
//            val completeBy = call.parameters["completeBy"]
//            val _dto = SeedsDto.Chore(id, choreId, workHours, completeBy)
//            tr//                service.update(_dto)
//            }
//            call.respond(HttpStatusCode.OK)
//        }
//
//        delete//            val id = call.parameters["id"]?.toInt() ?: error("Invalid delete request")
//            tr//                service.destroy(id)
//            }
//            call.respond(HttpStatusCode.OK)
//        }
//
//    }
//  class DetailedSeed @Inject c//    val dao: SeedsDao.De//    val service: Detailed//    fun routes(routing: Routing) = routing.route(SeedsDto.DetailedS//  //            call.respond(transaction { dao.index() })
//        }
//
//        //get("/new") {
//        //    TODO("Show form to make new")
//        //    //call.respond(collection.find().toList())
//        //    //call.respond(dao.Schedule.index())
//        //}
//
//   //            val choreId = call.parameters["choreId"]?.toInt() ?: return@post
//        call.respond(HttpStatusCode.BadRequest)
//            val workHours = call.parameters["workHours"]
//            val completeBy = call.parameters["completeBy"]
//            val _dto = SeedsDto.DetailedSeed(-1, choreId, workHours, completeBy)
//            val _response = tr//                service.create(_dto)
//            }
//            call.respond(_response)
//        }
//
//        //get("/{id}") {
//        //    TODO("Lookup Schedule by id")
//        //}
//
//        //get("/{id}/edit") {
//        //}
//
//        put//            val id = call.parameters["id"]?.toInt() ?: return@put
//        call.respond(HttpStatusCode.BadRequest)
//            val choreId = call.parameters["choreId"]?.toInt() ?: return@put
//        call.respond(HttpStatusCode.BadRequest)
//            val workHours = call.parameters["workHours"]
//            val completeBy = call.parameters["completeBy"]
//            val _dto = SeedsDto.DetailedSeed(id, choreId, workHours, completeBy)
//            tr//                service.update(_dto)
//            }
//            call.respond(HttpStatusCode.OK)
//        }
//
//        delete//            val id = call.parameters["id"]?.toInt() ?: error("Invalid delete request")
//            tr//                service.destroy(id)
//            }
//            call.respond(HttpStatusCode.OK)
//        }
//
//    }
//  class MySeeds @Inject c//    val dao: SeedsD//    val service: MyS//    fun routes(routing: Routing) = routing.route(SeedsDto.MySe//  //            call.respond(transaction { dao.index() })
//        }
//
//        //get("/new") {
//        //    TODO("Show form to make new")
//        //    //call.respond(collection.find().toList())
//        //    //call.respond(dao.Schedule.index())
//        //}
//
//   //            val choreId = call.parameters["choreId"]?.toInt() ?: return@post
//        call.respond(HttpStatusCode.BadRequest)
//            val workHours = call.parameters["workHours"]
//            val completeBy = call.parameters["completeBy"]
//            val _dto = SeedsDto.MySeeds(-1, choreId, workHours, completeBy)
//            val _response = tr//                service.create(_dto)
//            }
//            call.respond(_response)
//        }
//
//        //get("/{id}") {
//        //    TODO("Lookup Schedule by id")
//        //}
//
//        //get("/{id}/edit") {
//        //}
//
//        put//            val id = call.parameters["id"]?.toInt() ?: return@put
//        call.respond(HttpStatusCode.BadRequest)
//            val choreId = call.parameters["choreId"]?.toInt() ?: return@put
//        call.respond(HttpStatusCode.BadRequest)
//            val workHours = call.parameters["workHours"]
//            val completeBy = call.parameters["completeBy"]
//            val _dto = SeedsDto.MySeeds(id, choreId, workHours, completeBy)
//            tr//                service.update(_dto)
//            }
//            call.respond(HttpStatusCode.OK)
//        }
//
//        delete//            val id = call.parameters["id"]?.toInt() ?: error("Invalid delete request")
//            tr//                service.destroy(id)
//            }
//            call.respond(HttpStatusCode.OK)
//        }
//
//    }
//  class Schedule @Inject c//    val dao: SeedsDa//    val service: Sche//    fun routes(routing: Routing) = routing.route(SeedsDto.Sched//  //            call.respond(transaction { dao.index() })
//        }
//
//        //get("/new") {
//        //    TODO("Show form to make new")
//        //    //call.respond(collection.find().toList())
//        //    //call.respond(dao.Schedule.index())
//        //}
//
//   //            val choreId = call.parameters["choreId"]?.toInt() ?: return@post
//        call.respond(HttpStatusCode.BadRequest)
//            val workHours = call.parameters["workHours"]
//            val completeBy = call.parameters["completeBy"]
//            val _dto = SeedsDto.Schedule(-1, choreId, workHours, completeBy)
//            val _response = tr//                service.create(_dto)
//            }
//            call.respond(_response)
//        }
//
//        //get("/{id}") {
//        //    TODO("Lookup Schedule by id")
//        //}
//
//        //get("/{id}/edit") {
//        //}
//
//        put//            val id = call.parameters["id"]?.toInt() ?: return@put
//        call.respond(HttpStatusCode.BadRequest)
//            val choreId = call.parameters["choreId"]?.toInt() ?: return@put
//        call.respond(HttpStatusCode.BadRequest)
//            val workHours = call.parameters["workHours"]
//            val completeBy = call.parameters["completeBy"]
//            val _dto = SeedsDto.Schedule(id, choreId, workHours, completeBy)
//            tr//                service.update(_dto)
//            }
//            call.respond(HttpStatusCode.OK)
//        }
//
//        delete//            val id = call.parameters["id"]?.toInt() ?: error("Invalid delete request")
//            tr//                service.destroy(id)
//            }
//            call.respond(HttpStatusCode.OK)
//        }
//
//    }
//  class SeedCategory @Inject c//    val dao: SeedsDao.Se//    val service: SeedCate//    fun routes(routing: Routing) = routing.route(SeedsDto.SeedCateg//  //            call.respond(transaction { dao.index() })
//        }
//
//        //get("/new") {
//        //    TODO("Show form to make new")
//        //    //call.respond(collection.find().toList())
//        //    //call.respond(dao.Schedule.index())
//        //}
//
//   //            val choreId = call.parameters["choreId"]?.toInt() ?: return@post
//        call.respond(HttpStatusCode.BadRequest)
//            val workHours = call.parameters["workHours"]
//            val completeBy = call.parameters["completeBy"]
//            val _dto = SeedsDto.SeedCategory(-1, choreId, workHours, completeBy)
//            val _response = tr//                service.create(_dto)
//            }
//            call.respond(_response)
//        }
//
//        //get("/{id}") {
//        //    TODO("Lookup Schedule by id")
//        //}
//
//        //get("/{id}/edit") {
//        //}
//
//        put//            val id = call.parameters["id"]?.toInt() ?: return@put
//        call.respond(HttpStatusCode.BadRequest)
//            val choreId = call.parameters["choreId"]?.toInt() ?: return@put
//        call.respond(HttpStatusCode.BadRequest)
//            val workHours = call.parameters["workHours"]
//            val completeBy = call.parameters["completeBy"]
//            val _dto = SeedsDto.SeedCategory(id, choreId, workHours, completeBy)
//            tr//                service.update(_dto)
//            }
//            call.respond(HttpStatusCode.OK)
//        }
//
//        delete//            val id = call.parameters["id"]?.toInt() ?: error("Invalid delete request")
//            tr//                service.destroy(id)
//            }
//            call.respond(HttpStatusCode.OK)
//        }
//
//    }
//}
