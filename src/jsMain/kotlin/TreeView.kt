import generated.model.Seeds
import models.Node

/*
  Todo
    introduce material list.
    move and delete should use paths instead of ids?
    display leaf to root order
    render in material list
    add controls I mention them elsewhere
    move TreeView out
    maybe TreeView can provide and abstraction around our API?
    Implement a walker for Infix and Postfix.
    Implement path which is just a walk back up the tree.
 */
class TreeView<T: Seeds.Chore>(val rootId: Int, val collection: List<T>) {

    fun walk(id: Int = rootId, block: (T) -> Unit) {
        val node = collection.find { it.id == id }
        console.log("ID of node was ${id}")
        if (node != null) {
            block(node!!)
            node!!.childrenIds.split(",").map {
                if (it != "")
                    walk(it.toInt(), block)
            }
        }
    }

    //Todo - swap for dependency walk. This walks from the leaves to the root completing entire projects.
    fun depthFirstWalk(id: Int = rootId, block: (T) -> Unit) {
        val node = collection.find { it.id == id }
        if (node != null) {
            node!!.childrenIds.split(",").map {
                depthFirstWalk(it.toInt(), block)
            }
            block(node!!)
        }
    }

    //Todo - This will tell us all the little things working from the leave to the root.
    fun dependencyWalk(id: Int = rootId, block: (T) -> Unit) {
        val node = collection.find { it.id == id }
        if (node != null) {
            node!!.childrenIds.split(",").map {
                depthFirstWalk(it.toInt(), block)
            }
            block(node!!)
        }
    }

    fun find(id: Int): T {
        return collection.find { it.id == id }!!
    }
    operator fun get(id: Int) = collection.find { it.id == id }!!

    fun path(id: Int): List<T> {
        var nodeId = id
        val path = mutableListOf<T>()
        while (nodeId!=-1) {
            find(nodeId).let { node ->
                path.add(node)
                nodeId = node.parentId
            }
        }
        return path.reversed()
    }
    fun pathString(id: Int) = path(id).drop(1).joinToString("/") { it.id.toString() }

    //operator fun get(pointer: String): Node<T> = path(pointer).last()
    //operator fun set(path: String, value: T): Node<T> {
    //    return get(path).set(value)
    //}

}
