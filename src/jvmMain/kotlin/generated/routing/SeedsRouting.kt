package generated.routing

import generated.model.SeedsDto
import io.ktor.routing.Routing
import io.ktor.routing.route

class SeedsRouting {
  class Chore {
    fun routes(routing: Routing) = routing.route(SeedsDto.Chore.path) {
       TODO("Generate routes but first consider refactoring API so create and update pass entity.")
    }
  }

  class DetailedSeed {
    fun routes(routing: Routing) = routing.route(SeedsDto.DetailedSeed.path) {
       TODO("Generate routes but first consider refactoring API so create and update pass entity.")
    }
  }

  class MySeeds {
    fun routes(routing: Routing) = routing.route(SeedsDto.MySeeds.path) {
       TODO("Generate routes but first consider refactoring API so create and update pass entity.")
    }
  }

  class Schedule {
    fun routes(routing: Routing) = routing.route(SeedsDto.Schedule.path) {
       TODO("Generate routes but first consider refactoring API so create and update pass entity.")
    }
  }

  class SeedCategory {
    fun routes(routing: Routing) = routing.route(SeedsDto.SeedCategory.path) {
       TODO("Generate routes but first consider refactoring API so create and update pass entity.")
    }
  }
}
