import com.ccfraser.muirwik.components.*
import com.ccfraser.muirwik.components.menu.mMenuItem
import com.ccfraser.muirwik.components.table.*
import kotlinext.js.jsObject
import models.Chore
import react.*
import styled.StyledElementBuilder

fun RBuilder.farmPriorities2() = child(FarmPriorities2.Component) {}
/**
 * This can be used with anything that can be labeled with a number.
 */
object FarmPriorities2 {

    val Component = functionalComponent<RProps> {

        val (thing, setThing) = useState<Any>(Chores.path)

        val inputProps: RProps = jsObject { }
        inputProps.asDynamic().name = "thing"
        inputProps.asDynamic().id = "thing-simple"
        mSelect(thing, name = "thing", onChange = { event, _ -> setThing(event.targetValue) }) {
            attrs.inputProps = inputProps
            mMenuItem("Chores", value = Chores.path)
        }
        when (thing) {
            Chores.path -> chores {
                title = "Priorities in terms of Chores"
                sortTemplate = { col: Chores.ColumnId, direction: MTableCellSortDirection ->
                    "Chores ordered by $col $direction"}
            }
        }

    }

    fun RBuilder.chores(handler: TreeTable.Props<Chores.ColumnId>.() -> Unit) =
        child(Chores::class) { attrs { handler() } }

    class Chores(props: Props<ColumnId>): TreeTable<Chore, Chores.ColumnId>(props) {

        companion object {
            val path = Chores::class.simpleName.toString()
        }

        override suspend fun get() = FarmPrioritiesApi.get()

        override fun Chore.label() = name

        enum class ColumnId { Name, Description, Id, ParentId }

        override var orderByColumn: ColumnId = ColumnId.Description

        //fun tabs(source: Chore) {
        //    var node = source.id
        //    while (node!=null) {
        //        node = node.parentI
        //    }
        //}

        override fun StyledElementBuilder<*>.buildRow(source: Chore, isSelected: Boolean) {
            mTableCell(padding = MTableCellPadding.checkbox) {
                mCheckbox(isSelected)
            }
            val tabs = state.treeView.path(source.id!!).size - 1
            mTableCell(align = MTableCellAlign.left, padding = MTableCellPadding.none) { +"${"__".repeat(tabs)}${source.name}" }
            mTableCell(align = MTableCellAlign.right) { +"" } //description
            mTableCell(align = MTableCellAlign.right) { +source.id!!.toString() }
            mTableCell(align = MTableCellAlign.right) { +source.parentId!!.toString() }
        }

        override fun ColumnId.comparator(a: Chore, b: Chore) = when (this) {
            ColumnId.Name -> (a.name?:"").compareTo(b.name?:"")
            ColumnId.Description -> 0
            ColumnId.Id -> a.id!!.compareTo(b.id!!)
            ColumnId.ParentId -> a.parentId!!.compareTo(b.parentId!!)
        }

        override val Chore._id get() = id!!

        override val columnData = listOf(
            ColumnData(ColumnId.Name, false, false, "Name"),
            ColumnData(ColumnId.Description, true, false, "Description"),
            ColumnData(ColumnId.Id, true, false, "Id"),
            ColumnData(ColumnId.Name, true, false, "Name")
        )

    }

}
