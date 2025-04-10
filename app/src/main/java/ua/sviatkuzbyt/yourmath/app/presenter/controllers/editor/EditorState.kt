package ua.sviatkuzbyt.yourmath.app.presenter.controllers.editor

import ua.sviatkuzbyt.yourmath.app.presenter.other.basic.EmptyScreenInfo
import ua.sviatkuzbyt.yourmath.domain.structures.main.FormulaItem

data class EditorState(
    val listContent: EditorListContent = EditorListContent.Nothing,
    val dialogContent: EditorDialogContent = EditorDialogContent.Nothing
)

sealed class EditorListContent{
    data class FormulaList(val formulas: List<FormulaItem>): EditorListContent()
    data class EmptyScreen(val info: EmptyScreenInfo): EditorListContent()
    data object Nothing: EditorListContent()
}

sealed class EditorDialogContent {
    data object Nothing: EditorDialogContent()
}