package ua.sviatkuzbyt.yourmath.app.presenter.controllers.formula

import ua.sviatkuzbyt.yourmath.domain.structures.main.FormulaItem

sealed class FormulaIntent {
    data class ChangeInputData(val position: Int, val newData: String): FormulaIntent()
}