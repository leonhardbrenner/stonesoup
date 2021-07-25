import Plan2.ComponentStyles.listDiv
import com.ccfraser.muirwik.components.*
import com.ccfraser.muirwik.components.button.mIconButton
import com.ccfraser.muirwik.components.card.mCard
import com.ccfraser.muirwik.components.card.mCardActionArea
import com.ccfraser.muirwik.components.card.mCardContent
import com.ccfraser.muirwik.components.list.*
import com.ccfraser.muirwik.components.transitions.mCollapse
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import kotlinx.css.*
import models.Chore
import react.*
import react.dom.span
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
}

//class Plan2 : RComponent<RProps, PlanState>() {
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
                //mList {
                //    css(themeStyles.list)
                //    mListSubheader { +"Nested List Items" }
                //    mListItemWithIcon("send", "Sent mail")
                //    mListItemWithIcon("inbox", "Inbox", onClick = { setState { expanded = !expanded } }) {
                //        if (expanded) mIcon("expand_less") else mIcon("expand_more")
                //    }
                //    mCollapse(show = expanded) {
                //        mList(disablePadding = true) {
                //            mListItem(button = true) {
                //                css { paddingLeft = 8.spacingUnits }
                //                mIcon("star")
                //                mListItemText(builder2.span { +"Starred (v1)" }, inset = true)
                //            }
                //            mListItemWithIcon("star", "Starred (v2)") { css { paddingLeft = 8.spacingUnits } }
                //        }
                //    }
                //    mListSubheader { +"Other Type of Items" }
                //    mListItemWithIcon("add_shopping_cart", "With Primary Text", "And secondary text")
                //    mListItem(button = true) {
                //        mListItemText(primary = "With a secondary action")
                //        mListItemSecondaryAction {
                //            mIconButton("comment", onClick = {})
                //        }
                //    }
                //    mListItem(button = true) {
                //        mListItemText(primary = "With a secondary action 2")
                //        mListItemSecondaryAction {
                //            mIconButton("comment", onClick = {})
                //        }
                //    }
                //}

                mList {
                    val view = TreeView(0, props.chores)
                    view.walk { item ->
                        mListItem {
                            key = item.id!!.toString()//toString()
                            attrs.onClick = {
                                console.log("Delete ${item.id}")
                                props.deleteChore(item.id)
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
                            if (props.isMouseIn(item.id)) {
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