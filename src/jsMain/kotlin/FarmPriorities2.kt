import com.ccfraser.muirwik.components.*
import com.ccfraser.muirwik.components.menu.mMenuItem
import com.ccfraser.muirwik.components.table.*
import generated.model.SeedsDto
import kotlinext.js.jsObject
import react.*
import styled.StyledElementBuilder

fun RBuilder.farmPriorities2() = child(FarmPriorities2.Component) {}
/**
 * This can be used with anything that can be labeled with a number.
 */
object FarmPriorities2 {

    val Component = functionalComponent<RProps> {

        val (thing, setThing) = useState<Any>(SeedsDto.MySeeds.path)

        val inputProps: RProps = jsObject { }
        inputProps.asDynamic().name = "thing"
        inputProps.asDynamic().id = "thing-simple"
        mSelect(thing, name = "thing", onChange = { event, _ -> setThing(event.targetValue) }) {
            attrs.inputProps = inputProps
            mMenuItem("Seeds", value = SeedsDto.MySeeds.path)
        }
        when (thing) {
            SeedsDto.MySeeds.path -> seeds {} //TODO - this should point to SeedsOrganizer
        }

    }

    private class Chores(props: Props): Table<models.Chore, Chores.ColumnId>(props) {

        override suspend fun get() = FarmPrioritiesApi.get()

        override fun models.Chore.label() = name

        enum class ColumnId { Name, Description, Id, ParentId }

        override var orderByColumn: ColumnId = ColumnId.Description

        override fun StyledElementBuilder<*>.buildRow(source: models.Chore, isSelected: Boolean) {
            mTableCell(padding = MTableCellPadding.checkbox) {
                mCheckbox(isSelected)
            }
            mTableCell(align = MTableCellAlign.left, padding = MTableCellPadding.none) { +source.name }
            mTableCell(align = MTableCellAlign.right) { +"" } //description
            mTableCell(align = MTableCellAlign.right) { +source.id!!.toString() }
            mTableCell(align = MTableCellAlign.right) { +source.parentId!!.toString() }
        }

        override fun ColumnId.comparator(a: models.Chore, b: models.Chore) = when (this) {
            ColumnId.Name -> (a.name?:"").compareTo(b.name?:"")
            ColumnId.Description -> 0
            ColumnId.Id -> a.id!!.compareTo(b.id!!)
            ColumnId.ParentId -> a.parentId!!.compareTo(b.parentId!!)
        }

        override val models.Chore._id get() = id!!

        override val columnData = listOf(
            ColumnData(ColumnId.Name, false, true, "Name"),
            ColumnData(ColumnId.Description, true, false, "Description"),
            ColumnData(ColumnId.Id, true, false, "Id"),
            ColumnData(ColumnId.Name, true, false, "Name")
        )

    }

    fun RBuilder.seeds(handler: Props.() -> Unit) = child(Chores::class) { attrs { handler() } }

}
