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
            addImport("generated.model", "${namespace.name}Dto")
            addImport("io.ktor.routing", "Routing", "route")
            addType(typeSpec.build())
        }.build()
        val writer = File("$path/jvmMain/kotlin")
        file.writeTo(writer)
    }

    fun TypeSpec.Builder.generateRouting(type: Manifest.Namespace.Type): TypeSpec.Builder = addType(
        //Todo - generate: class ScheduleRouting @Inject constructor(val dao: SeedsDao.Schedule, val service: SeedsService) {
        TypeSpec.classBuilder(type.name).apply {
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
                |   TODO("Generate routes but first consider refactoring API so create and update pass entity.")
                |}
            """.trimMargin() + "\n") //Todo - short form this and use it where formatting is Icky.
            .build()

}
