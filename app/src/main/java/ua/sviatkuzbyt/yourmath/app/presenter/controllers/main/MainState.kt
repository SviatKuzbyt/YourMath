package ua.sviatkuzbyt.yourmath.app.presenter.controllers.main

import ua.sviatkuzbyt.yourmath.app.presenter.other.basic.EmptyScreenInfo
import ua.sviatkuzbyt.yourmath.app.presenter.other.basic.ErrorData
import ua.sviatkuzbyt.yourmath.domain.structures.main.SplitFormulaItems

data class MainState(
    val searchText: String = "",
    val listContent: MainListContent = MainListContent.Nothing,
    val errorMessage: ErrorData? = null,
)

sealed class MainListContent{
    data class FormulaList(val formulas: SplitFormulaItems): MainListContent()
    data class EmptyScreen(val info: EmptyScreenInfo): MainListContent()
    data object Nothing: MainListContent()
}