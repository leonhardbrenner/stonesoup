package models

import kotlinx.serialization.Serializable

@Serializable
data class Chore(
    val name: String = "",
    val description: String? = null,
    var parent: Chore? = null,
    var children: LinkedHashMap<String, Chore> = linkedMapOf(),
    var priority: Int? = null,
    var estimateInHours: Int? = null
) {
    val id: Int = name.hashCode()

    companion object {
        val root = Chore("<root>")
        const val path = "/chores"
    }
}

//Make this a sealed class with Move and Link as base classes.
@Serializable
data class ChoreUpdate(
    val path: String,
    val move: String? = null,
    val link: String? = null
)