package ua.sviatkuzbyt.yourmath.app.ui.states

import ua.sviatkuzbyt.yourmath.app.ui.other.ErrorData
import ua.sviatkuzbyt.yourmath.domain.structures.PinUnpinFormulaItems

data class MainState(
    val searchText: String = "",
    val formulas: PinUnpinFormulaItems = PinUnpinFormulaItems(listOf(), listOf()),
    val isLoading: Boolean = true,
    val errorMessage: ErrorData? = null
)
