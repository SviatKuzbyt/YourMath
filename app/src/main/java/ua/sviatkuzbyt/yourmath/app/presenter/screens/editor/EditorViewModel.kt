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
import ua.sviatkuzbyt.yourmath.app.presenter.other.basic.GlobalEvent
import ua.sviatkuzbyt.yourmath.app.presenter.other.basic.GlobalEventType
import ua.sviatkuzbyt.yourmath.app.presenter.other.basic.safeBackgroundLaunch
import ua.sviatkuzbyt.yourmath.domain.usecases.editor.DeleteAllFormulasUseCase
import ua.sviatkuzbyt.yourmath.domain.usecases.editor.DeleteFormulaUseCase
import ua.sviatkuzbyt.yourmath.domain.usecases.editor.GetFormulasToEditUseCase
import javax.inject.Inject

@HiltViewModel
class EditorViewModel @Inject constructor(
    private val getFormulasToEditUseCase: GetFormulasToEditUseCase,
    private val deleteFormulaUseCase: DeleteFormulaUseCase,
    private val deleteAllFormulasUseCase: DeleteAllFormulasUseCase
) : ViewModel() {

    private val _screenState = MutableStateFlow(EditorState())
    val screenState: StateFlow<EditorState> = _screenState

    init {
        loadFormulas()
    }

    fun onIntent(intent: EditorIntent){
        when(intent){
            EditorIntent.AddFormula -> println("SKLT $intent")
            EditorIntent.DeleteAllFormulas -> deleteAllFormulas()
            EditorIntent.ExportFormulas -> println("SKLT $intent")
            EditorIntent.ImportFormulas -> println("SKLT $intent")
            is EditorIntent.OpenDialog -> updateDialogContent(intent.dialog)
            EditorIntent.CloseDialog -> closeContentDialog()
            is EditorIntent.DeleteFormula -> deleteFormula(intent.formulaID)
            is EditorIntent.MoveItem -> moveItem(intent.from, intent.to)
        }
    }

    private fun moveItem(from: Int, to: Int){
        val list = (_screenState.value.listContent as EditorListContent.FormulaList).formulas
        if (to >= 0 && to < list.size){
            _screenState.update {
                it.copy(listContent = EditorListContent.FormulaList(
                    list.toMutableList().apply { add(to, removeAt(from)) }
                ))
            }
        }
    }

    private fun deleteAllFormulas() = safeBackgroundLaunch(
        code = {
            deleteAllFormulasUseCase.execute()
            _screenState.value = EditorState(
                listContent = EditorListContent.EmptyScreen(EmptyScreenInfo.noEditFormulas()),
                dialogContent = EditorDialogContent.Nothing
            )

            GlobalEvent.sendEvent(GlobalEventType.ChangeFormulaList)
        },
        errorHandling = ::setError
    )

    private fun deleteFormula(formulaID: Long) = safeBackgroundLaunch(
        code = {
            deleteFormulaUseCase.execute(formulaID)
            _screenState.update { state ->
                val oldList = (state.listContent as EditorListContent.FormulaList).formulas
                val formulaToDelete = oldList.first { it.id == formulaID }
                val newList = oldList - formulaToDelete

                val listContent = if(newList.isEmpty()){
                    EditorListContent.EmptyScreen(EmptyScreenInfo.noEditFormulas())
                } else{
                    EditorListContent.FormulaList(newList)
                }
                state.copy(
                    listContent = listContent,
                    dialogContent = EditorDialogContent.Nothing
                )
            }
            GlobalEvent.sendEvent(GlobalEventType.ChangeFormulaList)
        },
        errorHandling = ::setError
    )

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

    private fun updateDialogContent(content: EditorDialogContent) {
        _screenState.update { it.copy(dialogContent = content) }
    }
}