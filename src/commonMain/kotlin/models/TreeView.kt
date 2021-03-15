package models

class TreeView<T> {

    val root = Node<T>("<root>") //Go to the pool/collection instead of this mess.

    fun path(pointer: String): List<Node<T>> {
        var node = root
        return pointer.split("/").map { name ->
            node = node[name]
            node
        }
    }

    fun walk(node: Node<T> = root) {
        println("  ".repeat(node.path.size) + node.name)
        node.children.values.map {
            walk(node.collection[it]!!)
        }
    }

    operator fun get(pointer: String): Node<T> = path(pointer).last()
    operator fun set(path: String, value: T): Node<T> {
        return get(path).set(value)
    }

}
