import com.ccfraser.muirwik.components.*
import com.ccfraser.muirwik.components.button.mIconButton
import com.ccfraser.muirwik.components.menu.mMenuItem
import com.ccfraser.muirwik.components.table.*
import generated.model.SeedsDto
import kotlinext.js.jsObject
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import kotlinx.css.*
import models.Resources
import react.*
import react.dom.br
import styled.StyleSheet
import styled.StyledElementBuilder
import styled.css
import styled.styledDiv
import kotlin.math.min

private val scope = MainScope()

fun RBuilder.seedOrganizer() = child(Orginizer.Component) {}
/**
 * This can be used with anything that can be labeled with a number.
 */
object Orginizer {

    val Component = functionalComponent<RProps> {

        val (thing, setThing) = useState<Any>(SeedsDto.MySeeds.path)

        val inputProps: RProps = jsObject { }
        inputProps.asDynamic().name = "thing"
        inputProps.asDynamic().id = "thing-simple"
        mSelect(thing, name = "thing", onChange = { event, _ -> setThing(event.targetValue) }) {
            attrs.inputProps = inputProps
            mMenuItem("Seeds", value = SeedsDto.MySeeds.path)
            mMenuItem("Deserts", value = DessertOrganizer.path)
        }
        when (thing) {
            SeedsDto.MySeeds.path -> seeds {} //TODO - this should point to SeedsOrganizer
            DessertOrganizer.path -> dessertOrganizer {}
        }

    }

    private class AvailableSeeds(props: Props): Orginizer.Table<Resources.MySeeds, AvailableSeeds.ColumnId>(props) {

        override suspend fun get() = SeedsApi.getMySeeds()

        override fun Resources.MySeeds.label() = description

        enum class ColumnId { Description, Id, SeedLabel, Name, Germination, Maturity }

        override var orderByColumn: ColumnId = ColumnId.Description

        override fun StyledElementBuilder<*>.buildRow(source: Resources.MySeeds, isSelected: Boolean) {
            mTableCell(padding = MTableCellPadding.checkbox) {
                mCheckbox(isSelected)
            }
            mTableCell(align = MTableCellAlign.left, padding = MTableCellPadding.none) { +source.description }
            mTableCell(align = MTableCellAlign.right) { +source.my_seed_id.toString() }
            mTableCell(align = MTableCellAlign.right) { +source.seed_label }
            mTableCell(align = MTableCellAlign.right) { +(source.detailedSeed?.name?:"") }
            mTableCell(align = MTableCellAlign.right) { +source.germination_test }
            mTableCell(align = MTableCellAlign.right) { +(source.detailedSeed?.maturity?:"") }
        }

        override fun ColumnId.comparator(a: Resources.MySeeds, b: Resources.MySeeds) = when (this) {
            ColumnId.Description -> (a.description).compareTo(b.description)
            ColumnId.Id -> a.my_seed_id.compareTo(b.my_seed_id)
            ColumnId.SeedLabel -> (a.seed_label).compareTo(b.seed_label)
            ColumnId.Name -> (a.detailedSeed?.name?:"").compareTo(b.detailedSeed?.name?:"")
            ColumnId.Germination -> a.germination_test.compareTo(b.germination_test)
            ColumnId.Maturity -> (a.detailedSeed?.maturity?:"").compareTo(b.detailedSeed?.maturity?:"")
        }

        override val Resources.MySeeds.id get() = my_seed_id

        override val columnData = listOf(
            ColumnData(ColumnId.Description, false, true, "Description"),
            ColumnData(ColumnId.Id, true, false, "Id"),
            ColumnData(ColumnId.SeedLabel, true, false, "Label"),
            ColumnData(ColumnId.Name, true, false, "Name"),
            ColumnData(ColumnId.Germination, true, false, "Germination"),
            ColumnData(ColumnId.Maturity, true, false, "Maturity")
        )

    }

    fun RBuilder.seeds(handler: Props.() -> Unit) = child(AvailableSeeds::class) { attrs { handler() } }

    data class Dessert(
        val id: Int,
        val name: String,
        val calories: Int,
        val fat: Double,
        val carbs: Int,
        val protein: Double
    )

