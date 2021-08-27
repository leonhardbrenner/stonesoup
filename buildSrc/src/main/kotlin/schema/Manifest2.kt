package schema

import com.squareup.kotlinpoet.ClassName
import kotlin.reflect.KClass

class Manifest2(builder: Manifest2.() -> Unit) {
    val namespaces = linkedMapOf<String, Namespace>()
    init {
        builder()
    }
    operator fun invoke(builder: Manifest2.() -> Unit) {
        builder()
    }

    abstract class Type {
        abstract val name: String
        abstract val typeName: ClassName
    }

    inner class Namespace(val name: String, builder: Namespace.() -> Unit) {
        val namespace = this
        val types = linkedMapOf<String, Type>()
        val simpleTypes = linkedMapOf<String, SimpleType>()
        val complexTypes = linkedMapOf<String, ComplexType>()
        operator fun get(name: String) = simpleTypes[name]!!
        init {
            namespaces[name] = this
            builder()
        }
        operator fun invoke(builder: Namespace.() -> Unit) {
            builder()
        }

        inner class ComplexType(
            override val name: String,
            builder: ComplexType.() -> Unit
        ) : Type() {
            val elements = linkedMapOf<String, Element>()
            init {
                types[name] = this
                complexTypes[name] = this
                builder()
            }
            operator fun invoke(builder: ComplexType.() -> Unit) {
                builder()
            }
            override val typeName get() = ClassName("generated.model", name)

            inner class Element(
                val name: String,
                val type: Type,
                var minOccurs: Int = 1, var maxOccurs: Int = 1,
                var default: Any? = null,
                val isAttribute: Boolean = false,
                builder: Element.() -> Unit = {}
            ) {
                val nullable get() = (minOccurs == 0 && maxOccurs == 1)
                init {
                    elements[name] = this
                    builder()
                }
                operator fun invoke(builder: Element.() -> Unit) {
                    builder()
                }
            }
        }

        inner class SimpleType(
            override val name: String,
            val kClass: KClass<*>, //builtIn["int"].kClass.qualifiedName
            builder: SimpleType.() -> Unit? = {}
        ) : Type() {
            init {
                types[name] = this
                simpleTypes[name] = this
                builder()
            }
            fun invoke(builder: SimpleType.() -> Unit? = {}) {
                builder()
            }
            override val typeName get() = ClassName("kotlin", kClass.simpleName.toString())

        }

        inner class Element(
            val name: String,
            val type: Type,
            var default: Any? = null,
            val isAttribute: Boolean = false,
            builder: Element.() -> Unit? = {}
        ) {
            init {
                builder()
            }
            operator fun invoke(builder: Element.() -> Unit) {
                builder()
            }
        }
    }

}
