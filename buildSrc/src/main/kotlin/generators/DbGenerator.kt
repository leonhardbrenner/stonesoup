package generators

import com.squareup.kotlinpoet.*
import java.io.File
import com.squareup.kotlinpoet.ParameterizedTypeName.Companion.parameterizedBy
import schema.Manifest

object DbGenerator: Generator {

    override fun generate(namespace: Manifest.Namespace) {
        val file = FileSpec.builder("generated.model.db", "${namespace.name}Db")
            .addImport("org.jetbrains.exposed.dao", "IntEntity", "IntEntityClass")
            .addImport("org.jetbrains.exposed.dao.id", "EntityID", "IntIdTable")
            .addImport("org.jetbrains.exposed.sql", "ResultRow", "selectAll")
            .addImport("org.jetbrains.exposed.sql.transactions", "transaction")
            .addImport("generated.model", "${namespace.name}")
            .addImport("generated.model", "${namespace.name}Dto")
            .addType(
                TypeSpec.objectBuilder("${namespace.name}Db").apply {
                    namespace.complexTypes.values.forEach { complexType ->
                        addType(
                            TypeSpec.objectBuilder(complexType.name)
                                .addType(complexType.table)
                                .addType(complexType.entity)
                                .addFunction(complexType.create)
                                .addFunction(
                                    FunSpec.builder("fetchAll")
                                        .addCode(
                                            "return transaction { with (Table) { selectAll().map { create(it) } } }"
                                        ).build()
                                ).build()
                        )
                    }
                }.build()
            ).build()
        val writer = File("$path/jvmMain/kotlin")
        file.writeTo(writer)
    }

    val Manifest.Namespace.ComplexType.Element.propertySpec
        get() = PropertySpec.builder(
            name,
            ClassName("org.jetbrains.exposed.sql", "Column")
                .parameterizedBy(type.typeName.copy(nullable=nullable))
        )
            .apply {
                type.name
            }
            .initializer("${
                when (type.typeName.toString()) {
                    "kotlin.String" -> "text"
                    "kotlin.Int" -> "integer"
                    "kotlin.Double" -> "double"
                    "kotlin.Long" -> "long"
                    "kotlin.Boolean" -> "bool"
                    else -> "text"
                }
            }(\"${dbName}\")${if (nullable) ".nullable()" else ""}")
            .build()

    val Manifest.Namespace.ComplexType.table
        get() = TypeSpec.objectBuilder("Table")
            .superclass(ClassName("org.jetbrains.exposed.dao.id", "IntIdTable"))
            .addSuperclassConstructorParameter("%S", name)
            .apply {
                elements.values.forEach { element ->
                    if (element.name != "id")
                        addProperty(element.propertySpec) //XXX - bring me back.
                }
            }.build()

    val Manifest.Namespace.ComplexType.create
        get() = FunSpec.builder("create")
            .addParameter("source", ClassName("org.jetbrains.exposed.sql", "ResultRow"))

            .addCode("return %LDto.%L(%L)",
                packageName, name,
                (elements.values.map { "source[Table.${it.name}]${if (it.name == "id") ".value" else ""}" }
                        + links.values.map { "null"})
                    .joinToString(", "))
            .build()

    fun Manifest.Namespace.ComplexType.Element.asPropertySpec(mutable: Boolean, vararg modifiers: KModifier) =
        PropertySpec.builder(name, type.typeName.copy(nullable = nullable))
            .addModifiers(modifiers.toList())
            .mutable(mutable)

    val Manifest.Namespace.ComplexType.entity
        get() = TypeSpec.classBuilder("Entity")
            .superclass(ClassName("org.jetbrains.exposed.dao", "IntEntity"))
            .addSuperclassConstructorParameter("id")
            //TODO: Revisit this but I was clashing on column['id']. Remarkably this also deleted the override methods.
            //.addSuperinterface(ClassName("generated.model.${namespace.name}", name))
            .primaryConstructor(
                com.squareup.kotlinpoet.FunSpec.constructorBuilder().apply {
                    addParameter(
                        "id",
                        ClassName("org.jetbrains.exposed.dao.id", "EntityID")
                            .parameterizedBy(kotlin.Int::class.asTypeName())
                    ).build()
                }.build()
            )
            .addType(
                TypeSpec.companionObjectBuilder()
                    .superclass(
                        ClassName("org.jetbrains.exposed.dao", "IntEntityClass")
                            .parameterizedBy(ClassName("", "Entity"))
                    )
                    .addSuperclassConstructorParameter("Table")
                    //TODO: Delete this if we never need it. .new was good enough for now.
                    //.addFunction(
                    //    com.squareup.kotlinpoet.FunSpec.builder("insert")
                    //        .addParameter(
                    //            ParameterSpec("source", ClassName(packageName, name))
                    //        )
                    //        .addCode("""Entity.new {
                    //        |%L
                    //        |}""".trimMargin(), elements.map { "  ${it.columnName} = source.${it.name}" }.joinToString("\n"))
                    //        .build()
                    //)
                    .build()
            )
            .apply {
                elements.values.forEach { slot ->
                    if (slot.name != "id") {
                        //TODO: delete comment but currently my only reference.
                        val propertySpec = slot.asPropertySpec(true /*, com.squareup.kotlinpoet.KModifier.OVERRIDE*/)
                            .delegate("Table.%L", slot.name)
                            .mutable(true)
                            .build()
                        addProperty(propertySpec)
                    }
                }
            }
            .build()

}
