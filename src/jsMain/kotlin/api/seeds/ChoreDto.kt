package api.seeds

import endpoint
import jsonClient
import generated.model.SeedsDto
import io.ktor.client.request.*

open class Chore {

    suspend fun index(): List<SeedsDto.Chore> {
        return jsonClient.get(endpoint + SeedsDto.Chore.path)
    }

    suspend fun new() { TODO("Form data defaults. Use a template class.") }

    suspend fun create(parentId: Int, name: String) {
        jsonClient.post<Unit>(endpoint + SeedsDto.Chore.path) {
            parameter("parentId", parentId)
            parameter("name", name)
        }
    }

    suspend fun edit() { TODO("Form data defaults. Use a template class.") }

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

    suspend fun destroy(choreId: Int) {
        jsonClient.delete<Unit>(endpoint + SeedsDto.Chore.path + "/${choreId}")
    }
}
