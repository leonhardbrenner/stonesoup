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

        override suspend fun get() = PlanPrioritizeApi.get()

        override fun Chore.label() = name

        enum class ColumnId { Id, Description, Priority, RequiredBy }

        override var orderByColumn: ColumnId = ColumnId.Description

        override fun StyledElementBuilder<*>.buildRow(source: Chore, isSelected: Boolean) {
            mTableCell(padding = MTableCellPadding.checkbox) {
                mCheckbox(isSelected)
            }
            mTableCell(align = MTableCellAlign.left, padding = MTableCellPadding.checkbox) { +source.id.toString().padStart(4) }
            mTableCell(align = MTableCellAlign.left) { +(source.name) }
            mTableCell(align = MTableCellAlign.left) { +(source.priority?.toString()?:"") }
            mTableCell(align = MTableCellAlign.left) { +(if (source.parentId==0) "" else treeView[source.parentId].name) }
        }

        override fun ColumnId.comparator(a: Chore, b: Chore) = when (this) {
            ColumnId.Id -> (a.id).compareTo(b.id)
            ColumnId.Description -> (a.name).compareTo(b.name)
            ColumnId.Priority -> a.priority!!.compareTo(b.priority!!)
            ColumnId.RequiredBy -> treeView[a.parentId].name.compareTo(treeView[b.parentId].name)
        }

        override val Chore._id get() = id!!

        override val columnData = listOf(
            ColumnData(ColumnId.Id, false, false, "Id"),
            ColumnData(ColumnId.Description, false, false, "Description"),
            ColumnData(ColumnId.Priority, false, false, "Priority"),
            ColumnData(ColumnId.RequiredBy, false, false, "Required By")
        )

    }

}

fun RBuilder.prioritize() = child(Prioritize.Component) {}
