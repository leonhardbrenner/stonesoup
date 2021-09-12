package generators

import com.squareup.kotlinpoet.*
import java.io.File
import schema.Manifest
import javax.inject.Inject

object RouteGenerator: Generator {

    override fun generate(namespace: Manifest.Namespace) {
        val typeSpec = TypeSpec.classBuilder("${namespace.name}Routing").apply {
            namespace.types.forEach { type ->
                generateRouting(type)
            }
        }
        val file = FileSpec.builder("generated.routing", "${namespace.name}Routing").apply {
            addImport("generated.dao", "${namespace.name}Dao")
            addImport("generated.model", "${namespace.name}Dto")
            addImport("org.jetbrains.exposed.sql.transactions", "transaction")
            addImport("io.ktor.application",
                "call")
            addImport("io.ktor.response",
                "respond")
            addImport("io.ktor.http",
                "HttpStatusCode")
            addImport("io.ktor.routing",
                "Routing", "route", "get", "post", "put", "delete")
            addType(typeSpec.build())
        }.build()
        val writer = File("$path/jvmMain/kotlin")
        file.writeTo(writer)
    }

    fun TypeSpec.Builder.generateRouting(type: Manifest.Namespace.Type): TypeSpec.Builder = addType(
        //Todo - generate: class ScheduleRouting @Inject constructor(val dao: SeedsDao.Schedule, val service: SeedsService) {
        TypeSpec.classBuilder(type.name).apply {
            primaryConstructor(
                //https://github.com/square/kotlinpoet/issues/515
                FunSpec.constructorBuilder().addAnnotation(ClassName("javax.inject", "Inject"))
                    .addParameter("dao", ClassName("generated.dao", "${type.namespace.name}Dao", type.name))
                    .addParameter("service", ClassName("services.${type.namespace.name.decapitalize()}", "${type.name}Service"))
                .build()
            )
            addProperty(
                PropertySpec.builder(
                "dao",
                ClassName("generated.dao", "${type.namespace.name}Dao", type.name)
            ).initializer("dao").build())
            addProperty(
                PropertySpec.builder(
                "service",
                ClassName("services.${type.namespace.name.decapitalize()}", "${type.name}Service")
            ).initializer("service").build())
            addFunction(type.index)
        }.build()
    )

    val Manifest.Namespace.Type.index
        get() = FunSpec
            .builder("routes")
            .addParameter(
                ParameterSpec.builder(
                    "routing",
                    ClassName("io.ktor.routing", "Routing")
                ).build()
            )
            .addCode("""
            |return routing.route(${dotPath("Dto")}.path) {
            |
            |    get {
            |        call.respond(transaction { dao.index() })
            |    }
            |
            |    //get("/new") {
            |    //    TODO("Show form to make new")
            |    //    //call.respond(collection.find().toList())
            |    //    //call.respond(dao.Schedule.index())
            |    //}
            |
            |    post {
            |        val choreId = call.parameters["choreId"]?.toInt() ?: return@post call.respond(HttpStatusCode.BadRequest)
            |        val workHours = call.parameters["workHours"]
            |        val completeBy = call.parameters["completeBy"]
            |        val _dto = ${dotPath("Dto")}(-1, choreId, workHours, completeBy)
            |        val _response = transaction {
            |            service.create(_dto)
            |        }
            |        call.respond(_response)
            |    }
            |
            |    //get("/{id}") {
            |    //    TODO("Lookup Schedule by id")
            |    //}
            |
            |    //get("/{id}/edit") {
            |    //}
            |
            |    put("/{id}") {
            |        val id = call.parameters["id"]?.toInt() ?: return@put call.respond(HttpStatusCode.BadRequest)
            |        val choreId = call.parameters["choreId"]?.toInt() ?: return@put call.respond(HttpStatusCode.BadRequest)
            |        val workHours = call.parameters["workHours"]
            |        val completeBy = call.parameters["completeBy"]
            |        val _dto = ${dotPath("Dto")}(id, choreId, workHours, completeBy)
            |        transaction {
            |            service.update(_dto)
            |        }
            |        call.respond(HttpStatusCode.OK)
            |    }
            |
            |    delete("/{id}") {
            |        val id = call.parameters["id"]?.toInt() ?: error("Invalid delete request")
            |        transaction {
            |            service.destroy(id)
            |        }
            |        call.respond(HttpStatusCode.OK)
            |    }
            |
            |}
            """.trimMargin() + "\n") //Todo - short form this and use it where formatting is Icky.
            .build()

}
