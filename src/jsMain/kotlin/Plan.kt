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

fun RBuilder.plan() = child(Plan) {}

private val scope = MainScope()

/**
 * Let's adapt this to be a task queue for volunteers at the farm. We can have a lead and a help view. Lead is able
 * to create task. We will need other things like skills and teacher level. This will help us associate chores with
 * volunteers and task leaders. Don't worry we'll get there:)
 */
//https://litote.org/kmongo/dokka/kmongo/org.litote.kmongo/graph-lookup.html
val Plan = functionalComponent<RProps> { _ ->
    val (chores, setChores) = useState(listOf<Chore>())

    useEffect(dependencies = listOf()) {
        scope.launch {
            setChores(FarmPrioritiesApi.get())
        }
    }


    div {
        val view = TreeView(0, chores)
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
                +"${"__".repeat(view.path(item.id!!).size - 1)}${view.pathString(item.id!!)} - (${item.parentId}, ${item.id})"
            }
        }
    }

    inputComponent {
        onSubmit = { input ->
            scope.launch {
                handleInput(input)
                setChores(FarmPrioritiesApi.get())
            }
        }
    }
}

suspend fun handleInput(input: String) {
    val parts = input.split(" ")
    if (parts[0] == "create") {
        console.log("Creating ${parts[1]}")
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