package components.seeds

import SeedsApi
import components.seeds.DisplayComponent.ComponentStyles.inline
import components.seeds.DisplayComponent.ComponentStyles.listDiv
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
import models.SeedsResources
import styled.StyleSheet
import styled.css
import styled.styledDiv

private val scope = MainScope()

fun RBuilder.register(handler: RegisterProps.() -> Unit): ReactElement {
    return child(RegisterComponent::class) {
        this.attrs(handler)
    }
}

external interface RegisterProps : RProps {
    var type: String
    var setType: (String) -> Unit
}

class RegisterComponent : RComponent<RegisterProps, RState>() {

    override fun RBuilder.render() {
        val inputProps: RProps = jsObject { }
        inputProps.asDynamic().name = "type"
        inputProps.asDynamic().id = "type-simple"
        mSelect(props.type,
            name = "type",
            onChange = { event, _ -> props.setType(event.targetValue as String) }
        ) {
            attrs.inputProps = inputProps
            mMenuItem("My Seeds", value = SeedsDto.MySeeds.path)
            mMenuItem("Available Seeds", value = SeedsDto.DetailedSeed.path)
            mMenuItem("Category", value = SeedsDto.SeedCategory.path)
            mMenuItem("app.seeds.Chore", value = SeedsDto.Chore.path)
        }
        when (props.type) {
            SeedsDto.MySeeds.path -> mySeeds {}
            SeedsDto.DetailedSeed.path -> detailedSeed {}
            SeedsDto.SeedCategory.path -> category {}
            SeedsDto.Chore.path -> localChores {}
        }
    }
}

private class MySeeds(props: DisplayProps): DisplayComponent<SeedsResources.MySeeds>(props) {
    override suspend fun get() = SeedsApi.MySeedsApi.index()
    override fun SeedsResources.MySeeds.label() = description //I don't think extension function is a good choice
    override fun SeedsResources.MySeeds.transform() = "$id ${detailedSeed?.image?:"No image found"}"
}
fun RBuilder.mySeeds(handler: DisplayProps.() -> Unit) = child(MySeeds::class) { attrs { handler() } }

private class DetailedSeed(props: DisplayProps): DisplayComponent<Seeds.DetailedSeed>(props) {
    override suspend fun get() = SeedsApi.DetailedSeedsApi.index()
    override fun Seeds.DetailedSeed.label() = name
    override fun Seeds.DetailedSeed.transform() = "$id $name"
}
fun RBuilder.detailedSeed(handler: DisplayProps.() -> Unit) = child(DetailedSeed::class) { attrs { handler() } }

private class SeedCategory(props: DisplayProps): DisplayComponent<Seeds.SeedCategory>(props) {
    override suspend fun get() = SeedsApi.CategoryApi.index()
    override fun Seeds.SeedCategory.label() = name
    override fun Seeds.SeedCategory.transform() = "$id $name"
}
fun RBuilder.category(handler: DisplayProps.() -> Unit) = child(SeedCategory::class) { attrs { handler() } }

private class Chore(props: DisplayProps): DisplayComponent<Seeds.Chore>(props) {
    override suspend fun get() = SeedsApi.ChoreApi.index()
    override fun Seeds.Chore.label() = name
    override fun Seeds.Chore.transform() = "$id $name"
}
fun RBuilder.localChores(handler: DisplayProps.() -> Unit) = child(Chore::class) { attrs { handler() } }

external interface DisplayProps : RProps

external interface DisplayState<T> : RState {
    var items: List<Pair<String, T>>
    var currentSeed: String
}

private abstract class DisplayComponent<T> (props: DisplayProps) : RComponent<DisplayProps, DisplayState<T>>() {

    override fun DisplayState<T>.init() {
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
