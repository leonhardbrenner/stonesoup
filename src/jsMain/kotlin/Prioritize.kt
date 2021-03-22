import com.ccfraser.muirwik.components.*
import com.ccfraser.muirwik.components.menu.mMenuItem
import com.ccfraser.muirwik.components.table.*
import kotlinext.js.jsObject
import models.Chore
import react.*
import styled.StyledElementBuilder

/**
 * This can be used with anything that can be labeled with a number.
 */
object Prioritize {

    val Component = functionalComponent<RProps> {

        val (thing, setThing) = useState<Any>(Chores.path)

        val inputProps: RProps = jsObject { }
        inputProps.asDynamic().name = "name"
        inputProps.asDynamic().id = "id"
        mSelect(thing, name = "name", onChange = { event, _ -> setThing(event.targetValue) }) {
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

        enum class ColumnId { Name, Description, Id, Parent }

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
            mTableCell(align = MTableCellAlign.left, padding = MTableCellPadding.none) { +treeView.pathString(source.id!!) }
            mTableCell(align = MTableCellAlign.right) { +(source.description?:"") } //description
            mTableCell(align = MTableCellAlign.right) { +source.id!!.toString() }
            mTableCell(align = MTableCellAlign.right) { +treeView.pathString(source.parentId) }
        }

        override fun ColumnId.comparator(a: Chore, b: Chore) = when (this) {
            ColumnId.Name -> (a.name?:"").compareTo(b.name?:"")
            ColumnId.Description -> 0
            ColumnId.Id -> a.id!!.compareTo(b.id!!)
            ColumnId.Parent -> a.parentId!!.compareTo(b.parentId!!)
        }

        override val Chore._id get() = id!!

        override val columnData = listOf(
            ColumnData(ColumnId.Name, false, false, "Name"),
            ColumnData(ColumnId.Description, true, false, "Description"),
            ColumnData(ColumnId.Id, true, false, "Id"),
            ColumnData(ColumnId.Parent, true, false, "Parent")
        )

    }

}

fun RBuilder.prioritize() = child(Prioritize.Component) {}
