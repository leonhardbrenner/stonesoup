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

    object Category {
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

    object DetailedSeed {
        suspend fun index(): List<SeedsDto.DetailedSeed> {
            return jsonClient.get(endpoint + SeedsDto.DetailedSeed.path)
        }

        suspend fun new() { TODO("Form data defaults. Use a template class.") }

        suspend fun create(name: String, maturity: String?, secondary_name: String?,
                           description: String?, image: String?, link: String?) {
            jsonClient.post<Unit>(endpoint + SeedsDto.DetailedSeed.path) {
                parameter("name", name)
                parameter("maturity", maturity)
                parameter("secondary_name", secondary_name)
                parameter("description", description)
                parameter("image", image)
                parameter("link", link)
            }
        }

        suspend fun edit() { TODO("Form data defaults. Use a template class.") }

        //Todo - implement edit which has loads existing values

        suspend fun update(id: Int, name: String, maturity: String?, secondary_name: String?,
                           description: String?, image: String?, link: String?) {
            jsonClient.put<Unit>(endpoint + SeedsDto.DetailedSeed.path + "/$id") {
                //Example of how to send a complexType
                //data class Node(val id: Int, val name: String)
                //contentType(ContentType.Application.Json)
                //body = Node(1, "Y")
                parameter("name", name)
                parameter("maturity", maturity)
                parameter("secondary_name", secondary_name)
                parameter("description", description)
                parameter("image", image)
                parameter("link", link)
            }
        }

        suspend fun destroy(choreId: Int) {
            jsonClient.delete<Unit>(endpoint + SeedsDto.DetailedSeed.path + "/${choreId}")
        }
    }

    object MySeeds {
        suspend fun index(): List<Resources.MySeeds> {
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

}
