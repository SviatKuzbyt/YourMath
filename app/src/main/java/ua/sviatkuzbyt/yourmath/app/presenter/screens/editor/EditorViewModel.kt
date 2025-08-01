package ua.sviatkuzbyt.yourmath.app.presenter.screens.editor

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import ua.sviatkuzbyt.yourmath.app.presenter.controllers.editor.EditorDialogContent
import ua.sviatkuzbyt.yourmath.app.presenter.controllers.editor.EditorIntent
import ua.sviatkuzbyt.yourmath.app.presenter.controllers.editor.EditorListContent
import ua.sviatkuzbyt.yourmath.app.presenter.controllers.editor.EditorState
import ua.sviatkuzbyt.yourmath.app.presenter.other.basic.EmptyScreenInfo
import ua.sviatkuzbyt.yourmath.app.presenter.other.basic.ErrorData
import ua.sviatkuzbyt.yourmath.app.presenter.other.basic.safeBackgroundLaunch
import ua.sviatkuzbyt.yourmath.domain.structures.edit.FormulaNameItem
import ua.sviatkuzbyt.yourmath.domain.usecases.editor.DeleteAllFormulasUseCase
import ua.sviatkuzbyt.yourmath.domain.usecases.editor.DeleteFormulaUseCase
import ua.sviatkuzbyt.yourmath.domain.usecases.editor.MoveFormulaUseCase
import ua.sviatkuzbyt.yourmath.domain.usecases.editor.ObserveFormulasToEditUseCase
import javax.inject.Inject

@HiltViewModel
class EditorViewModel @Inject constructor(
    private val observeFormulasToEditUseCase: ObserveFormulasToEditUseCase,
    private val deleteFormulaUseCase: DeleteFormulaUseCase,
    private val deleteAllFormulasUseCase: DeleteAllFormulasUseCase,
    private val moveFormulaUseCase: MoveFormulaUseCase
) : ViewModel() {

    private val _screenState = MutableStateFlow(EditorState())
    val screenState: StateFlow<EditorState> = _screenState

    init { observeFormulas() }

    fun onIntent(intent: EditorIntent){
        when(intent){
            is EditorIntent.MoveItem -> moveItem(intent.from, intent.to)
            is EditorIntent.OpenDialog -> updateDialogContent(intent.dialog)
            EditorIntent.CloseDialog -> closeContentDialog()
            is EditorIntent.DeleteFormula -> deleteFormula(intent.formulaID)
            EditorIntent.DeleteAllFormulas -> deleteAllFormulas()
        }
    }

    private fun observeFormulas(){
        observeFormulasToEditUseCase.execute()
            .onEach { formulas ->
                val listContent = if (formulas.isEmpty()) {
                    EditorListContent.EmptyScreen(EmptyScreenInfo.noEditFormulas())
                } else {
                    EditorListContent.FormulaList(formulas)
                }
                _screenState.update { it.copy(listContent = listContent) }
            }
            .catch { e -> setError(e) }
            .launchIn(viewModelScope)
    }

    private fun moveItem(fromIndex: Int, toIndex: Int) = safeBackgroundLaunch(
        code = {
            val list = getCurrentFormulasList()
            if (toIndex in list.indices){
                val fromID = list[fromIndex].id
                val toID = list[toIndex].id
                moveFormulaUseCase.execute(fromID, fromIndex, toID, toIndex)
            }
        },
        errorHandling = ::setError
    )

    private fun updateDialogContent(content: EditorDialogContent) {
        _screenState.update { it.copy(dialogContent = content) }
    }

    private fun closeContentDialog(){
        _screenState.update { state ->
            state.copy(dialogContent = EditorDialogContent.Nothing)
        }
    }

    private fun deleteFormula(formulaID: Long) = safeBackgroundLaunch(
        code = {
            deleteFormulaUseCase.execute(formulaID)
            closeContentDialog()
        },
        errorHandling = ::setError
    )

    private fun deleteAllFormulas() = safeBackgroundLaunch(
        code = {
            deleteAllFormulasUseCase.execute()
            closeContentDialog()
        },
        errorHandling = ::setError
    )

    private fun getCurrentFormulasList(): List<FormulaNameItem> {
        return (_screenState.value.listContent as? EditorListContent.FormulaList)?.formulas.orEmpty()
    }

    private fun setError(exception: Throwable){
        _screenState.update { state ->
            state.copy(
                dialogContent = EditorDialogContent.ErrorDialog(
                    ErrorData(detailStr = exception.message)
                )
            )
        }
    }
}