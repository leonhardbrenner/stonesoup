import generated.model.SeedsDto
import io.ktor.http.*
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

    suspend fun add(chore: ChoreCreate) {
        jsonClient.post<Unit>(endpoint + SeedsDto.Chore.path) {
            contentType(ContentType.Application.Json)
            body = chore
        }
    }

    suspend fun move(id: Int, to: Int?) {
        jsonClient.put<Unit>(endpoint + SeedsDto.Chore.path + "/$id") {
            //Note: the goal is to avoid the extra object but if that gets clumsy we could go back to this.
            //contentType(ContentType.Application.Json)
            //body = node
            parameter("moveTo", to)
        }
    }

    suspend fun delete(choreId: ChoreId) {
        jsonClient.delete<Unit>(endpoint + SeedsDto.Chore.path + "/${choreId}")
    }

}