    private class DessertOrganizer(props: Props): Orginizer.Table<Dessert, DessertOrganizer.ColumnId>(props) {

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

        override val Dessert.id get() = id //TODO - handle this differently. Perhaps make an interfaces on the items.

        override val columnData = listOf(
            ColumnData(ColumnId.Name, false, true, "Dessert (100g serving)"),
            ColumnData(ColumnId.Calories, true, false, "Calories"),
            ColumnData(ColumnId.Fat, true, false, "Fat (g)"),
            ColumnData(ColumnId.Carbs, true, false, "Carbs (g)"),
            ColumnData(ColumnId.Protein, true, false, "Protein (g)")
        )

    }

    fun RBuilder.dessertOrganizer(handler: Props.() -> Unit) = child(DessertOrganizer::class) { attrs { handler() } }

    interface Props : RProps

    interface State<T> : RState {
        var items: MutableList<Pair<Int, T>>
        var order: MTableCellSortDirection
    }

    data class ColumnData<T>(
        val name: T,
        val rightAligned: Boolean,
        val disablePadding: Boolean,
        val label: String
    )

    private abstract class Table<T, ColumnId>(props: Props) : RComponent<Props, State<T>>() {

        override fun State<T>.init() {
            items = mutableListOf()
            scope.launch {
                val seeds: List<T> = get()
                setState {
                    seeds.forEach { items.add(it.id to it) }
                    order = MTableCellSortDirection.asc
                }
            }
        }

        // State for sort and select example

        protected val selectedIds = mutableSetOf<Int>()
        protected var page = 0
        protected var rowsPerPage = 10

        abstract suspend fun get(): List<T>

        abstract fun T.label(): String

        abstract fun ColumnId.comparator(a: T, b: T): Int

        abstract val columnData: List<ColumnData<ColumnId>>

        abstract var orderByColumn: ColumnId

        abstract fun StyledElementBuilder<*>.buildRow(source: T, isSelected: Boolean)

        abstract val T.id: Int

        override fun RBuilder.render() {
            mTypography("Simple Table ${orderByColumn} ${state.order}")
            br { }
            br { }
            mTypography("Sorting and Selecting")
            sortingAndSelecting()
        }

        val size get() = state.items.size
        val Pair<Int, T>.id get() = first
        fun RBuilder.sortingAndSelecting() {
            mPaper {
                css {
                    width = 100.pct
                    marginTop = 3.spacingUnits
                }
                enhancedTableToolbar(selectedIds.size)
                styledDiv {
                    css { overflowX = Overflow.auto }
                    mTable {
                        css { minWidth = 700.px }
                        enhancedTableHead(
                            selectedIds.size, state.order, orderByColumn, size,
                            ::handleSelectAllClick, ::handleRequestSort
                        )
                        mTableBody {
                            state.items.subList(page * rowsPerPage, min((page + 1) * rowsPerPage, size)).forEach {
                                val isSelected = selectedIds.contains(it.id)
                                mTableRow(it.id, isSelected, true, onClick = { _ -> handleClick(it.id) }) {
                                    attrs.asDynamic().tabIndex = -1
                                    attrs.asDynamic().role = "checkbox"
                                    buildRow(it.second, isSelected)
                                }
                            }
                            val emptyRows = rowsPerPage - min(rowsPerPage, state.items.size - page * rowsPerPage)
                            if (emptyRows > 0) {
                                mTableRow {
                                    css { height = (49 * emptyRows).px }
                                    mTableCell(colSpan = 6)
                                }
                            }
                        }
                    }
                }
                mTablePagination(count = state.items.size, rowsPerPage = rowsPerPage, page = page,
                    onChangePage = { _, newPage -> setState { page = newPage } },
                    onChangeRowsPerPage = {
                        setState {
                            rowsPerPage = it.target.asDynamic().value
                            page = 0
                        }
                    })
            }
        }

        private fun handleSelectAllClick(checked: Boolean): Unit {
            setState {
                if (checked) {
                    selectedIds.addAll(state.items.map { it.first })
                } else {
                    selectedIds.clear()
                }
            }
        }

