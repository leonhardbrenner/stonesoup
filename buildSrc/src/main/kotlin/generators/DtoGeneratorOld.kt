package generators

import com.squareup.kotlinpoet.ClassName
import com.squareup.kotlinpoet.FileSpec
import com.squareup.kotlinpoet.FunSpec
import com.squareup.kotlinpoet.KModifier
import com.squareup.kotlinpoet.PropertySpec
import com.squareup.kotlinpoet.TypeSpec

import schema.ManifestOld
import java.io.File

@Deprecated("See ManifestOld for details.")
object DtoGeneratorOld: GeneratorOld {

    //TODO: Generate the top level elements as resources.
    override fun generate(namespace: ManifestOld.Namespace) {
        val file = FileSpec.builder("generated.model", "${namespace.name}Dto")
            .addType(
                TypeSpec.interfaceBuilder("${namespace.name}Dto").apply {
                    namespace.types.forEach { generateType(it) }
                }.build()
            ).build()
        val writer = File("$path/commonMain/kotlin")
        file.writeTo(writer)
    }

    fun TypeSpec.Builder.generateType(type: ManifestOld.Namespace.Type): TypeSpec.Builder = addType(
        TypeSpec.classBuilder(type.name)
            .addAnnotation(ClassName("kotlinx.serialization", "Serializable"))
            .addModifiers(KModifier.DATA)
            .addSuperinterface(type.typeName)
            .primaryConstructor(
                FunSpec.constructorBuilder().apply {
                    type.elements.forEach { element ->
                        addParameter(element.name, element.type.typeName)
                            .build()
                    }
                }.build()
            )
            .apply {
                type.elements.forEach { element ->
                    addProperty(
                        PropertySpec.builder(element.name, element.type.typeName)
                            .addModifiers(listOf(KModifier.OVERRIDE))
                            .mutable(false)
                            .initializer(element.name)
                            .build()
                    )
                }
            }
            .addType(
                TypeSpec.companionObjectBuilder().apply {
                    addProperty(
                        PropertySpec.builder("path", String::class)
                            .addModifiers(listOf(KModifier.CONST))
                            .initializer("\"${type.path}\"")
                            .build()
                    )
                    addFunction(
                        FunSpec.builder("create")
                            .addParameter("source", type.typeName)
                            //Look at CodeBlock.addArgument and you will see L stands for literal
                            .addCode(
                                "return %T(%L)",
                                ClassName("generated.model", type.dotPath("Dto")),
                                type.elements.map { "source.${it.name}" }.joinToString(", ")
                            )
                            .build()
                    )
                }.build()
            ).apply {
                type.types.forEach {
                    generateType(it)
                }
            }.build()
    )
}
