package ua.sviatkuzbyt.yourmath.app.presenter.controllers.editor

import ua.sviatkuzbyt.yourmath.app.presenter.other.basic.EmptyScreenInfo
import ua.sviatkuzbyt.yourmath.app.presenter.other.basic.ErrorData
import ua.sviatkuzbyt.yourmath.domain.structures.edit.FormulaNameItem

data class EditorState(
    val listContent: EditorListContent = EditorListContent.Nothing,
    val dialogContent: EditorDialogContent = EditorDialogContent.Nothing
)

sealed class EditorListContent{
    data class FormulaList(val formulas: List<FormulaNameItem>): EditorListContent()
    data class EmptyScreen(val info: EmptyScreenInfo): EditorListContent()
    data object Nothing: EditorListContent()
}

sealed class EditorDialogContent {
    data object Nothing: EditorDialogContent()
    data class ErrorDialog(val data: ErrorData): EditorDialogContent()
    data object DeleteAll: EditorDialogContent()
    data class DeleteFormula(val formulaID: Long, val formulaName: String): EditorDialogContent()
}