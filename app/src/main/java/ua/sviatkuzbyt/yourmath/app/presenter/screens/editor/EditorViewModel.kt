package ua.sviatkuzbyt.yourmath.app.presenter.screens.editor

import android.util.Log
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
import ua.sviatkuzbyt.yourmath.domain.structures.editor.FormulaNameItem
import ua.sviatkuzbyt.yourmath.domain.usecases.editor.DeleteAllFormulasUseCase
import ua.sviatkuzbyt.yourmath.domain.usecases.editor.DeleteFormulaUseCase
import ua.sviatkuzbyt.yourmath.domain.usecases.editor.GetFormulasToEditUseCase
import ua.sviatkuzbyt.yourmath.domain.usecases.editor.GetNewFormulasUseCase
import ua.sviatkuzbyt.yourmath.domain.usecases.editor.MoveFormulaUseCase
import javax.inject.Inject

@HiltViewModel
class EditorViewModel @Inject constructor(
    private val getFormulasToEditUseCase: GetFormulasToEditUseCase,
    private val deleteFormulaUseCase: DeleteFormulaUseCase,
    private val deleteAllFormulasUseCase: DeleteAllFormulasUseCase,
    private val moveFormulaUseCase: MoveFormulaUseCase,
    private val getNewFormulasUseCase: GetNewFormulasUseCase
) : ViewModel() {

    private val _screenState = MutableStateFlow(EditorState())
    val screenState: StateFlow<EditorState> = _screenState

    init {
        loadFormulas()
    }

    fun onIntent(intent: EditorIntent){
        when(intent){
            EditorIntent.AddFormula -> println("SKLT $intent")
            is EditorIntent.MoveItem -> moveItem(intent.from, intent.to)
            EditorIntent.LoadImportedFormulas -> loadImportedFormulas()
            is EditorIntent.OpenDialog -> updateDialogContent(intent.dialog)
            EditorIntent.CloseDialog -> closeContentDialog()
            is EditorIntent.DeleteFormula -> deleteFormula(intent.formulaID)
            EditorIntent.DeleteAllFormulas -> deleteAllFormulas()
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

    private fun moveItem(fromIndex: Int, toIndex: Int) = safeBackgroundLaunch(
        code = {
            val list = getCurrentFormulasList()
            if (toIndex in list.indices){
                val fromID = list[fromIndex].id
                val toID = list[toIndex].id
                moveFormulaUseCase.execute(fromID, fromIndex, toID, toIndex)

                val updatedList = list.toMutableList().apply {
                    add(toIndex, removeAt(fromIndex))
                }

                _screenState.update {
                    it.copy(listContent = EditorListContent.FormulaList(updatedList))
                }
                sendListChangedEvent()
            }
        },
        errorHandling = ::setError
    )

    private fun loadImportedFormulas() = safeBackgroundLaunch(
        code = {
            val existingFormulas = getCurrentFormulasList()
            val newFormulas = getNewFormulasUseCase.execute(existingFormulas.size)
            val combinedList = existingFormulas + newFormulas

            _screenState.value = EditorState(
                listContent = EditorListContent.FormulaList(combinedList)
            )

            sendListChangedEvent()
        },
        errorHandling = ::setError
    )

    private fun sendListChangedEvent(){
        GlobalEvent.sendEvent(GlobalEventType.ChangeFormulaList)
    }

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

            val oldList = getCurrentFormulasList()
            val formulaToDelete = oldList.first { it.id == formulaID }
            val newList = oldList - formulaToDelete

            val listContent = if(newList.isEmpty()){
                EditorListContent.EmptyScreen(EmptyScreenInfo.noEditFormulas())
            } else{
                EditorListContent.FormulaList(newList)
            }

            _screenState.update { state ->
                state.copy(
                    listContent = listContent,
                    dialogContent = EditorDialogContent.Nothing
                )
            }
            sendListChangedEvent()
        },
        errorHandling = ::setError
    )

    private fun deleteAllFormulas() = safeBackgroundLaunch(
        code = {
            deleteAllFormulasUseCase.execute()
            _screenState.value = EditorState(
                listContent = EditorListContent.EmptyScreen(EmptyScreenInfo.noEditFormulas())
            )

            sendListChangedEvent()
        },
        errorHandling = ::setError
    )

    private fun getCurrentFormulasList(): List<FormulaNameItem> {
        return (_screenState.value.listContent as? EditorListContent.FormulaList)?.formulas.orEmpty()
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