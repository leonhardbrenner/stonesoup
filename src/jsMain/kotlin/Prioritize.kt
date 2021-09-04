import com.ccfraser.muirwik.components.*
import com.ccfraser.muirwik.components.menu.mMenuItem
import com.ccfraser.muirwik.components.table.*
import generated.model.Seeds
import kotlinext.js.jsObject
import react.*
import styled.StyledElementBuilder


fun RBuilder.prioritize(handler: PrioritizeProps.() -> Unit): ReactElement {
    return child(PrioritizeComponent::class) {
        this.attrs(handler)
    }
}

external interface PrioritizeProps : RProps {
    var thing: String
    var setThing: (String) -> Unit
}

class PrioritizeComponent : RComponent<PrioritizeProps, RState>() {

    override fun RBuilder.render() {
        val inputProps: RProps = jsObject { }
        inputProps.asDynamic().name = "name"
        inputProps.asDynamic().id = "id"
        mSelect(props.thing,
            name = "name",
            onChange = { event, _ -> props.setThing(event.targetValue as String) }
        ) {
            attrs.inputProps = inputProps
            mMenuItem("Chores", value = Chores.path)
        }
        when (props.thing) {
            Chores.path -> chores {
                title = "Priorities in terms of Chores"
                sortTemplate = { col: Chores.ColumnId, direction: MTableCellSortDirection ->
                    "Chores ordered by $col $direction"}
            }
        }
    }
}

fun RBuilder.chores(handler: TreeTable.Props<Chores.ColumnId>.() -> Unit) =
    child(Chores::class) { attrs { handler() } }

class Chores(props: Props<ColumnId>): TreeTable<Seeds.Chore, Chores.ColumnId>(props) {

    companion object {
        val path = Chores::class.simpleName.toString()
    }

    override suspend fun get() = SeedsApi.Chore.index()

    override fun Seeds.Chore.label() = name

    enum class ColumnId { Id, Description/*, Priority*/, RequiredBy }

    override var orderByColumn: ColumnId = ColumnId.Description

    override fun StyledElementBuilder<*>.buildRow(source: Seeds.Chore, isSelected: Boolean) {
        mTableCell(padding = MTableCellPadding.checkbox) {
            mCheckbox(isSelected)
        }
        mTableCell(align = MTableCellAlign.left, padding = MTableCellPadding.checkbox) { +source.id.toString().padStart(4) }
        mTableCell(align = MTableCellAlign.left) { +(source.name) }
        //mTableCell(align = MTableCellAlign.left) { +(source.priority?.toString()?:"") }
        mTableCell(align = MTableCellAlign.left) { +(if (source.parentId==0) "" else treeView[source.parentId].name) }
    }

    override fun ColumnId.comparator(a: Seeds.Chore, b: Seeds.Chore) = when (this) {
        ColumnId.Id -> (a.id).compareTo(b.id)
        ColumnId.Description -> (a.name).compareTo(b.name)
        //ColumnId.Priority -> a.priority!!.compareTo(b.priority!!)
        ColumnId.RequiredBy -> treeView[a.parentId].name.compareTo(treeView[b.parentId].name)
    }

    override val Seeds.Chore._id get() = id

    override val columnData = listOf(
        ColumnData(ColumnId.Id, false, false, "Id"),
        ColumnData(ColumnId.Description, false, false, "Description"),
        //ColumnData(ColumnId.Priority, false, false, "Priority"),
        ColumnData(ColumnId.RequiredBy, false, false, "Required By")
    )

}
