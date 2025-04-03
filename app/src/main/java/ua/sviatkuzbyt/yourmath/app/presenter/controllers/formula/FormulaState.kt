package ua.sviatkuzbyt.yourmath.app.presenter.controllers.formula

import ua.sviatkuzbyt.yourmath.app.presenter.other.ErrorData
import ua.sviatkuzbyt.yourmath.domain.structures.formula.Formula
import ua.sviatkuzbyt.yourmath.domain.structures.formula.FormulaInfo

data class FormulaState(
    val content: Formula = Formula(
        info = FormulaInfo(""),
        inputData = listOf(),
        resultData = listOf()
    ),
    val errorMessage: ErrorData? = null,
)