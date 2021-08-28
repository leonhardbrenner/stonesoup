package schema

import com.squareup.kotlinpoet.ClassName
import com.squareup.kotlinpoet.KModifier
import com.squareup.kotlinpoet.PropertySpec
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
        override fun toString() = name

        inner class ComplexType(
            override val name: String,
            builder: ComplexType.() -> Unit
        ) : Type() {
            val parent: ComplexType? = null
            val elements = linkedMapOf<String, Element>()
            init {
                types[name] = this
                complexTypes[name] = this
                builder()
            }
            operator fun invoke(builder: ComplexType.() -> Unit) {
                builder()
            }
            override val typeName get() = ClassName("generated.model.$namespace", name)

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
                val dbName = name//.toLowerCase()
                fun asPropertySpec(mutable: Boolean, vararg modifiers: KModifier) = PropertySpec.builder(
                    name,
                    type.typeName
                ).addModifiers(modifiers.toList() ).mutable(mutable)
            }

            val path: String = if (parent==null)
                "/${namespace.name}/$name"
            else
                "${parent.path}/$name"

            fun dotPath(aspect: String = ""): String = if (parent==null)
                "${namespace.name}$aspect.$name"
            else
                "${parent.dotPath(aspect)}.$name"

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
