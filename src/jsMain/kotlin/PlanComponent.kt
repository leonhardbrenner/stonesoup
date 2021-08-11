import PlanComponent.ComponentStyles.listDiv
import com.ccfraser.muirwik.components.*
import com.ccfraser.muirwik.components.button.mIconButton
import com.ccfraser.muirwik.components.list.*
import kotlinx.css.*
import models.Chore
import react.*
import styled.StyleSheet
import styled.css
import styled.styledDiv

external interface PlanProps: RProps {
    var chores: List<Chore>
    var deleteChore: (Int) -> Unit
    var handleInput: (String) -> Unit
    var onSelect: (Int) -> Unit
    var isSelected: (Int) -> Boolean
    //var onMouseEnter: (Int) -> Unit
    //var onMouseLeave: (Int) -> Unit
    //var isMouseIn: (Int) -> Boolean
}

class PlanComponent : RComponent<PlanProps, RState>() {
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
                        mListItemText("${item.name}") {
                            css {
                                marginLeft = ((view.path(item.id).size - 1) * 2).spacingUnits
                            }
                        }
                        if (props.isSelected(item.id)) {
                            mListItemSecondaryAction {
                                //TODO - change to edit action: to(textbox) expand an mCollapse[delete, priority, ]
                                mIconButton("comment",
                                    onClick = {
                                        props.deleteChore(item.id)
                                    })
                            }
                        }
                        //attrs.onMouseEnter = {
                        //    props.onMouseEnter(item.id)
                        //}
                        //attrs.onMouseLeave = {
                        //    props.onMouseLeave(item.id)
                        //}
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

fun RBuilder.plan(handler: PlanProps.() -> Unit): ReactElement {
    return child(PlanComponent::class) {
        this.attrs(handler)
    }
}
