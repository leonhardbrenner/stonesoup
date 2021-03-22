package models

import kotlinx.serialization.Serializable

/*
  Todo
    Change from Chore: Node to Node(Chore).
    Make a symbol that is 8 characters and encoded.
        https://stackoverflow.com/questions/53225190/produce-a-hash-string-of-fixed-length
    Build a nicer printer.
    Add an operation to the node which may take advantage of DSL visiting a graph.
    Try Node and Node.Dto out
 */
interface Node {
    var id: Int
    var parentId: Int
    var childrenIds: List<Int>
    val symbol get() = id.toString().padStart(4, '0')
}

//Make this a sealed class with Move and Link as sub classes.
@Serializable
data class NodeUpdate(
    val id: Int,
    val moveTo: Int? = null, //Todo - String -> Path
    val linkTo: Int? = null
)