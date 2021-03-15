import generated.model.SeedsDto
import io.ktor.http.*
import io.ktor.client.*
import io.ktor.client.request.*
import io.ktor.client.features.json.JsonFeature
import io.ktor.client.features.json.serializer.KotlinxSerializer

import kotlinx.browser.window
import models.Resources
import models.Chore
import models.ChoreUpdate

val endpoint = window.location.origin // only needed until https://github.com/ktorio/ktor/issues/1695 is resolved

val jsonClient = HttpClient {
    install(JsonFeature) { serializer = KotlinxSerializer() }
}

//Todo - move these Apis to separate files
object FarmPrioritiesApi {

    suspend fun get(): List<Chore>
        = jsonClient.get(endpoint + Chore.path)

    suspend fun add(chore: Chore) {
        jsonClient.post<Unit>(endpoint + Chore.path) {
            contentType(ContentType.Application.Json)
            body = chore
        }
    }

    suspend fun update(chore: ChoreUpdate) {
        jsonClient.put<Unit>(endpoint + Chore.path) {
            contentType(ContentType.Application.Json)
            body = chore
        }
    }

    suspend fun delete(chore: Chore) {
        jsonClient.delete<Unit>(endpoint + Chore.path + "/${chore.id}")
    }

}

object SeedsApi {
    suspend fun getMySeeds(): List<Resources.MySeeds> {
        return jsonClient.get(endpoint + SeedsDto.MySeeds.path)
    }

    suspend fun getDetailedSeed(): List<SeedsDto.DetailedSeed> {
        return jsonClient.get(endpoint + SeedsDto.DetailedSeed.path)
    }

    suspend fun getCategory(): List<SeedsDto.SeedCategory> {
        return jsonClient.get(endpoint + SeedsDto.SeedCategory.path)
    }
}
