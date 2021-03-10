import com.ccfraser.muirwik.components.*
import kotlinx.css.*
import react.*
import styled.StyleSheet
import styled.css
import styled.styledDiv

class App : RComponent<RProps, RState>() {
    private var tab1Value: Any = "seeds-organizer"

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
            //val themeStyles = object : StyleSheet("ComponentStyles", isStatic = true) {
            //    val tabRoot by css {
            //        textTransform = TextTransform.none
            //        fontWeight = FontWeight(theme.typography.fontWeightRegular.toString())
            //        marginRight = 4.spacingUnits
            //        hover {
            //            color = Color("#40a9ff")
            //            opacity = 1
            //        }
            //        focus {
            //            color = Color("#40a9ff")
            //        }
            //    }
            //    val tabSelected by css {
            //        color = Color("#1890ff")
            //        fontWeight = FontWeight(theme.typography.fontWeightMedium.toString())
            //    }
            //}
            styledDiv {
                css { flexGrow = 1.0; backgroundColor = Color(theme.palette.background.paper) }
                mAppBar(position = MAppBarPosition.static) {
                    mTabs(tab1Value, onChange = { _, value -> setState { tab1Value = value } }) {
                        mTab("Manifest", "seed-registry") //TODO - make these enums
                        mTab("Seed Orginizer", "seed-organizer")
                        mTab("Farm Priorities", "farm-priorities") //This can be personal or community
                    }
                }
                when (tab1Value) {
                    "seed-registry" -> { seedRegistry() }
                    "seed-organizer" -> { seedOrganizer() }
                    "farm-priorities" -> { farmPriorities() }
                }
            }
        }
    }
}

fun RBuilder.app() = child(App::class) {}
