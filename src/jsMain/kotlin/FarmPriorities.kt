import react.*
import react.dom.*
import kotlinx.coroutines.*
import kotlinx.html.js.onClickFunction
import models.Chore
import kotlinx.html.js.*
import kotlinx.html.InputType
import org.w3c.dom.events.Event
import org.w3c.dom.HTMLInputElement

private val scope = MainScope()

/**
 * Let's adapt this to be a task queue for volunteers at the farm. We can have a lead and a help view. Lead is able
 * to create task. We will need other things like skills and teacher level. This will help us associate chores with
 * volunteers and task leaders. Don't worry we'll get there:)
 */
//https://litote.org/kmongo/dokka/kmongo/org.litote.kmongo/graph-lookup.html
val FarmPriorities = functionalComponent<RProps> { _ ->
    val (shoppingList, setShoppingList) = useState(emptyList<Chore>())

    useEffect(dependencies = listOf()) {
        scope.launch {
            setShoppingList(ShoppingListApi.get())
        }
    }

    ul {
        shoppingList.sortedByDescending(Chore::priority).forEach { item ->
            li {
                key = item.toString()
                attrs.onClickFunction = {
                    scope.launch {
                        ShoppingListApi.delete(item)
                        setShoppingList(ShoppingListApi.get())
                    }
                }
                +"$item"
            }
        }
    }
    //Let's make this into a CLI later it can be a form:
    //    create A             #parent is root
    //    create A/B           #parent is A
    //    create A/B/C         #
    //    move A/B/C to A/B/D  #Here C is deleted from B and added to D
    //    create F             #
    //    link F to A/B        #Unlike move F remains
    //    delete A/B/D/C       #
    //    show F               #Show F as it's parts(A/B/F)
    //    show A/B/F           #Show part A/B/F as part of F
    //Also we want auto-complete.
    inputComponent {
        onSubmit = { input ->
            val cartItem = Chore(
                name = input.replace("!", ""),
                priority = input.count { it == '!' })
            scope.launch {
                ShoppingListApi.addItem(cartItem)
                setShoppingList(ShoppingListApi.get())
            }
        }
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
