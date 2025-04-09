package ua.sviatkuzbyt.yourmath.app.presenter.controllers.formula

import ua.sviatkuzbyt.yourmath.app.presenter.other.basic.ErrorData
import ua.sviatkuzbyt.yourmath.domain.structures.formula.FormulaContent
import ua.sviatkuzbyt.yourmath.domain.structures.formula.FormulaInfo

data class FormulaState(
    val content: FormulaContent = FormulaContent(
        info = FormulaInfo(""),
        inputData = listOf(),
        resultData = listOf()
    ),
    val isLoading: Boolean = false,
    val errorMessage: ErrorData? = null,
)