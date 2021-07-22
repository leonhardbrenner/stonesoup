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

external interface PlanState: RState {
    var chores: List<Chore>
}

//class Plan2 : RComponent<RProps, PlanState>() {
class Plan2 : RComponent<RProps, RState>() {
    private var expanded: Boolean = false

    private object ComponentStyles : StyleSheet("ComponentStyles", isStatic = true) {
        val listDiv by css {
            display = Display.inlineFlex
            padding(1.spacingUnits)
        }

    }

    //override fun PlanState.init() {
    //    val scope = MainScope()
    //    scope.launch {
    //        val prioritizedChores = PlanPrioritizeApi.get()
    //        setState {
    //            chores = prioritizedChores
    //        }
    //    }
    //}

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
                    css(themeStyles.list)
                    mListSubheader { +"Nested List Items" }
                    mListItemWithIcon("send", "Sent mail")
                    mListItemWithIcon("inbox", "Inbox", onClick = { setState { expanded = !expanded } }) {
                        if (expanded) mIcon("expand_less") else mIcon("expand_more")
                    }
                    mCollapse(show = expanded) {
                        mList(disablePadding = true) {
                            mListItem(button = true) {
                                css { paddingLeft = 8.spacingUnits }
                                mIcon("star")
                                mListItemText(builder2.span { +"Starred (v1)" }, inset = true)
                            }
                            mListItemWithIcon("star", "Starred (v2)") { css { paddingLeft = 8.spacingUnits } }
                        }
                    }
                    mListSubheader { +"Other Type of Items" }
                    mListItemWithIcon("add_shopping_cart", "With Primary Text", "And secondary text")
                    mListItem(button = true) {
                        mListItemText(primary = "With a secondary action")
                        mListItemSecondaryAction {
                            mIconButton("comment", onClick = {})
                        }
                    }
                    mListItem(button = true) {
                        mListItemText(primary = "With a secondary action 2")
                        mListItemSecondaryAction {
                            mIconButton("comment", onClick = {})
                        }
                    }
                }

                //mCard {
                //    val view = TreeView(0, state.chores)
                //    view.walk { item ->
                //        //chores.forEach { item ->
                //        mCardActionArea {
                //            key = item.id!!.toString()//toString()
                //            attrs.onClick = {
                //                //scope.launch {
                //                    //PlanPrioritizeApi.delete(item.id)
                //                    //val prioritizedChores = PlanPrioritizeApi.get()
                //                    //setState { chores = prioritizedChores }
                //                //}
                //            }
                //            //It would be neat to draw <root> as actual roots.
                //            //${"--".repeat(view.path(item.id!!).size + 1)}
                //            //${item.childrenIds}
                //            //+"${item.symbol}| ${item.parentId}__${item.name}"
                //            mCardContent {
                //                css {
                //                    marginLeft = ((view.path(item.id).size -1) * 10).px
                //                }
                //                mTypography("${item.name} - ${item.symbol}", component = "p" )
                //            }
                //
                //        }
                //    }
                //}
                //
                //inputComponent {
                //    onSubmit = { input ->
                //        //scope.launch {
                //        //    handleInput(input)
                //        //    val prioritizedChores = PlanPrioritizeApi.get()
                //            //setState { chores = prioritizedChores }
                //        //}
                //    }
                //}

            }
        }
    }
}

fun RBuilder.plan2() = child(Plan2::class) {}
