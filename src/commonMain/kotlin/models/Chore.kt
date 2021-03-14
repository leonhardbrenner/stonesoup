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