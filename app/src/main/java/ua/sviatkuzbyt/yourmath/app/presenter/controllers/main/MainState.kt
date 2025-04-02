package ua.sviatkuzbyt.yourmath.app.presenter.controllers.main

import ua.sviatkuzbyt.yourmath.app.presenter.other.ErrorData
import ua.sviatkuzbyt.yourmath.domain.structures.PinUnpinFormulaItems

data class MainState(
    val searchText: String = "",
    val content: MainContent = MainContent.Empty,
    val errorMessage: ErrorData? = null
)

sealed class MainContent{
    data class Formulas(val lists: PinUnpinFormulaItems): MainContent()
    data object NoFormulas: MainContent()
    data object NoSearchResult: MainContent()
    data object Empty: MainContent()
}
