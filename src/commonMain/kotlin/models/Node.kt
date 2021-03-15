package models

import kotlinx.serialization.Serializable

@Serializable
data class Node<T>(
    val name: String,
    val parentId: Int = -1,
    val children: LinkedHashMap<String, Int> = linkedMapOf(),
    var value: T? = null,
    var depth: Int = 0,
    val id: Int = name.hashCode(),
    //Todo - unchecked cast
    val collection: LinkedHashMap<Int, Node<T>> = pool as LinkedHashMap<Int, Node<T>>
) {
    companion object {
        val pool = linkedMapOf<Int, Node<*>>()
    }
    init {
        collection[id] = this
        var depth = 1
        var node = parent
        while (node != null) {
            if (depth > node.depth)
                node.depth = depth
            depth += 1
            node = node.parent
        }
    }

    val parent get() = if (parentId == -1)
        null
    else
        collection[parentId]

    operator fun get(name: String): Node<T> {
        if (!children.containsKey(name))
            children[name] = Node<T>(name, id).id
        return collection[children[name]!!]!!
    }

    fun set(value: T): Node<T> {
        this.value = value
        return this
    }

    val path get(): List<Node<T>> {
        var node: Node<T>? = this
        val path = mutableListOf<Node<T>>()
        while (node!=null) {
            path.add(node)
            node = node.parent
        }
        return path
    }
}
