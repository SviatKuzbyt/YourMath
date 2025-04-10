package ua.sviatkuzbyt.yourmath.app.presenter.screens.editor

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import ua.sviatkuzbyt.yourmath.app.presenter.controllers.editor.EditorDialogContent
import ua.sviatkuzbyt.yourmath.app.presenter.controllers.editor.EditorIntent
import ua.sviatkuzbyt.yourmath.app.presenter.controllers.editor.EditorListContent
import ua.sviatkuzbyt.yourmath.app.presenter.controllers.editor.EditorState
import ua.sviatkuzbyt.yourmath.app.presenter.other.basic.EmptyScreenInfo
import ua.sviatkuzbyt.yourmath.app.presenter.other.basic.ErrorData
import ua.sviatkuzbyt.yourmath.app.presenter.other.basic.safeBackgroundLaunch
import ua.sviatkuzbyt.yourmath.domain.usecases.editor.GetFormulasToEditUseCase
import javax.inject.Inject

@HiltViewModel
class EditorViewModel @Inject constructor(
    private val getFormulasToEditUseCase: GetFormulasToEditUseCase
) : ViewModel() {

    private val _screenState = MutableStateFlow(EditorState())
    val screenState: StateFlow<EditorState> = _screenState

    init {
        loadFormulas()
    }

    fun onIntent(intent: EditorIntent){
        when(intent){
            EditorIntent.AddFormula -> println("SKLT $intent")
            EditorIntent.DeleteAllFormulas -> println("SKLT $intent")
            EditorIntent.ExportFormulas -> println("SKLT $intent")
            EditorIntent.ImportFormulas -> println("SKLT $intent")
            is EditorIntent.DeleteFormula -> println("SKLT $intent")
            EditorIntent.CloseDialog -> closeContentDialog()
        }
    }

    private fun loadFormulas() = safeBackgroundLaunch(
        code = {
            val formulaList = getFormulasToEditUseCase.execute()
            val listContent = if (formulaList.isEmpty()) {
                EditorListContent.EmptyScreen(EmptyScreenInfo.noEditFormulas())
            } else {
                EditorListContent.FormulaList(formulaList)
            }

            _screenState.value = EditorState(listContent = listContent)
        },
        errorHandling = ::setError
    )

    private fun closeContentDialog(){
        _screenState.update { state ->
            state.copy(dialogContent = EditorDialogContent.Nothing)
        }
    }

    private fun setError(exception: Exception){
        _screenState.update { state ->
            state.copy(
                dialogContent = EditorDialogContent.ErrorDialog(
                    ErrorData(detailStr = exception.message)
                )
            )
        }
    }
}