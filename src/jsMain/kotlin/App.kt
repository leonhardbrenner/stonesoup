import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import react.*

private val scope = MainScope()

external interface AppState : RState {
    var value: String
}

class App : RComponent<RProps, AppState>() {
    //private var tab1Value = Label.Plan.text

    override fun AppState.init() {
        scope.launch {
            //val prioritizedChores = AtmApi.ChoreApi.index()
            setState {
                value = "Your balance is low"
            }
        }
    }

    override fun RBuilder.render() {
        inputComponent {

        }
    }
}

fun RBuilder.app() = child(App::class) {}
