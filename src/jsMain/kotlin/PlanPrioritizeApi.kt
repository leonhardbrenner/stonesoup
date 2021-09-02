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

//Todo - move these Apis to separate files
object PlanPrioritizeApi {

    suspend fun get(): List<SeedsDto.Chore> {
        return jsonClient.get(endpoint + SeedsDto.Chore.path)
    }

    suspend fun add(parentId: Int, name: String) {
        jsonClient.post<Unit>(endpoint + SeedsDto.Chore.path) {
            parameter("parentId", parentId)
            parameter("name", name)
        }
    }

    suspend fun move(id: Int, to: Int?) {
        jsonClient.put<Unit>(endpoint + SeedsDto.Chore.path + "/$id") {
            //Example of how to send a complexType
            //data class Node(val id: Int, val name: String)
            //contentType(ContentType.Application.Json)
            //body = Node(1, "Y")
            parameter("moveTo", to)
        }
    }

    suspend fun delete(choreId: Int) {
        jsonClient.delete<Unit>(endpoint + SeedsDto.Chore.path + "/${choreId}")
    }

}
