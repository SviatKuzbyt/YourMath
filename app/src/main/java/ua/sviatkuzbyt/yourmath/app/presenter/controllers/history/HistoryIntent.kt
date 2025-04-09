package ua.sviatkuzbyt.yourmath.app.presenter.controllers.history

sealed class HistoryIntent {
    data object LoadNewItems: HistoryIntent()
    data object ReloadItems: HistoryIntent()
    data object CloseErrorDialog: HistoryIntent()
    data class SetCleanDialog(val isShow: Boolean): HistoryIntent()
    data object CleanHistory: HistoryIntent()
    data object OpenFilters: HistoryIntent()
    data class SelectFilter(val formulaID: Long): HistoryIntent()
    data object CloseFilters: HistoryIntent()
}