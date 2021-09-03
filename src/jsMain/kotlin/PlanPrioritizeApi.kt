import generated.model.SeedsDto
import io.ktor.client.*
import io.ktor.client.request.*
import io.ktor.client.features.json.JsonFeature
import io.ktor.client.features.json.serializer.KotlinxSerializer

import kotlinx.browser.window
import models.*

val endpoint = window.location.origin // only needed until https://github.com/ktorio/ktor/issues/1695 is resolved

val jsonClient = HttpClient {
    install(JsonFeature) { serializer = KotlinxSerializer() }
}

//https://medium.com/@shubhangirajagrawal/the-7-restful-routes-a8e84201f206
object Api {

    object Chore {
        suspend fun index(): List<SeedsDto.Chore> {
            return jsonClient.get(endpoint + SeedsDto.Chore.path)
        }

        suspend fun create(parentId: Int, name: String) {
            jsonClient.post<Unit>(endpoint + SeedsDto.Chore.path) {
                parameter("parentId", parentId)
                parameter("name", name)
            }
        }

//suspend fun //Todo - implement create which has form data defaults. Make a template base class for this.

        //Todo - implement edit which has loads existing values

        suspend fun update(id: Int, parentId: Int?, name: String?) {
            jsonClient.put<Unit>(endpoint + SeedsDto.Chore.path + "/$id") {
                //Example of how to send a complexType
                //data class Node(val id: Int, val name: String)
                //contentType(ContentType.Application.Json)
                //body = Node(1, "Y")
                parameter("parentId", parentId)
                parameter("name", name)
            }
        }

        suspend fun delete(choreId: Int) {
            jsonClient.delete<Unit>(endpoint + SeedsDto.Chore.path + "/${choreId}")
        }
    }

    object MySeeds {
        suspend fun index(): List<Resources.MySeeds> {
            return jsonClient.get(endpoint + SeedsDto.MySeeds.path)
        }
    }

    object DetailedSeed {
        suspend fun index(): List<SeedsDto.DetailedSeed> {
            return jsonClient.get(endpoint + SeedsDto.DetailedSeed.path)
        }
    }

    object Category {
        suspend fun index(): List<SeedsDto.SeedCategory> {
            return jsonClient.get(endpoint + SeedsDto.SeedCategory.path)
        }
    }

}
