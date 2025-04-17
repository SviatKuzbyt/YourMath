package ua.sviatkuzbyt.yourmath.app.presenter.controllers.editformula

import ua.sviatkuzbyt.yourmath.app.R
import ua.sviatkuzbyt.yourmath.app.presenter.other.basic.ErrorData

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
    val dialog: EditFormulaDialog = EditFormulaDialog.Nothing
)

sealed class EditFormulaStateContent {
    data object InfoList: EditFormulaStateContent()
    data object InputList: EditFormulaStateContent()
    data object ResultList: EditFormulaStateContent()
    data object CodeField: EditFormulaStateContent()
    data object Nothing: EditFormulaStateContent()
}

sealed class EditFormulaDialog {
    data class ErrorDialog(val data: ErrorData): EditFormulaDialog()
    data object DoNotSave: EditFormulaDialog()
    data object DoNotAllData: EditFormulaDialog()
    data object Nothing: EditFormulaDialog()
}