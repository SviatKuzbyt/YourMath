package ua.sviatkuzbyt.yourmath.app.presenter.controllers.editor

sealed class EditorIntent{
    data object ImportFormulas: EditorIntent()
    data object ExportFormulas: EditorIntent()
    data object DeleteAllFormulas: EditorIntent()
    data object AddFormula: EditorIntent()
    data class DeleteFormula(val formulaID: Long): EditorIntent()
}