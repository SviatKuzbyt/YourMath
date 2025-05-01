package ua.sviatkuzbyt.yourmath.app.presenter.controllers.editformula

import ua.sviatkuzbyt.yourmath.app.R
import ua.sviatkuzbyt.yourmath.app.presenter.other.basic.ErrorData
import ua.sviatkuzbyt.yourmath.domain.structures.editformula.EditInput
import ua.sviatkuzbyt.yourmath.domain.structures.editformula.EditResult

data class EditFormulaState(
    val tabs: List<Int> = listOf(
        R.string.info,
        R.string.input_data,
        R.string.results,
        R.string.code
    ),
    val selectedTab: Int = 0,
    val content: EditFormulaStateContent = EditFormulaStateContent.Nothing,
    val isAddButton: Boolean = false,
    val dialog: EditFormulaDialog = EditFormulaDialog.Nothing,
    val isNavigateBack: Boolean = false
)

sealed class EditFormulaStateContent {
    data class Info(
        val name: String,
        val description: String?,
        val isNote: Boolean
    ): EditFormulaStateContent()
    data class Inputs(val list: List<EditInput>): EditFormulaStateContent()
    data class Results(val list: List<EditResult>): EditFormulaStateContent()
    data class Code(val text: String): EditFormulaStateContent()
    data object Nothing: EditFormulaStateContent()
}

sealed class EditFormulaDialog {
    data class ErrorDialog(val data: ErrorData): EditFormulaDialog()
    data class DeleteFormula(val index: Int, val list: EditList): EditFormulaDialog()
    data object NoAllData: EditFormulaDialog()
    data object Nothing: EditFormulaDialog()
}