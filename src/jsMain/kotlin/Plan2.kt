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
    private var expanded: Boolean = false

    private object ComponentStyles : StyleSheet("ComponentStyles", isStatic = true) {
        val listDiv by css {
            display = Display.inlineFlex
            padding(1.spacingUnits)
        }
    }

    override fun RBuilder.render() {
        // For building things that we don't want to render now (e.g. the component will render it later), we need another builder
        val builder2 = RBuilder()
        themeContext.Consumer { theme ->
            val themeStyles = object : StyleSheet("ComponentStyles", isStatic = true) {
                val list by css {
                    width = 320.px
                    backgroundColor = Color(theme.palette.background.paper)
                }
            }
            styledDiv {
                css(listDiv)
                mList {
                    val view = TreeView(0, props.chores)
                    view.walk { item ->
                        mListItem {
                            key = item.id!!.toString()//toString()
                            attrs.onClick = {
                                console.log("Select item for drop ${item.id}")
                                props.onSelect(item.id)
                            }
                            attrs.onMouseEnter = {
                                console.log("Entering ${item.id}")
                                props.onMouseEnter(item.id)
                            }
                            attrs.onMouseLeave = {
                                console.log("Leaving ${item.id}")
                                props.onMouseLeave(item.id)
                            }
                            mListItemText("${item.name} - ${item.symbol}") {
                                css {
                                    marginLeft = ((view.path(item.id).size -1) * 2).spacingUnits
                                }
                            }
                            if (props.isSelected(item.id)) {
                                mListItemSecondaryAction {
                                    mIconButton("comment", onClick = {})
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
}

fun RBuilder.plan2(handler: Plan2Props.() -> Unit): ReactElement {
    return child(Plan2::class) {
        this.attrs(handler)
    }
}