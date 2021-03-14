import generated.model.SeedsDto
import io.ktor.http.*
import io.ktor.client.*
import io.ktor.client.request.*
import io.ktor.client.features.json.JsonFeature
import io.ktor.client.features.json.serializer.KotlinxSerializer

import kotlinx.browser.window
import models.Resources
import models.Chore

val endpoint = window.location.origin // only needed until https://github.com/ktorio/ktor/issues/1695 is resolved

val jsonClient = HttpClient {
    install(JsonFeature) { serializer = KotlinxSerializer() }
}

object ShoppingListApi {
    suspend fun get(): List<Chore> {
        return jsonClient.get(endpoint + Chore.path)
    }

    suspend fun addItem(chore: Chore) {
        jsonClient.post<Unit>(endpoint + Chore.path) {
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
