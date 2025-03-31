package ua.sviatkuzbyt.yourmath.app.ui.states

import ua.sviatkuzbyt.yourmath.domain.structures.FormulaListItem

data class MainState(
    val searchText: String = "",
    val listPinnedFormulas: List<FormulaListItem> = listOf(),
    val listNoPinnedFormulas: List<FormulaListItem> = listOf()
)