        private fun handleRequestSort(id: ColumnId): Unit {
            setState {
                if (orderByColumn == id) {
                    order = if (order == MTableCellSortDirection.asc)
                        MTableCellSortDirection.desc
                    else
                        MTableCellSortDirection.asc
                } else {
                    order = MTableCellSortDirection.asc
                }

                orderByColumn = id

                if (order == MTableCellSortDirection.asc) {
                    items.sortWith { a, b -> id.comparator(a.second, b.second) }
                } else {
                    items.sortWith { a, b -> id.comparator(b.second, a.second) }
                }
            }
        }

        protected fun handleClick(id: Int): Unit {
            setState {
                if (selectedIds.contains(id)) {
                    selectedIds.remove(id)
                } else {
                    selectedIds.add(id)
                }
            }
        }

        protected fun RBuilder.enhancedTableHead(
            numSelected: Int,
            order: MTableCellSortDirection,
            orderByColumn: ColumnId,
            rowCount: Int,
            onSelectAllClick: (checked: Boolean) -> Unit,
            onRequestSort: (id: ColumnId) -> Unit
        ) {
            mTableHead {
                mTableRow {
                    mTableCell(padding = MTableCellPadding.checkbox) {
                        mCheckbox(indeterminate = numSelected > 0 && numSelected < rowCount,
                            checked = numSelected == rowCount,
                            onChange = { _, checked -> onSelectAllClick(checked) })
                    }
                    columnData.forEach { data ->
                        mTableCell(
                            data.name,
                            align = if (data.rightAligned) MTableCellAlign.right else MTableCellAlign.left,
                            padding = if (data.disablePadding) MTableCellPadding.none else MTableCellPadding.default,
                            sortDirection = if (orderByColumn == data.name) order else MTableCellSortDirection.False
                        ) {
                            mTooltip(
                                "Sort",
                                if (data.rightAligned) TooltipPlacement.bottomEnd else TooltipPlacement.bottomStart,
                                enterDelay = 300
                            ) {
                                mTableSortLabel(data.label, orderByColumn == data.name,
//                                    iconFunction = { mIcon("star", addAsChild = false) },
                                    direction = if (order == MTableCellSortDirection.asc) MTableSortLabelDirection.asc else MTableSortLabelDirection.desc,
                                    onClick = { onRequestSort(data.name) })
                            }
                        }
                    }
                }
            }
        }

        fun RBuilder.enhancedTableToolbar(numSelected: Int) {
            themeContext.Consumer { theme ->
                val styles = object : StyleSheet("ComponentStyles", isStatic = true) {
                    //XXX - I was getting this error:
                    //  When accessing module declarations from UMD, they must be marked by both @JsModule and @JsNonModule
                    //
                    //val spacer by css {
                    //    flex(1.0, 1.0, 100.pct)
                    //}
                    //val highlight by css {
                    //    if (theme.palette.type == "light") {
                    //        color = Color(theme.palette.secondary.main)
                    //        backgroundColor = Color(lighten(theme.palette.secondary.light, 0.85))
                    //    } else {
                    //        color = Color(theme.palette.text.primary)
                    //        backgroundColor = Color(lighten(theme.palette.secondary.dark, 0.85))
                    //    }
                    //}
                    //val actions by css {
                    //    color = Color(theme.palette.text.secondary)
                    //}
                }

                mToolbar {
                    //if (numSelected > 0) css(styles.highlight)
                    styledDiv {
                        css { flex(0.0, 0.0, FlexBasis.auto) }
                        if (numSelected > 0) {
                            mTypography("$numSelected selected", variant = MTypographyVariant.subtitle1)
                        } else {
                            mTypography("Nutrition", variant = MTypographyVariant.h6)
                        }
                    }
                    //styledDiv { css(styles.spacer) }
                    styledDiv {
                        //css(styles.actions)
                        if (numSelected > 0) {
                            mTooltip("Delete") {
                                mIconButton("delete")
                            }
                        } else {
                            mTooltip("Filter list") {
                                mIconButton("filter_list")
                            }
                        }
                    }
                }
            }
        }
    }

}
