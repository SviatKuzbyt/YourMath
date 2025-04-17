package ua.sviatkuzbyt.yourmath.app.presenter.controllers.editformula

sealed class EditFormulaIntent{
    data class SelectTab(val index: Int): EditFormulaIntent()
    data object AddDataItem: EditFormulaIntent()
    data object SaveChanges: EditFormulaIntent()
}