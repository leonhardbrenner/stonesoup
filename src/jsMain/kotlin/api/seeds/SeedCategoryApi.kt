package api.seeds

import endpoint
import jsonClient
import generated.model.SeedsDto
import io.ktor.client.request.*

open class SeedCategoryApi {
    suspend fun index(): List<SeedsDto.SeedCategory> {
        return jsonClient.get(endpoint + SeedsDto.SeedCategory.path)
    }

    suspend fun new() { TODO("Form data defaults. Use a template class.") }

    suspend fun create(name: String, maturity: String?, secondary_name: String?,
                       description: String?, image: String?, link: String?) {
        jsonClient.post<Unit>(endpoint + SeedsDto.SeedCategory.path) {
            parameter("name", name)
            parameter("image", image)
            parameter("link", link)
        }
    }

    suspend fun edit() { TODO("Form data defaults. Use a template class.") }

    //Todo - implement edit which has loads existing values

    suspend fun update(id: Int, name: String, maturity: String?, secondary_name: String?,
                       description: String?, image: String?, link: String?) {
        jsonClient.put<Unit>(endpoint + SeedsDto.SeedCategory.path + "/$id") {
            //Example of how to send a complexType
            //data class Node(val id: Int, val name: String)
            //contentType(ContentType.Application.Json)
            //body = Node(1, "Y")
            parameter("name", name)
            parameter("image", image)
            parameter("link", link)
        }
    }

    suspend fun destroy(choreId: Int) {
        jsonClient.delete<Unit>(endpoint + SeedsDto.SeedCategory.path + "/${choreId}")
    }
}
