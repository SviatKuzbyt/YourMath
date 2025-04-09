package ua.sviatkuzbyt.yourmath.app.presenter.controllers.history

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import ua.sviatkuzbyt.yourmath.app.R
import ua.sviatkuzbyt.yourmath.app.presenter.other.ErrorData
import ua.sviatkuzbyt.yourmath.domain.structures.history.FormulaFilterItem
import ua.sviatkuzbyt.yourmath.app.presenter.other.HistoryItem

data class HistoryState(
    val listContent: HistoryListContent = HistoryListContent.Nothing,
    val aboveScreenContent: HistoryAboveScreenContent = HistoryAboveScreenContent.Nothing
)

sealed class HistoryListContent{
    data class Items(
        val list: List<HistoryItem>,
        val isLoadMore: Boolean
    ): HistoryListContent()
    data class EmptyScreen(val info: EmptyScreenInfo): HistoryListContent()
    data object Nothing: HistoryListContent()
}

sealed class HistoryAboveScreenContent {
    data class ErrorDialog(val data: ErrorData): HistoryAboveScreenContent()
    data object CleanDialog: HistoryAboveScreenContent()
    data class FilterSheet(val list: List<FormulaFilterItem>): HistoryAboveScreenContent()
    data object Nothing: HistoryAboveScreenContent()
}

data class EmptyScreenInfo(
    @StringRes val message: Int,
    @DrawableRes val icon: Int
){
    companion object{
        fun noItemsInHistory() = EmptyScreenInfo(
            message = R.string.no_history,
            icon = R.drawable.ic_no_history
        )
        fun noFilterResultHistory() = EmptyScreenInfo(
            message = R.string.no_history_by_filter,
            icon = R.drawable.ic_no_filters
        )
    }
}