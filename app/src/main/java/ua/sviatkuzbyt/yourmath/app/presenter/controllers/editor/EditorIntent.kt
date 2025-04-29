package ua.sviatkuzbyt.yourmath.app.presenter.controllers.editor

sealed class EditorIntent{
    data object DeleteAllFormulas: EditorIntent()
    data class OpenDialog(val dialog: EditorDialogContent): EditorIntent()
    data class DeleteFormula(val formulaID: Long): EditorIntent()
    data object CloseDialog: EditorIntent()
    data class MoveItem(val from: Int, val to: Int): EditorIntent()
    data object LoadImportedFormulas: EditorIntent()
    data object ReloadFormulas: EditorIntent()
}