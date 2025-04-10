package ua.sviatkuzbyt.yourmath.app.presenter.screens.editor

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import ua.sviatkuzbyt.yourmath.app.presenter.controllers.editor.EditorIntent
import ua.sviatkuzbyt.yourmath.app.presenter.controllers.editor.EditorState

class EditorViewModel: ViewModel() {
    private val _screenState = MutableStateFlow(EditorState())
    val screenState: StateFlow<EditorState> = _screenState

    fun onIntent(intent: EditorIntent){
        when(intent){
            EditorIntent.AddFormula -> println("SKLT $intent")
            EditorIntent.DeleteAllFormulas -> println("SKLT $intent")
            EditorIntent.ExportFormulas -> println("SKLT $intent")
            EditorIntent.ImportFormulas -> println("SKLT $intent")
        }
    }
}