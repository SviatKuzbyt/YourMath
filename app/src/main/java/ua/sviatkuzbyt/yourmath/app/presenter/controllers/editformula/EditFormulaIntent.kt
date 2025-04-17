package ua.sviatkuzbyt.yourmath.app.presenter.controllers.editformula

sealed class EditFormulaIntent{
    data class SelectTab(val index: Int): EditFormulaIntent()
    data object AddDataItem: EditFormulaIntent()
    data object SaveChanges: EditFormulaIntent()
    data class ChangeName(val name: String): EditFormulaIntent()
    data class ChangeDescription(val description: String): EditFormulaIntent()
    data class MoveInputItem(val from: Int, val to: Int): EditFormulaIntent()
    data class ChangeInputLabel(val id: Long, val newText: String): EditFormulaIntent()
    data class ChangeInputCodeLabel(val id: Long, val newText: String): EditFormulaIntent()
    data class ChangeInputDefaultData(val id: Long, val newText: String): EditFormulaIntent()
    data class DeleteInputItem(val id: Long): EditFormulaIntent()
}