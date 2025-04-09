package ua.sviatkuzbyt.yourmath.app.presenter.controllers.main

import ua.sviatkuzbyt.yourmath.app.presenter.other.ErrorData
import ua.sviatkuzbyt.yourmath.domain.structures.main.SplitFormulaItems

data class MainState(
    val searchText: String = "",
    val formulas: SplitFormulaItems = SplitFormulaItems(listOf(), listOf()),
    val showOnMainScreen: ShowOnMainScreen = ShowOnMainScreen.Nothing,
    val errorMessage: ErrorData? = null,
)

enum class ShowOnMainScreen{
    Formulas, NoFormulas, NoSearchResult, Nothing
}