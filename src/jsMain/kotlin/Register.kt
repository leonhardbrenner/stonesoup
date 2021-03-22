import Register.DisplayComponent.ComponentStyles.inline
import Register.DisplayComponent.ComponentStyles.listDiv
import com.ccfraser.muirwik.components.*
import com.ccfraser.muirwik.components.list.*
import com.ccfraser.muirwik.components.menu.mMenuItem
import generated.model.Seeds
import generated.model.SeedsDto
import kotlinext.js.jsObject
import react.*
import react.dom.*
import kotlinx.coroutines.*
import kotlinx.css.*
import models.Resources
import styled.StyleSheet
import styled.css
import styled.styledDiv

private val scope = MainScope()

fun RBuilder.register() = child(Register.Component) {}

//Registry is for creating categories and to add, update, and delete items from category
object Register {

    interface Props : RProps

    interface State<T> : RState {
        var items: List<Pair<String, T>>
        var currentSeed: String
    }

    val Component = functionalComponent<RProps> {
        val (type, setType) = useState<Any>(SeedsDto.DetailedSeed.path)
        val inputProps: RProps = jsObject { }
        inputProps.asDynamic().name = "type"
        inputProps.asDynamic().id = "type-simple"
        mSelect(type, name = "type", onChange = { event, _ -> setType(event.targetValue) }) {
            attrs.inputProps = inputProps
            mMenuItem("My Seeds", value = SeedsDto.MySeeds.path)
            mMenuItem("Available Seeds", value = SeedsDto.DetailedSeed.path)
            mMenuItem("Category", value = SeedsDto.SeedCategory.path)
        }
        when (type) {
            SeedsDto.MySeeds.path -> mySeeds {}
            SeedsDto.DetailedSeed.path -> detailedSeed {}
            SeedsDto.SeedCategory.path -> category {}
        }
    }

    private abstract class DisplayComponent<T> (props: Props) : RComponent<Props, State<T>>() {

        override fun State<T>.init() {
            items = listOf()
            scope.launch {
                val seeds: List<T> = get()
                setState {
                    items = seeds.map { it.label() to it }
                }
            }
        }

        private object ComponentStyles : StyleSheet("ComponentStyles", isStatic = true) {
            val listDiv by css {
                display = Display.inlineFlex
                padding(1.spacingUnits)
            }

            val inline by css {
                display = Display.inlineBlock
            }
        }

        override fun RBuilder.render() {
            // For building things that we don't want to render now (e.g. the component will render it later), we need
            // another builder.
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
                        state.items.forEach { (name, callback) ->
                            mListItem(alignItems = MListItemAlignItems.flexStart, button = true, onClick = {
                                setState {
                                    currentSeed = callback.transform()
                                }
                            }) {
                                mListItemText(builder2.span { +name }, builder2.span {
                                    mTypography(name + " again", component = "span", variant = MTypographyVariant.body2) { css(inline) }
                                })
                            }
                        }
                    }
                    mContainer {
                        mTypography(state.currentSeed, component = "span", variant = MTypographyVariant.body2) { css(inline) }
                    }
                }
            }
        }
        abstract suspend fun get(): List<T>
        abstract fun T.label(): String
        abstract fun T.transform(): String

    }

    private class MySeeds(props: Props): DisplayComponent<Resources.MySeeds>(props) {
        override suspend fun get() = SeedsApi.getMySeeds()
        override fun Resources.MySeeds.label() = description //I don't think extension function is a good choice
        override fun Resources.MySeeds.transform() = detailedSeed?.image?:"No image found"
    }
    fun RBuilder.mySeeds(handler: Props.() -> Unit) = child(MySeeds::class) { attrs { handler() } }

    private class DetailedSeed(props: Props): DisplayComponent<Seeds.DetailedSeed>(props) {
        override suspend fun get() = SeedsApi.getDetailedSeed()
        override fun Seeds.DetailedSeed.label() = name
        override fun Seeds.DetailedSeed.transform() = name
    }
    fun RBuilder.detailedSeed(handler: Props.() -> Unit) = child(DetailedSeed::class) { attrs { handler() } }

    private class Category(props: Props): DisplayComponent<Seeds.SeedCategory>(props) {
        override suspend fun get() = SeedsApi.getCategory()
        override fun Seeds.SeedCategory.label() = name
        override fun Seeds.SeedCategory.transform() = image
    }
    fun RBuilder.category(handler: Props.() -> Unit) = child(Category::class) { attrs { handler() } }
}
