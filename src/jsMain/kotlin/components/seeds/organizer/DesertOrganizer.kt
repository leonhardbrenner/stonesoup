package components.seeds.organizer

import com.ccfraser.muirwik.components.mCheckbox
import com.ccfraser.muirwik.components.table.MTableCellAlign
import com.ccfraser.muirwik.components.table.MTableCellPadding
import com.ccfraser.muirwik.components.table.mTableCell
import components.Table
import react.RBuilder
import styled.StyledElementBuilder

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
        ColumnData(ColumnId.Name, false, true, "app.seeds.Dessert (100g serving)"),
        ColumnData(ColumnId.Calories, true, false, "Calories"),
        ColumnData(ColumnId.Fat, true, false, "Fat (g)"),
        ColumnData(ColumnId.Carbs, true, false, "Carbs (g)"),
        ColumnData(ColumnId.Protein, true, false, "Protein (g)")
    )

}

fun RBuilder.dessertOrganizer(handler: Table.Props<DessertOrganizer.ColumnId>.() -> Unit) = child(DessertOrganizer::class) { attrs { handler() } }
