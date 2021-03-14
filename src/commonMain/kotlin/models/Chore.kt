package models

import kotlinx.serialization.Serializable

@Serializable
data class Chore(val desc: String, val priority: Int) {
    val id: Int = desc.hashCode()

    companion object {
        const val path = "/chores"
    }
}