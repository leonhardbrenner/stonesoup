package models

import kotlinx.serialization.Serializable

typealias ChoreId = Int

@Serializable
data class Chore(
    val name: String = "",
    val description: String? = null,
    var id: Int? = null,
    var parentId: Int = -1,
    var childrenIds: LinkedHashMap<String, Int> = linkedMapOf(),
    var priority: Int? = null,
    var estimateInHours: Int? = null
) {
    init {
        if (id ==null)
            id = name.hashCode()
    }
    companion object {
        val root = Chore("<root>")
        const val path = "/chores"
    }

    override fun toString(): String {
        return "$name($id, $parentId)"//super.toString()
    }
}

//Make this a sealed class with Move and Link as base classes.
@Serializable
data class ChoreUpdate(
    val id: Int,
    val moveTo: Int? = null, //Todo - String -> Path
    val linkTo: Int? = null
)