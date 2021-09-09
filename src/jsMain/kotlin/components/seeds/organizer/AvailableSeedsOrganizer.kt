package components.seeds.organizer

import com.ccfraser.muirwik.components.mCheckbox
import com.ccfraser.muirwik.components.table.MTableCellAlign
import com.ccfraser.muirwik.components.table.MTableCellPadding
import com.ccfraser.muirwik.components.table.mTableCell
import components.Table
import models.SeedsResources
import react.RBuilder
import styled.StyledElementBuilder

class AvailableSeedsOrganizer(props: Props<ColumnId>): Table<SeedsResources.MySeeds, AvailableSeedsOrganizer.ColumnId>(props) {

    override suspend fun get() = SeedsApi.MySeedsApi.index()

    override fun SeedsResources.MySeeds.label() = description

    enum class ColumnId { Description, Id, CompanyId, SeedId, Name, Germination, Maturity }

    override var orderByColumn: ColumnId = ColumnId.Description

    override fun StyledElementBuilder<*>.buildRow(source: SeedsResources.MySeeds, isSelected: Boolean) {
        mTableCell(padding = MTableCellPadding.checkbox) {
            mCheckbox(isSelected)
        }
        mTableCell(align = MTableCellAlign.left, padding = MTableCellPadding.none) { +source.description }
        mTableCell(align = MTableCellAlign.right) { +source.id.toString() }
        mTableCell(align = MTableCellAlign.right) { +source.seedId }
        mTableCell(align = MTableCellAlign.right) { +source.companyId }
        mTableCell(align = MTableCellAlign.right) { +(source.detailedSeed?.name ?: "") }
        mTableCell(align = MTableCellAlign.right) { +source.germinationTest }
        mTableCell(align = MTableCellAlign.right) { +(source.detailedSeed?.maturity ?: "") }
    }

    override fun ColumnId.comparator(a: SeedsResources.MySeeds, b: SeedsResources.MySeeds) = when (this) {
        ColumnId.Description -> (a.description).compareTo(b.description)
        ColumnId.Id -> a.id.compareTo(b.id)
        ColumnId.SeedId -> (a.seedId).compareTo(b.seedId)
        ColumnId.CompanyId -> (a.companyId).compareTo(b.companyId)
        ColumnId.Name -> (a.detailedSeed?.name ?: "").compareTo(b.detailedSeed?.name ?: "")
        ColumnId.Germination -> a.germinationTest.compareTo(b.germinationTest)
        ColumnId.Maturity -> (a.detailedSeed?.maturity ?: "").compareTo(b.detailedSeed?.maturity ?: "")
    }

    override val SeedsResources.MySeeds._id get() = id

    override val columnData = listOf(
        ColumnData(ColumnId.Description, false, true, "Description"),
        ColumnData(ColumnId.Id, true, false, "Id"),
        ColumnData(ColumnId.CompanyId, true, false, "CompanyId"),
        ColumnData(ColumnId.SeedId, true, false, "SeedId"),
        ColumnData(ColumnId.Name, true, false, "Name"),
        ColumnData(ColumnId.Germination, true, false, "Germination"),
        ColumnData(ColumnId.Maturity, true, false, "Maturity")
    )

}

fun RBuilder.availableSeedOrganizer(
    handler: Table.Props<AvailableSeedsOrganizer.ColumnId>.() -> Unit
) = child(AvailableSeedsOrganizer::class) { attrs { handler() } }
