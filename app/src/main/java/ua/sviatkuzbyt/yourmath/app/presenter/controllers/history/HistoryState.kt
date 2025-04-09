package ua.sviatkuzbyt.yourmath.app.presenter.controllers.history

import ua.sviatkuzbyt.yourmath.app.presenter.other.ErrorData
import ua.sviatkuzbyt.yourmath.domain.structures.history.HistoryItem

data class HistoryState(
    val items: List<HistoryItem> = listOf(),
    val errorMessage: ErrorData? = null,
    val allDataIsLoaded: Boolean = true,
    val showCleanDialog: Boolean = false,
    val isRecords: Boolean = true
)