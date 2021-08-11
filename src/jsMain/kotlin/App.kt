import com.ccfraser.muirwik.components.*
import generated.model.SeedsDto
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import kotlinx.css.*
import models.*
import react.*
import styled.StyleSheet
import styled.css
import styled.styledDiv

private val scope = MainScope()

enum class Label(val text: String) {
    Register("Register"),
    Organize("Organize"),
    Plan("Plan"),
    Prioritize("Prioritize")
}

//TODO - move this back into Plan2. Then we can lift state and compose new components.
external interface AppState : RState {
    var tabValue: String
    var registerType: String
    var organizerThing: String
    var prioritizeThing: String
    var chores: List<Chore>
    var over: Int?
    var selected: Int?
}

class App : RComponent<RProps, AppState>() {
    //private var tab1Value = Label.Plan.text

    override fun AppState.init() {
        scope.launch {
            val prioritizedChores = PlanPrioritizeApi.get()
            setState {
                tabValue = Label.Plan.text
                chores = prioritizedChores
                over = null
                registerType = SeedsDto.DetailedSeed.path
                organizerThing = SeedsDto.MySeeds.path
                prioritizeThing = Chores.path
            }
        }
    }

    override fun RBuilder.render() {
        themeContext.Consumer { theme ->
            styledDiv {
                css { flexGrow = 1.0; backgroundColor = Color(theme.palette.background.paper) }
                mAppBar(position = MAppBarPosition.static) {
                    mTabs(state.tabValue,
                        onChange = { _, value -> setState { tabValue = value as String } }
                    ) {
                        //Todo - register, organize, prioritize
                        mTab(Label.Register.text, Label.Register.text)
                        mTab(Label.Organize.text, Label.Organize.text)
                        mTab(Label.Plan.text, Label.Plan.text) //This can be personal or community
                        mTab(Label.Prioritize.text, Label.Prioritize.text) //This can be personal or community
                    }
                }
                when (state.tabValue) {
                    Label.Register.text -> {
                        register {
                            type = state.registerType
                            setType = { type ->
                                setState { registerType = type}
                            }
                        }
                    }
                    Label.Organize.text -> {
                        organize {
                            thing = state.organizerThing
                            setThing = { thing ->
                                setState { organizerThing = thing }
                            }
                        }
                    }
                    Label.Plan.text -> {
                        plan {
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
                    }
                    Label.Prioritize.text -> {
                        prioritize {
                            thing = state.prioritizeThing
                            setThing = { thing ->
                                setState {
                                    organizerThing = thing
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

fun RBuilder.app() = child(App::class) {}
