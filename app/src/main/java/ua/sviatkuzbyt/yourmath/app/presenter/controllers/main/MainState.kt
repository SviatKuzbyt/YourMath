package ua.sviatkuzbyt.yourmath.app.presenter.controllers.main

import ua.sviatkuzbyt.yourmath.app.presenter.other.basic.ErrorData
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

//TODO update MainScreen like History and add comments
//data class MainState(
//    val searchText: String = "",
//    val list: MainList = MainList.Nothing,
//    val errorMessage: ErrorData? = null,
//)
//
//sealed class MainList{
//    data class FormulaList(val formulas: SplitFormulaItems): MainList()
//    data object NoFormulas: MainList()
//    data object NoSearchResult: MainList()
//    data object Nothing: MainList()
//}