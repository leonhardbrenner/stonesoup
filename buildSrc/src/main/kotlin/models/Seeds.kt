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

class Namespace(val name: String, builder: Namespace.() -> Unit) {
    companion object {
        val namespace = this
        val simpleTypes = mutableMapOf<String, Namespace.SimpleType<Any>>()
    }
    operator fun get(name: String) = simpleTypes[name]!!
    inner class ComplexType(
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

    inner class SimpleType<T: Any>(
        override val name: String,
        val kClass: KClass<T>,
        builder: SimpleType<T>.() -> Unit? = {}
    ): Type() {
        fun invoke() {}
        init {
            simpleTypes
        }

    }
    inner class Element(
        val name: String,
        val type: Type,
        var default: Any? = null,
        val isAttribute: Boolean = false,
        builder: Element.() -> Unit? = {}
    ) {

    }

}

val builtIn = Namespace("BuiltIn") {
    SimpleType("Int", Int::class)
    SimpleType("String", String::class)
    SimpleType("Long", Long::class)
    SimpleType("Double", Double::class)
    SimpleType("Double", Double::class)
}

val seeds = Namespace("seeds") {

    ComplexType("Node") {
        Element("foo", builtIn["string"]!!)
    }


//TODO: Create namespaces
    ComplexType("MySeeds") {
        Element("id", builtIn["int"])
        Element("seed_label", builtIn["string"])
        Element("description", builtIn["string"])
        Element("germination_test", builtIn["string"])
    }

    ComplexType("SeedCategory") {
        Element("id", builtIn["int"])
        Element("name", builtIn["string"])
        Element("image", builtIn["string"])
        Element("link", builtIn["string"])
    }

    ComplexType("DetailedSeed") {
        Element("id", builtIn["int"])
        Element("name", builtIn["string"])
        Element("maturity", builtIn["string"]) {
            minOccurs = 0
        }
        Element("secondary_name", builtIn["string"]) {
            minOccurs = 0
        }
        Element("description", builtIn["string"]) {
            minOccurs = 0
        }
        Element("image", builtIn["string"]) {
            minOccurs = 0
        }
        Element("link", builtIn["string"]) {
            minOccurs = 0
        }
    }

    //TODO: I am thinking this should just be Node. We can then tie items to the tree.
    //    This should make XQuery like queries much simpler
    //
    ComplexType("Chore") {
        Element("id", builtIn["int"])
        Element("parentId", builtIn["string"]) {
            default = 0
        }
        Element("name", builtIn["string"])
        Element("maturity", builtIn["string"]) {
            minOccurs = 0
        }
    }

    ComplexType("Schedule") {
        Element("id", builtIn["int"])
        Element("choreId", builtIn["string"]) {
            default = 0
        }
        Element("workHours", builtIn["string"]) {
            minOccurs = 0
        }
        Element("completeBy", builtIn["string"]) {
            minOccurs = 0
        }
    }
}