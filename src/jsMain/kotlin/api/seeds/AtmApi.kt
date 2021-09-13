package api.seeds

//Todo - flush this out if FE is ever needed.
//import endpoint
//import jsonClient
//import generated.model.AtmDto
//import io.ktor.client.request.*
//
//class AtmApi {
//    open class AuthorizationPin {
//
//        suspend fun index(): List<AtmDto.AuthorizationPin> {
//            return jsonClient.get(endpoint + AtmDto.AuthorizationPin.path)
//        }
//
//        suspend fun new() {
//            TODO("Form data defaults. Use a template class.")
//        }
//
//        suspend fun create(parentId: Int, name: String) {
//            jsonClient.post<Unit>(endpoint + AtmDto.AuthorizationPin.path) {
//                parameter("parentId", parentId)
//                parameter("name", name)
//            }
//        }
//
//        suspend fun edit() {
//            TODO("Form data defaults. Use a template class.")
//        }
//
//        //Todo - implement edit which has loads existing values
//
//        suspend fun update(accountId: String, pin: String) {
//            jsonClient.put<Unit>(endpoint + AtmDto.AuthorizationPin.path + "/account/$accountId") {
//                //Example of how to send a complexType
//                //data class Node(val id: Int, val name: String)
//                //contentType(ContentType.Application.Json)
//                //body = Node(1, "Y")
//                parameter("parentId", parentId)
//                parameter("name", name)
//            }
//        }
//
//        suspend fun destroy(choreId: Int) {
//            jsonClient.delete<Unit>(endpoint + AtmDto.AuthorizationPin.path + "/${choreId}")
//        }
//    }
//}