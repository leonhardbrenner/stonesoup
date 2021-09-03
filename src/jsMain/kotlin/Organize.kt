import com.ccfraser.muirwik.components.*
import com.ccfraser.muirwik.components.menu.mMenuItem
import com.ccfraser.muirwik.components.table.*
import generated.model.SeedsDto
import kotlinext.js.jsObject
import models.Resources
import react.*
import styled.StyledElementBuilder

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
            SeedsDto.MySeeds.path -> seeds {
                title = "These are your seeds."
                sortTemplate = { col: AvailableSeeds.ColumnId, direction: MTableCellSortDirection ->
                    "Your seeds ordered by $col $direction"
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

class AvailableSeeds(props: Props<ColumnId>): Table<Resources.MySeeds, AvailableSeeds.ColumnId>(props) {

    override suspend fun get() = Api.MySeeds.index()

    override fun Resources.MySeeds.label() = description

    enum class ColumnId { Description, Id, SeedLabel, Name, Germination, Maturity }

    override var orderByColumn: ColumnId = ColumnId.Description

    override fun StyledElementBuilder<*>.buildRow(source: Resources.MySeeds, isSelected: Boolean) {
        mTableCell(padding = MTableCellPadding.checkbox) {
            mCheckbox(isSelected)
        }
        mTableCell(align = MTableCellAlign.left, padding = MTableCellPadding.none) { +source.description }
        mTableCell(align = MTableCellAlign.right) { +source.id.toString() }
        mTableCell(align = MTableCellAlign.right) { +source.seed_label }
        mTableCell(align = MTableCellAlign.right) { +(source.detailedSeed?.name ?: "") }
        mTableCell(align = MTableCellAlign.right) { +source.germination_test }
        mTableCell(align = MTableCellAlign.right) { +(source.detailedSeed?.maturity ?: "") }
    }

    override fun ColumnId.comparator(a: Resources.MySeeds, b: Resources.MySeeds) = when (this) {
        ColumnId.Description -> (a.description).compareTo(b.description)
        ColumnId.Id -> a.id.compareTo(b.id)
        ColumnId.SeedLabel -> (a.seed_label).compareTo(b.seed_label)
        ColumnId.Name -> (a.detailedSeed?.name ?: "").compareTo(b.detailedSeed?.name ?: "")
        ColumnId.Germination -> a.germination_test.compareTo(b.germination_test)
        ColumnId.Maturity -> (a.detailedSeed?.maturity ?: "").compareTo(b.detailedSeed?.maturity ?: "")
    }

    override val Resources.MySeeds._id get() = id

    override val columnData = listOf(
        ColumnData(ColumnId.Description, false, true, "Description"),
        ColumnData(ColumnId.Id, true, false, "Id"),
        ColumnData(ColumnId.SeedLabel, true, false, "Label"),
        ColumnData(ColumnId.Name, true, false, "Name"),
        ColumnData(ColumnId.Germination, true, false, "Germination"),
        ColumnData(ColumnId.Maturity, true, false, "Maturity")
    )

}

fun RBuilder.seeds(handler: Table.Props<AvailableSeeds.ColumnId>.() -> Unit) = child(AvailableSeeds::class) { attrs { handler() } }

data class Dessert(
    val id: Int,
    val name: String,
    val calories: Int,
    val fat: Double,
    val carbs: Int,
    val protein: Double
)

class DessertOrganizer(props: Props<ColumnId>): Table<Dessert, DessertOrganizer.ColumnId>(props) {

    companion object {
        val path = "dessert"
    }

    enum class ColumnId { Name, Calories, Fat, Carbs, Protein } //TODO - get me out of here.

    override fun Dessert.label() = name

    override var orderByColumn: ColumnId = ColumnId.Name

    //Needs abstract
    override suspend fun get() = mutableListOf(
        Dessert(1, "Cupcake", 305, 3.7, 67, 4.3),
        Dessert(2, "Donut", 452, 25.0, 51, 4.9),
        Dessert(3, "Eclair", 262, 16.0, 24, 6.0),
        Dessert(4, "Frozen yoghurt", 159, 6.0, 24, 4.0),
        Dessert(5, "Gingerbread", 356, 16.0, 49, 3.9),
        Dessert(6, "Honeycomb", 408, 3.2, 87, 6.5),
        Dessert(7, "Ice cream sandwich", 237, 9.0, 37, 4.3),
        Dessert(8, "Jelly bean", 375, 0.0, 94, 0.0),
        Dessert(9, "KitKat", 518, 26.0, 65, 7.0),
        Dessert(10, "Lollipop", 392, 0.2, 98, 0.0),
        Dessert(11, "Marshmallow", 318, 0.0, 81, 2.0),
        Dessert(12, "Nougat", 360, 19.0, 9, 37.0),
        Dessert(13, "Oreo", 437, 18.0, 63, 4.0)
    )

    override fun StyledElementBuilder<*>.buildRow(source: Dessert, isSelected: Boolean) {
        mTableCell(padding = MTableCellPadding.checkbox) {
            mCheckbox(isSelected)
        }
        mTableCell(align = MTableCellAlign.left, padding = MTableCellPadding.none) { +source.name }
        mTableCell(align = MTableCellAlign.right) { +source.calories.toString() }
        mTableCell(align = MTableCellAlign.right) { +source.fat.toString() }
        mTableCell(align = MTableCellAlign.right) { +source.carbs.toString() }
        mTableCell(align = MTableCellAlign.right) { +source.protein.toString() }
    }

    override fun ColumnId.comparator(a: Dessert, b: Dessert) = when (this) {
        //Move this out
        ColumnId.Name -> a.name.compareTo(b.name)
        ColumnId.Calories -> a.calories.compareTo(b.calories)
        ColumnId.Carbs -> a.carbs.compareTo(b.carbs)
        ColumnId.Fat -> a.fat.compareTo(b.fat)
        ColumnId.Protein -> a.protein.compareTo(b.protein)
    }

    override val Dessert._id get() = id //TODO - handle this differently. Perhaps make an interfaces on the items.

    override val columnData = listOf(
        ColumnData(ColumnId.Name, false, true, "Dessert (100g serving)"),
        ColumnData(ColumnId.Calories, true, false, "Calories"),
        ColumnData(ColumnId.Fat, true, false, "Fat (g)"),
        ColumnData(ColumnId.Carbs, true, false, "Carbs (g)"),
        ColumnData(ColumnId.Protein, true, false, "Protein (g)")
    )

}

fun RBuilder.dessertOrganizer(handler: Table.Props<DessertOrganizer.ColumnId>.() -> Unit) = child(DessertOrganizer::class) { attrs { handler() } }


