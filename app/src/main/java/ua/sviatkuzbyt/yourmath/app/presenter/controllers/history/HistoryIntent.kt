package ua.sviatkuzbyt.yourmath.app.presenter.controllers.history

sealed class HistoryIntent {
    data object LoadNewItems: HistoryIntent()
    data object ReloadItems: HistoryIntent()
}