package api.seeds

import endpoint
import jsonClient
import generated.model.SeedsDto
import io.ktor.client.request.*
import models.SeedsResources

open class MySeedsApi {
    suspend fun index(): List<SeedsResources.MySeeds> {
        return jsonClient.get(endpoint + SeedsDto.MySeeds.path)
    }

    suspend fun new() { TODO("Form data defaults. Use a template class.") }

    suspend fun create(seed_label: String, description: String?, germination_test: String?) {
        jsonClient.post<Unit>(endpoint + SeedsDto.MySeeds.path) {
            parameter("seed_label", seed_label)
            parameter("description", description)
            parameter("germination_test", germination_test)
        }
    }

    suspend fun edit() { TODO("Form data defaults. Use a template class.") }

    //Todo - implement edit which has loads existing values

    suspend fun update(id: Int, seed_label: String, description: String?, germination_test: String?) {
        jsonClient.put<Unit>(endpoint + SeedsDto.MySeeds.path + "/$id") {
            //Example of how to send a complexType
            //data class Node(val id: Int, val name: String)
            //contentType(ContentType.Application.Json)
            //body = Node(1, "Y")
            parameter("seed_label", seed_label)
            parameter("description", description)
            parameter("germination_test", germination_test)
        }
    }

    suspend fun destroy(choreId: Int) {
        jsonClient.delete<Unit>(endpoint + SeedsDto.DetailedSeed.path + "/${choreId}")
    }

}