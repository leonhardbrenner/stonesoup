package models

import kotlinx.serialization.Serializable

typealias ChoreId = Int

@Serializable
data class Chore(
    override var id: Int,
    override var parentId: Int = 0,
    override var childrenIds: List<Int> = listOf(),
    val name: String,
    val description: String? = null,
    var priority: Int? = null,
    var estimateInHours: Int? = null
): Node {

    companion object {
        const val path = "/chores"
    }

    override fun toString(): String {
        return "Chore($id, $parentId $name)}"
    }

}

@Serializable
data class ChoreCreate(
    val parentId: Int,
    val name: String,
    val childrenIds: List<Int> = listOf()
    //val description: String? = null,
    //var priority: Int? = null,
    //var estimateInHours: Int? = null
)