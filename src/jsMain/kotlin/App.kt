import com.ccfraser.muirwik.components.*
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import kotlinx.css.*
import models.*
import react.*
import styled.StyleSheet
import styled.css
import styled.styledDiv

enum class Label(val text: String) {
    Register("Register"),
    Organize("Organize"),
    Plan("Plan"),
    Plan2("Plan2"),
    Prioritize("Prioritize")
}

//TODO - move this back into Plan2. Then we can lift state and compose new components.
external interface AppState : RState {
    var chores: List<Chore>
    var over: Int?
    var selected: Int?
}

class App : RComponent<RProps, AppState>() {
    private var tab1Value: Any = "Organize"

    override fun AppState.init() {
        val scope = MainScope()
        scope.launch {
            val prioritizedChores = PlanPrioritizeApi.get()
            setState {
                chores = prioritizedChores
                over = null
            }
        }
    }

    private object CustomTabStyles : StyleSheet("ComponentStyles", isStatic = true) {
        val tabsRoot by css {
            borderBottom = "1px solid #e8e8e8"
        }
        val tabsIndicator by css {
            backgroundColor = Color("#1890ff")
        }
        val typography by css {
            padding(3.spacingUnits)
        }
    }

    private fun RBuilder.tabContainer(text: String) {
        mTypography(text) {
            css { padding(3.spacingUnits) }
        }
    }

    override fun RBuilder.render() {
        themeContext.Consumer { theme ->
            styledDiv {
                css { flexGrow = 1.0; backgroundColor = Color(theme.palette.background.paper) }
                mAppBar(position = MAppBarPosition.static) {
                    mTabs(tab1Value, onChange = { _, value -> setState { tab1Value = value } }) {
                        //Todo - register, organize, prioritize
                        mTab(Label.Register.text, Label.Register.text)
                        mTab(Label.Organize.text, Label.Organize.text)
                        mTab(Label.Plan.text, Label.Plan.text) //This can be personal or community
                        mTab(Label.Plan2.text, Label.Plan2.text) //This can be personal or community
                        mTab(Label.Prioritize.text, Label.Prioritize.text) //This can be personal or community
                    }
                }
                when (tab1Value) {
                    Label.Register.text -> {
                        register()
                    }
                    Label.Organize.text -> {
                        organize()
                    }
                    Label.Plan.text -> {
                        child(Plan) {}
                    }
                    Label.Plan2.text -> plan2 {
                        chores = state.chores
                        deleteChore = { id ->
                            val scope = MainScope()
                            scope.launch {
                                PlanPrioritizeApi.delete(id)
                                val prioritizedChores = PlanPrioritizeApi.get()
                                setState {
                                    chores = prioritizedChores
                                }
                            }
                        }
                        onMouseEnter = { id ->
                            setState {
                                over = id
                            }
                        }
                        onMouseLeave = { id ->
                            setState {
                                over = null
                            }
                        }
                        isMouseIn = { id ->
                            state.over?.equals(id) ?: false
                        }
                        onSelect = { id ->
                            setState {
                                if (selected == null)
                                    selected = id
                                else {
                                    if (id == selected)
                                        selected = null
                                    else {
                                        val chore = NodeUpdate(
                                            id = selected!!,
                                            moveTo = id
                                        )
                                        MainScope().launch {
                                            PlanPrioritizeApi.update(chore)
                                            val prioritizedChores = PlanPrioritizeApi.get()
                                            setState {
                                                chores = prioritizedChores
                                                selected = null
                                            }
                                        }
                                    }
                                }

                            }
                        }
                        isSelected = { id ->
                            state.selected == id
                        }
                        handleInput = { input: String ->
                            val scope = MainScope()
                            scope.launch {
                                val chore = ChoreCreate(
                                    name = input.replace("!", ""),
                                    priority = input.count { it == '!' })
                                PlanPrioritizeApi.add(chore)
                                val prioritizedChores = PlanPrioritizeApi.get()
                                setState {
                                    chores = prioritizedChores
                                }
                            }
                        }
                    }
                    Label.Prioritize.text -> {
                        prioritize()
                    }
                }
            }
        }
    }
}

fun RBuilder.app() = child(App::class) {}
