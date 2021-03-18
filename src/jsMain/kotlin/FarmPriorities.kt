import react.*
import react.dom.*
import kotlinx.coroutines.*
import kotlinx.html.js.onClickFunction
import models.Chore
import kotlinx.html.js.*
import kotlinx.html.InputType
import models.ChoreUpdate
import org.w3c.dom.events.Event
import org.w3c.dom.HTMLInputElement

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
class TreeView(val rootId: Int, val collection: List<Chore>, val builder: RDOMBuilder<*>) {

    //fun path(pointer: String): List<Chore> {
    //    var node = root
    //    return pointer.split("/").map { name ->
    //        node = node[name]
    //        node
    //    }
    //}

    fun walk(id: Int = rootId, block: RDOMBuilder<*>.(Chore) -> Unit) {
        val node = collection.find { it.id == id }
        if (node != null) {
            builder.block(node!!)
            node!!.childrenIds.map {
                walk(it, block)
            }
        }
    }

    fun find(id: Int): Chore {
        return collection.find { it.id == id }!!
    }
    fun path(id: Int): List<Chore> {
        var nodeId = id
        val path = mutableListOf<Chore>()
        while (nodeId!=-1) {
            find(nodeId).let { node ->
                path.add(node)
                nodeId = node.parentId
            }
        }
        return path
    }

    //operator fun get(pointer: String): Node<T> = path(pointer).last()
    //operator fun set(path: String, value: T): Node<T> {
    //    return get(path).set(value)
    //}

}

private val scope = MainScope()

/**
 * Let's adapt this to be a task queue for volunteers at the farm. We can have a lead and a help view. Lead is able
 * to create task. We will need other things like skills and teacher level. This will help us associate chores with
 * volunteers and task leaders. Don't worry we'll get there:)
 */
//https://litote.org/kmongo/dokka/kmongo/org.litote.kmongo/graph-lookup.html
val FarmPriorities = functionalComponent<RProps> { _ ->
    val (chores, setChores) = useState(listOf<Chore>())
    useEffect(dependencies = listOf()) {
        scope.launch {
            setChores(FarmPrioritiesApi.get())
        }
    }

    //val choreMap = chores.toList().map { it.id!! to it }.toMap()
    //val view = chores)

    div {
        val view = TreeView(0, chores, this)
        view.walk { item ->
            div {
                key = item.id!!.toString()//toString()
                attrs.onClickFunction = {
                    scope.launch {
                        FarmPrioritiesApi.delete(item.id!!)
                        setChores(FarmPrioritiesApi.get())
                    }
                }
                //It would be neat to draw <root> as actual roots.
                //${"--".repeat(view.path(item.id!!).size + 1)}
                //${item.childrenIds}
                val path = view.path(item.id!!).reversed()
                val pathString = path.drop(1).map { it.name }.joinToString("/")
                +"${"__".repeat(path.size - 1)}$pathString - (${item.parentId}, ${item.id})"
            }
        }

    }
    //Let's make this into a CLI later it can be a form:
    //    create A             #parent is root
    //    create A/B           #parent is A
    //    create A/B/C         #
    //    move A/B/C to A/B/D  #Here C is deleted from B and added to D
    //    create F             #
    //    link F from A/B        #Unlike move F remains
    //    delete A/B/D/C       #
    //    show F               #Show F as it's parts(A/B/F)
    //    show A/B/F           #Show part A/B/F as part of F
    //Also we want auto-complete.
    inputComponent {
        onSubmit = { input ->
            scope.launch {
                x(input)
                setChores(FarmPrioritiesApi.get())
            }
        }
    }
}

suspend fun x(input: String) {
    val parts = input.split(" ")
    if (parts[0] == "create") {
        val chore = Chore(
            name = parts[1].replace("!", ""),
            priority = parts[1].count { it == '!' })
        FarmPrioritiesApi.add(chore)
    } else if (parts[0] == "move") {
        val chore = ChoreUpdate(
            id = parts[1].toInt(),
            moveTo = parts[3].toInt()
        )
        FarmPrioritiesApi.update(chore)
    } else if (parts[0] == "link") {
        val chore = ChoreUpdate(
            id = parts[1].toInt(),
            linkTo = parts[3].toInt()
        )
        FarmPrioritiesApi.update(chore)
    } else if (parts[0] == "delete") {
        FarmPrioritiesApi.delete(parts[1].toInt())
    }
}

fun RBuilder.farmPriorities() = child(FarmPriorities) {}

external interface InputProps : RProps {
    var onSubmit: (String) -> Unit
}

val InputComponent = functionalComponent<InputProps> { props ->
    val (text, setText) = useState("")

    val submitHandler: (Event) -> Unit = {
        it.preventDefault()
        setText("")
        props.onSubmit(text)
    }

    val changeHandler: (Event) -> Unit = {
        val value = (it.target as HTMLInputElement).value
        setText(value)
    }

    form {
        attrs.onSubmitFunction = submitHandler
        input(InputType.text) {
            attrs.onChangeFunction = changeHandler
            attrs.value = text
        }
    }
}

fun RBuilder.inputComponent(handler: InputProps.() -> Unit) = child(InputComponent) { attrs { handler() } }
