package models

import kotlinx.serialization.Serializable

typealias ChoreId = Int

/*
  Todo
    Split this into Node<T> and Chore.
    Make a symbol that is 8 characters and encoded.
        https://stackoverflow.com/questions/53225190/produce-a-hash-string-of-fixed-length
    Build a nicer printer.
    Add and operation to the node which may take advantage of DSL visiting a graph.
    Try Node and Node.Dto out
 */
@Serializable
data class Chore(
    val name: String = "",
    val description: String? = null,
    var id: Int? = null,
    var parentId: Int = 0,
    var childrenIds: List<Int> = listOf(),
    var priority: Int? = null,
    var estimateInHours: Int? = null
) {

    init {
        if (id ==null)
            id = name.hashCode()
    }

    companion object {
        const val path = "/chores"
    }

    override fun toString(): String {
        return "$name($id, $parentId)}" //super.toString()
    }

}

//Make this a sealed class with Move and Link as base classes.
@Serializable
data class ChoreUpdate(
    val id: Int,
    val moveTo: Int? = null, //Todo - String -> Path
    val linkTo: Int? = null
)