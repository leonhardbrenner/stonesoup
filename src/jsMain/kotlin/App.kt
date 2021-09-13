import com.ccfraser.muirwik.components.*
import components.seeds.*
import generated.model.SeedsDto
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import kotlinx.css.*
import models.SeedsResources
import react.*
import styled.css
import styled.styledDiv

private val scope = MainScope()

//val noMoreChores = listOf(
//    app.seeds.Chore(0, -1, listOf(1, 3), "<root>"),
//    app.seeds.Chore(1, 0, listOf(2), "A"),
//    app.seeds.Chore(2, 1, emptyList(), "B"),
//    app.seeds.Chore(3, 0, emptyList(), "C")
//)

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
    var chores: List<SeedsResources.Chore>
    var selected: Int?
    var over: Int?
}

class App : RComponent<RProps, AppState>() {
    //private var tab1Value = Label.Plan.text

    override fun AppState.init() {
        scope.launch {
            val prioritizedChores = SeedsApi.ChoreApi.index()
            setState {
                tabValue = Label.Organize.text
                chores = prioritizedChores
                registerType = SeedsDto.Chore.path
                organizerThing = SeedsDto.MySeeds.path
                prioritizeThing = Chores.path
                selected = null
                over = null
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
                        //Todo - app.seeds.register, app.seeds.organize, app.seeds.prioritize
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
                    //TODO - Move the complexity into PlanComponent. Consider using an abstract base class for handling.
                    Label.Plan.text ->
                        plan {
                            chores = state.chores
                            deleteChore = { id ->
                                scope.launch {
                                    SeedsApi.ChoreApi.destroy(id)
                                    val prioritizedChores = SeedsApi.ChoreApi.index()
                                    setState {
                                        chores = prioritizedChores
                                        selected = null //TODO - Yuck this is spaghetti. This is because in order to delete we once selected.
                                    }
                                }
                            }
                            onSelect = { id ->
                                setState {
                                    if (selected == null)
                                        selected = id
                                    else {
                                        if (id == selected)
                                            selected = null
                                        else {
                                            MainScope().launch {
                                                SeedsApi.ChoreApi.move(selected!!, parentId = id)
                                                val prioritizedChores = SeedsApi.ChoreApi.index()
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
                                scope.launch {
                                    SeedsApi.ChoreApi.create(1, input.replace("!", ""))
                                    val prioritizedChores = SeedsApi.ChoreApi.index()
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
