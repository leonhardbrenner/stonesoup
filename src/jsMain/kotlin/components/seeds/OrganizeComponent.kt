package components.seeds

import components.seeds.organizer.*
import com.ccfraser.muirwik.components.*
import com.ccfraser.muirwik.components.menu.mMenuItem
import com.ccfraser.muirwik.components.table.*
import generated.model.SeedsDto
import kotlinext.js.jsObject
import react.*

//Todo - I think these should be moved to a top level file.
fun RBuilder.organize(handler: OrganizeProps.() -> Unit): ReactElement {
    return child(OrganizeComponent::class) {
        this.attrs(handler)
    }
}

external interface OrganizeProps : RProps {
    var thing: String
    var setThing: (String) -> Unit
}

class OrganizeComponent : RComponent<OrganizeProps, RState>() {

    override fun RBuilder.render() {
        val inputProps: RProps = jsObject { }
        inputProps.asDynamic().name = "name"
        inputProps.asDynamic().id = "id"
        mSelect(props.thing,
            name = "Name",
            onChange = { event, _ -> props.setThing(event.targetValue as String)
        }) {
            attrs.inputProps = inputProps
            mMenuItem("Seeds", value = SeedsDto.MySeeds.path)
            mMenuItem("Deserts", value = DessertOrganizer.path)
        }
        when (props.thing) {
            SeedsDto.MySeeds.path -> availableSeedOrganizer {
                title = "These are your app.seeds.seeds."
                sortTemplate = { col: AvailableSeedsOrganizer.ColumnId, direction: MTableCellSortDirection ->
                    "Your app.seeds.seeds ordered by $col $direction"
                }
            } //TODO - this should point to SeedsOrganizer
            DessertOrganizer.path -> dessertOrganizer {
                title = "How about a dessert?"
                sortTemplate = { col: DessertOrganizer.ColumnId, direction: MTableCellSortDirection ->
                    "Desserts ordered by $col $direction"
                }
            }
        }
    }

}


