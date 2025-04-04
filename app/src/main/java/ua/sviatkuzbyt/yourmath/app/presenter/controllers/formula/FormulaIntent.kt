package ua.sviatkuzbyt.yourmath.app.presenter.controllers.formula

sealed class FormulaIntent {
    data class ChangeInputData(val position: Int, val newData: String): FormulaIntent()
    data object MathFormula: FormulaIntent()
    data object CloseDialog: FormulaIntent()
}