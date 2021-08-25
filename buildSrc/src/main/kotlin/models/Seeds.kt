package models

import org.gradle.internal.impldep.com.sun.xml.bind.v2.schemagen.episode.Klass
import java.util.jar.Attributes
import kotlin.reflect.KClass

interface Seeds {
    //TODO - most of these need account ids
    //TODO - we need an accounts table
    class MySeeds(
        val id: Int,
        val seed_label: String,
        val description: String,
        val germination_test: String
    )

    class SeedCategory(
        val id: Int,
        val name: String,
        val image: String,
        val link: String
    )

    class DetailedSeed(
        val id: Int,
        val name: String,
        val maturity: String?,
        val secondary_name: String?,
        val description: String?,
        val image: String?,
        val link: String?
    )

    //TODO: I am thinking this should just be Node. We can then tie items to the tree.
    //    This should make XQuery like queries much simpler
    //
    class Chore(
        val id: Int,
        val parentId: Int = 0,
        val childrenIds: String, //List<Int> = listOf(),
        val name: String
        //val description: String? = null,
        //val priority: Int? = null,
        //var estimateInHours: Int? = null
    )

    class Schedule(
        val id: Int,
        val choreId: Int, //
        val workHours: String?, //When can I work on this //TODO: This should support workDays as well.
        val completeBy: String? //When must it be done.

    )

}

//TODO - move away from reflection toward DSL
//Class("Schedule") {
//    Val("parentId", type = Int::class) {
//        Doc("""Stuff""")
//        Annotation("Bla")
//    }
//}

//XXX - Using reflection was a terrible idea change from above to below:)
abstract class Type {
    abstract val name: String
}

class ComplexType(
    override val name: String,
    builder: ComplexType.() -> Unit
): Type() {
    inner class Element(
        val name: String,
        val type: Type,
        var minOccurs: Int = 1, var maxOccurs: Int = 1,
        var default: Any? = null,
        val isAttribute: Boolean = false,
        builder: Element.() -> Unit = {})
}

class SimpleType<T: Any>(
    override val name: String,
    val kClass: KClass<T>,
    builder: SimpleType<T>.() -> Unit? = {}
): Type() {
    fun invoke() {}

}
class Element(
    val name: String,
    val type: Type,
    var default: Any? = null,
    val isAttribute: Boolean = false,
    builder: Element.() -> Unit? = {}
) {

}

val boolean = SimpleType("Int", Int::class)
val int = SimpleType("Int", Int::class)
val string = SimpleType("String", String::class)
val long = SimpleType("Long", Long::class)
val double = SimpleType("Double", Double::class)
val float = SimpleType("Double", Double::class)

val node = ComplexType("Node") {
    Element("foo", string)
}

//TODO: Create namespaces
val mySeeds = ComplexType("MySeeds") {
    Element("id", int)
    Element("seed_label", string)
    Element("description", string)
    Element("germination_test", string)
}

val seedCategory = ComplexType("SeedCategory") {
    Element("id", int)
    Element("name", string)
    Element("image", string)
    Element("link", string)
}

val detailedSeed = ComplexType("DetailedSeed") {
    Element("id", int)
    Element("name", string)
    Element("maturity", string) {
        minOccurs = 0
    }
    Element("secondary_name", string) {
        minOccurs = 0
    }
    Element("description", string) {
        minOccurs = 0
    }
    Element("image", string) {
        minOccurs = 0
    }
    Element("link", string) {
        minOccurs = 0
    }
}

//TODO: I am thinking this should just be Node. We can then tie items to the tree.
//    This should make XQuery like queries much simpler
//
val chore = ComplexType("Chore") {
    Element("id", int)
    Element("parentId", int) {
        default = 0
    }
    Element("name", string)
    Element("maturity", string) {
        minOccurs = 0
    }
}

val schedule = ComplexType("Schedule") {
    Element("id", int)
    Element("choreId", int) {
        default = 0
    }
    Element("workHours", string) {
        minOccurs = 0
    }
    Element("completeBy", string) {
        minOccurs = 0
    }
}