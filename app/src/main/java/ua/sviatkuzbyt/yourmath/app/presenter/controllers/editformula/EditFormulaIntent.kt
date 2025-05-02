package ua.sviatkuzbyt.yourmath.app.presenter.controllers.editformula

sealed class EditFormulaIntent{
    data class SelectTab(val index: Int): EditFormulaIntent()
    data class ChangeFormulaText(
        val type: FormulaText,
        val text: String
    ): EditFormulaIntent()
    data class ChangeIsNote(val isNote: Boolean): EditFormulaIntent()
    data class ChangeListText(
        val type: FormulaListText,
        val index: Int,
        val text: String
    ): EditFormulaIntent()
    data class SaveFormulaText(val type: FormulaText): EditFormulaIntent()
    data class SaveListText(
        val type: FormulaListText,
        val index: Int
    ): EditFormulaIntent()
    data class MoveItem(val from: Int, val to: Int): EditFormulaIntent()
    data class DeleteItem(val index: Int): EditFormulaIntent()
    data object AddDataItem: EditFormulaIntent()
    data class OpenDialog(val dialog: EditFormulaDialogContent): EditFormulaIntent()
    data object CloseDialog: EditFormulaIntent()
    data object Exit: EditFormulaIntent()
}

enum class FormulaText {
    Name, Description, Code
}

enum class FormulaListText{
    InputLabel, InputCodeLabel, InputDefaultData, ResultLabel, ResultCodeLabel
}