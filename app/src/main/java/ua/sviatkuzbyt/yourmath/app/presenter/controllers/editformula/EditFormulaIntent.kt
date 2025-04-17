package ua.sviatkuzbyt.yourmath.app.presenter.controllers.editformula

sealed class EditFormulaIntent{
    data class SelectTab(val index: Int): EditFormulaIntent()
    data object AddDataItem: EditFormulaIntent()
    data object SaveChanges: EditFormulaIntent()
    data class ChangeName(val name: String): EditFormulaIntent()
    data class ChangeDescription(val description: String): EditFormulaIntent()
    data class MoveItem(val from: Int, val to: Int, val list: EditList): EditFormulaIntent()
    data class ChangeItemLabel(val id: Long, val newText: String, val list: EditList): EditFormulaIntent()
    data class ChangeItemCodeLabel(val id: Long, val newText: String, val list: EditList): EditFormulaIntent()
    data class ChangeItemDefaultData(val id: Long, val newText: String, val list: EditList): EditFormulaIntent()
    data class DeleteItem(val id: Long, val list: EditList): EditFormulaIntent()
}

enum class EditList{
    Inputs, Results
}

