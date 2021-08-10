import Plan2.ComponentStyles.listDiv
import com.ccfraser.muirwik.components.*
import com.ccfraser.muirwik.components.button.mIconButton
import com.ccfraser.muirwik.components.list.*
import kotlinx.css.*
import models.Chore
import react.*
import styled.StyleSheet
import styled.css
import styled.styledDiv
import kotlinx.html.InputType
import org.w3c.dom.events.Event
import org.w3c.dom.HTMLInputElement
import react.dom.*
import kotlinx.html.js.*

external interface Plan2Props: RProps {
    var chores: List<Chore>
    var deleteChore: (Int) -> Unit
    var handleInput: (String) -> Unit
    var onMouseEnter: (Int) -> Unit
    var onMouseLeave: (Int) -> Unit
    var isMouseIn: (Int) -> Boolean
    var onSelect: (Int) -> Unit
    var isSelected: (Int) -> Boolean
}

class Plan2 : RComponent<Plan2Props, RState>() {
    private object ComponentStyles : StyleSheet("ComponentStyles", isStatic = true) {
        val listDiv by css {
            display = Display.inlineFlex
            padding(1.spacingUnits)
        }
    }

    override fun RBuilder.render() {
        // For building things that we don't want to render now (e.g. the component will render it later), we need another builder
        styledDiv {
            css(listDiv)
            mList {
                val view = TreeView(0, props.chores)
                view.walk { item ->
                    mListItem {
                        key = item.id!!.toString()//toString()
                        attrs.onClick = {
                            props.onSelect(item.id)
                        }
                        attrs.onMouseEnter = {
                            props.onMouseEnter(item.id)
                        }
                        attrs.onMouseLeave = {
                            props.onMouseLeave(item.id)
                        }
                        mListItemText("${item.name}") {
                            css {
                                marginLeft = ((view.path(item.id).size - 1) * 2).spacingUnits
                            }
                        }
                        if (props.isSelected(item.id)) {
                            mListItemSecondaryAction {
                                //TODO - change to edit action: to(textbox) expand an mCollapse[delete, priority, ]
                                mIconButton("comment", onClick = { props.deleteChore(item.id) })
                            }
                        }
                    }
                }
            }

            inputComponent {
                onSubmit = { input ->
                    props.handleInput(input)
                }
            }

        }
    }

}

fun RBuilder.plan2(handler: Plan2Props.() -> Unit): ReactElement {
    return child(Plan2::class) {
        this.attrs(handler)
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
