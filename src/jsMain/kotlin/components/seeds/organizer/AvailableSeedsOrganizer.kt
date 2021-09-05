package components.seeds.organizer

import com.ccfraser.muirwik.components.mCheckbox
import com.ccfraser.muirwik.components.table.MTableCellAlign
import com.ccfraser.muirwik.components.table.MTableCellPadding
import com.ccfraser.muirwik.components.table.mTableCell
import components.Table
import models.Resources
import react.RBuilder
import styled.StyledElementBuilder

class AvailableSeedsOrganizer(props: Props<ColumnId>): Table<Resources.MySeeds, AvailableSeedsOrganizer.ColumnId>(props) {

    override suspend fun get() = SeedsApi.MySeedsApi.index()

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

fun RBuilder.availableSeedOrganizer(
    handler: Table.Props<AvailableSeedsOrganizer.ColumnId>.() -> Unit
) = child(AvailableSeedsOrganizer::class) { attrs { handler() } }
