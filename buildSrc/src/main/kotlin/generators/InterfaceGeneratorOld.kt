package generators

import com.squareup.kotlinpoet.FileSpec
import com.squareup.kotlinpoet.PropertySpec
import com.squareup.kotlinpoet.TypeSpec
import java.io.File
import schema.ManifestOld

@Deprecated("See ManifestOld for details.")
object InterfaceGeneratorOld: GeneratorOld {

    override fun generate(namespace: ManifestOld.Namespace) {
        val typeSpec = TypeSpec.interfaceBuilder("${namespace.name}").apply {
            namespace.types.forEach { type ->
                generateType(type)
            }
        }
        val file = FileSpec.builder("generated.model", "${namespace.name}").apply {
            addType(typeSpec.build())
        }.build()
        val writer = File("$path/commonMain/kotlin")
        file.writeTo(writer)
    }

    fun TypeSpec.Builder.generateType(type: ManifestOld.Namespace.Type): TypeSpec.Builder
    = addType(
        TypeSpec.interfaceBuilder(type.name).apply {
            type.elements.forEach { element ->
                addProperty(
                    PropertySpec.builder(element.name, with (element.type) { typeName.copy(nullable = nullable) })
                        .mutable(false)
                        .build()
                )
            }

            type.types.forEach { type ->
                generateType(type)
            }
        }.build()
    )
}