import org.gradle.api.DefaultTask
import org.gradle.api.tasks.TaskAction

val chores = x("Goals") {
    x("Gardens") {
        y("Plant potatoes")
        x("Organize flower pots")
        x("Transplant Lemongrass")
        x("Start mixed greens salads")
        x("Start flower seedlings")
        x("Start sweet potato slips")
        x("Setup paremeter water lines")
        x("Chard")
        x("Mixed Kale")
        x("Wood chips between trail and garden")
        x("Install chicken wire")
        x("Grow great tomatoes") {
            x("Run wires between pipes.") {
                x("Dig in the pipes for the tomato stacks")
                x("Find metal wire from Bill")
            }
        }
    }
    x("Other garden's") {
        x("Start Jamie's plants")
        x("Start Mathew's plants")
    }
    x("House") {
        x("Flood light on addition")
        x("Clean wood ceilings on outside of house")
        x("Finish siding on addition")
        x("Finish trim on front of house")
        x("Fix front door")
        x("Baseboard trim")
        x("Windows trim")
        x("Trim on kitchin")
        x("Underside of addition") {
            x("Clear trash")
            x("Remove siding")
            x("Price out door")
            x("Screen") {
                x("Install new door")
                x("Install bricks") {
                    x("Gas Pipe")
                }
            }
        }
    }
    x("Stone soup software") {
        x("Register") {
            x("Add forms for data entry")
        }
        x("Organize") {
            x("Searching") {
                x("Create a search box")
            }
            x("Sorting") {
                x("Clicking on columns should sort by those columns")
                x("Clicking on description column should return dependency order")
            }
        }
        x("Plan") {
            y("parent -> parentId")
            y("children -> childIds")
            x("Get data entry to work from this")
            x("Move delete to edit screen")
            x("Clicking and item should display the item")
        }
        x("Prioritize") {
            x("Start by listing things from leaf to root")
        }
        x("Notifications") {
            x("Required") {
                x("Integrate quartz scheduler")
                x("Create a job that list next five thing to do")
                x("Integrate with slack") {
                    x("This should send to a channel my five list.")
                }
            }
            x("Nice to have") {
                x("Integrate sendgrid")
                x("Create a messenger inbox")
            }
        }
    }
    x("bs2") {
        y("Implement Atom in Kotlin.")
        x("Make an expression tree and execute different nodes.")
    }
    x("Fountain") {
        y("Clean out leaves")
        x("Seal") {
            x("Build up walls")
        }
        x("Get water running") {
            x("Try pump")
            x("Run electric to pond")
        }
    }
    x("Flourish") {
        x("Install oven") {
            x("Install oven wire")
        }
    }
    x("Random") {
        x("Install Toyota iPhone connector")
        x("File unemployment") //Great start from weekly attribute
    }
}

open class PlanPrioritize : DefaultTask() {

    init {
        group = "com.buckysoap"
        description = "planner"
    }

    @TaskAction
    fun prioritize() {
        chores.showChores()
    }

}

fun Chore.showPlan() {
    println("  ".repeat(tabs()) + name)
    children.forEach {
        it.showPlan()
    }
}

fun Chore.showChores() {
    fun Chore.trail(block: Chore.() -> Unit) {
        var nodeId = parentId
        while (nodeId != -1) {
            Chore.collection[nodeId]!!.block()
            nodeId = Chore.collection[nodeId]!!.parentId
        }
    }
    fun Chore.walk(depths: MutableMap<Int, Int> = mutableMapOf()): MutableMap<Int, Int> {
        if (children.isEmpty()) {
            var nodeId = id
            var depth = 0
            while (nodeId != -1) {
                if ((depths[nodeId] == null) || (depth > depths[nodeId]!!)) {
                    depths[nodeId] = depth
                }
                nodeId = Chore.collection[nodeId]!!.parentId
                depth += 1
            }
        } else {
            children.forEach {
                it.walk(depths)
            }
        }
        return depths
    }
    val depths = walk()
    depths.entries.sortedBy { it.value }.map { Chore.collection[it.key]!! }.forEach {
        if (!it.completed)
            println(it.name)
    }
}

fun x(name: String, block: Chore.() -> Unit = {}) = Chore(-1, name, false, block)

class Chore(val parentId: Int, val name: String, val completed: Boolean, block: Chore.() -> Unit) {

    companion object {
        val collection = LinkedHashMap<Int, Chore>()
    }

    val id: Int
    val parent get():Chore? = if (parentId==-1) null else collection[parentId]

    val childIds = mutableListOf<Int>()
    val children get() = childIds.map { collection[it]!! }

    init {
        id = collection.size
        collection[id] = this
        apply { block() }
    }

    fun tabs(tabs: Int = 0): Int = parent?.tabs(tabs + 1)?:tabs

    //TODO - there are actually subtasks, dependencies.
    fun x(name: String, block: Chore.() -> Unit = {}) {
        childIds.add(Chore(id, name, false, block).id)
    }

    fun y(name: String, block: Chore.() -> Unit = {}) {
        childIds.add(Chore(id, name, true, block).id)
    }
}
