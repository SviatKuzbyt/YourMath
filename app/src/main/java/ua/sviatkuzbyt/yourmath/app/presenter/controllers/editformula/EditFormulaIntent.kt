package ua.sviatkuzbyt.yourmath.app.presenter.controllers.editformula

sealed class EditFormulaIntent{
    data class SelectTab(val index: Int): EditFormulaIntent()
    data object AddDataItem: EditFormulaIntent()
    data class ChangeName(val name: String): EditFormulaIntent()
    data class ChangeDescription(val description: String): EditFormulaIntent()
    data class MoveItem(val from: Int, val to: Int, val list: EditList): EditFormulaIntent()
    data class ChangeItemLabel(val index: Int, val newText: String, val list: EditList): EditFormulaIntent()
    data class ChangeItemCodeLabel(val index: Int, val newText: String, val list: EditList): EditFormulaIntent()
    data class ChangeInputDefaultData(val index: Int, val newText: String): EditFormulaIntent()
    data class ChangeCodeText(val newText: String): EditFormulaIntent()
    data class DeleteItem(val index: Int, val list: EditList): EditFormulaIntent()
    data object SaveName: EditFormulaIntent()
    data object SaveDescription: EditFormulaIntent()
    data class SaveItemLabel(val index: Int, val list: EditList): EditFormulaIntent()
    data class SaveItemCodeLabel(val index: Int, val list: EditList): EditFormulaIntent()
    data class SaveInputDefaultData(val index: Int): EditFormulaIntent()
    data object SaveCodeText: EditFormulaIntent()
}

enum class EditList{
    Inputs, Results
}

