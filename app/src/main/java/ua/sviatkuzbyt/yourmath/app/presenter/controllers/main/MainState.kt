package ua.sviatkuzbyt.yourmath.app.presenter.controllers.main

import ua.sviatkuzbyt.yourmath.app.presenter.other.ErrorData
import ua.sviatkuzbyt.yourmath.domain.structures.main.PinUnpinFormulaItems

data class MainState(
    val searchText: String = "",
    val formulas: PinUnpinFormulaItems = PinUnpinFormulaItems(listOf(), listOf()),
    val showOnScreen: ShowOnScreen = ShowOnScreen.Nothing,
    val errorMessage: ErrorData? = null,
)

enum class ShowOnScreen{
    Formulas, NoFormulas, NoSearchResult, Nothing
}