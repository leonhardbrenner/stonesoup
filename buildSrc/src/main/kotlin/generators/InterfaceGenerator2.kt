package generators

import com.squareup.kotlinpoet.FileSpec
import com.squareup.kotlinpoet.KModifier
import com.squareup.kotlinpoet.PropertySpec
import com.squareup.kotlinpoet.TypeSpec
import java.io.File
import schema.Manifest2

object InterfaceGenerator2: Generator2 {

    override fun generate(namespace: Manifest2.Namespace) {
        val typeSpec = TypeSpec.interfaceBuilder("${namespace.name}").apply {
            namespace.complexTypes.values.forEach { type ->
                generateType(type)
            }
        }
        val file = FileSpec.builder("generated.model", "${namespace.name}").apply {
            addType(typeSpec.build())
        }.build()
        val writer = File("$path/commonMain/kotlin")
        file.writeTo(writer)
    }

    fun TypeSpec.Builder.generateType(type: Manifest2.Namespace.ComplexType): TypeSpec.Builder
            = addType(
        TypeSpec.interfaceBuilder(type.name).apply {
            type.elements.values.forEach { element ->
                addProperty(
                    //Note that I have moved toward elements defining nullability making it XMLSchema like. Reconsider.
                    PropertySpec.builder(element.name, with (element.type) { typeName.copy(nullable = element.nullable) })
                        .mutable(false)
                        .build()
                )
            }

            //XXX - Needed for fancy thi
            //type.types.forEach { type ->
            //    generateType(type)
            //}
        }.build()
    )
}
