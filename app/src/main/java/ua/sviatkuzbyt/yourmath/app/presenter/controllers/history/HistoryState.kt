package ua.sviatkuzbyt.yourmath.app.presenter.controllers.history

import ua.sviatkuzbyt.yourmath.app.presenter.other.basic.EmptyScreenInfo
import ua.sviatkuzbyt.yourmath.app.presenter.other.basic.ErrorData
import ua.sviatkuzbyt.yourmath.domain.structures.history.FormulaFilterItem
import ua.sviatkuzbyt.yourmath.app.presenter.other.history.HistoryItem

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