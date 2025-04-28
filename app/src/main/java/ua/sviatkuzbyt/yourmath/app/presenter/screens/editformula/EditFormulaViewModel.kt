package ua.sviatkuzbyt.yourmath.app.presenter.screens.editformula

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import ua.sviatkuzbyt.yourmath.app.presenter.controllers.editformula.EditFormulaIntent
import ua.sviatkuzbyt.yourmath.app.presenter.controllers.editformula.EditFormulaState
import ua.sviatkuzbyt.yourmath.app.presenter.controllers.editformula.EditFormulaStateContent
import ua.sviatkuzbyt.yourmath.app.presenter.controllers.editformula.EditList
import ua.sviatkuzbyt.yourmath.app.presenter.other.basic.safeBackgroundLaunch
import ua.sviatkuzbyt.yourmath.domain.usecases.editformula.GetEditFormulaDataUseCase
import javax.inject.Inject

@HiltViewModel
class EditFormulaViewModel @Inject constructor(
    sentData: SavedStateHandle,
    private val getEditFormulaDataUseCase: GetEditFormulaDataUseCase
): ViewModel() {

    private val formulaID: Long = sentData["formulaID"] ?: GetEditFormulaDataUseCase.NEW_FORMULA
    private val _screenState = MutableStateFlow(EditFormulaState())
    val screenState: StateFlow<EditFormulaState> = _screenState

    private val _info = MutableStateFlow(EditFormulaStateContent.Info("", null))
    private val _inputs = MutableStateFlow(EditFormulaStateContent.Inputs(listOf()))
    private val _results = MutableStateFlow(EditFormulaStateContent.Results(listOf()))
    private val _code = MutableStateFlow(EditFormulaStateContent.Code(""))

    init {
        loadData()
    }

    fun onIntent(intent: EditFormulaIntent){
        when(intent){
            is EditFormulaIntent.SelectTab ->
                changeTab(intent.index)
            is EditFormulaIntent.ChangeName ->
                changeName(intent.name)
            is EditFormulaIntent.ChangeDescription ->
                changeDescription(intent.description)
            EditFormulaIntent.AddDataItem ->
                println("SKLT $intent")
            is EditFormulaIntent.ChangeItemLabel ->
                changeItemLabel(intent.index, intent.newText, intent.list)
            is EditFormulaIntent.ChangeItemCodeLabel ->
                changeItemCodeLabel(intent.index, intent.newText, intent.list)
            is EditFormulaIntent.DeleteItem ->
                println("SKLT $intent")
            is EditFormulaIntent.MoveItem ->
                println("SKLT $intent")
            is EditFormulaIntent.ChangeInputDefaultData ->
                changeInputDefaultData(intent.index, intent.newText)
            is EditFormulaIntent.ChangeCodeText ->
                changeCodeText(intent.newText)

            is EditFormulaIntent.SaveCodeText ->
                println("SKLT $intent")
            is EditFormulaIntent.SaveDescription ->
                println("SKLT $intent")
            is EditFormulaIntent.SaveInputDefaultData ->
                println("SKLT $intent")
            is EditFormulaIntent.SaveItemCodeLabel ->
                println("SKLT $intent")
            is EditFormulaIntent.SaveItemLabel ->
                println("SKLT $intent")
            is EditFormulaIntent.SaveName ->
                println("SKLT $intent")
        }
    }

    private fun changeCodeText(newText: String){
        updateCode { it.copy(text = newText) }
    }

    private fun changeItemLabel(index: Int, newText: String, list: EditList){
        when(list){
            EditList.Inputs -> updateInputs { state ->
                state.copy(list = state.list.mapIndexed {i, input ->
                    if (i == index) input.copy(label = newText)
                    else input
                })
            }
            EditList.Results -> updateResults { state ->
                state.copy(list = state.list.mapIndexed {i, result ->
                    if (i == index) result.copy(label = newText)
                    else result
                })
            }
        }
    }

    private fun changeItemCodeLabel(index: Int, newText: String, list: EditList){
        when(list){
            EditList.Inputs -> updateInputs { state ->
                state.copy(list = state.list.mapIndexed { i, input ->
                    if (i == index) input.copy(codeLabel = newText)
                    else input
                })
            }
            EditList.Results -> updateResults { state ->
                state.copy(list = state.list.mapIndexed { i, result ->
                    if (i == index)  result.copy(codeLabel = newText)
                    else result
                })
            }
        }
    }

    private fun changeInputDefaultData(index: Int, newText: String){
        updateInputs { state ->
            state.copy(list = state.list.mapIndexed { i, input ->
                if (i == index)  input.copy(defaultData = newText)
                else input
            })
        }
    }

    private fun changeName(newText: String){
        updateInfo { it.copy(name = newText) }
    }

    private fun changeDescription(newText: String){
        updateInfo { it.copy(description = newText) }
    }

    private inline fun updateInfo(
        update: (EditFormulaStateContent.Info) -> EditFormulaStateContent.Info
    ){
        _info.value = update(_info.value)
        _screenState.update { state ->
            state.copy(content = _info.value)
        }
    }

    private inline fun updateInputs(
        update: (EditFormulaStateContent.Inputs) -> EditFormulaStateContent.Inputs
    ){
        _inputs.value = update(_inputs.value)
        _screenState.update { state ->
            state.copy(content = _inputs.value)
        }
    }

    private inline fun updateResults(
        update: (EditFormulaStateContent.Results) -> EditFormulaStateContent.Results
    ){
        _results.value = update(_results.value)
        _screenState.update { state ->
            state.copy(content = _results.value)
        }
    }

    private inline fun updateCode(
        update: (EditFormulaStateContent.Code) -> EditFormulaStateContent.Code
    ){
        _code.value = update(_code.value)
        _screenState.update { state ->
            state.copy(content = _code.value)
        }
    }

    private fun loadData() = safeBackgroundLaunch(
        code = {
            val data = getEditFormulaDataUseCase.execute(formulaID)
            _info.value = EditFormulaStateContent.Info(
                name = data.info.name,
                description = data.info.description
            )
            _inputs.value = EditFormulaStateContent.Inputs(data.inputList)
            _results.value = EditFormulaStateContent.Results(data.resultList)
            _code.value = EditFormulaStateContent.Code(data.info.code)

            _screenState.value = EditFormulaState(
                content = _info.value
            )
        },
        errorHandling = {}
    )

    private fun changeTab(index: Int){
        val content = when(index){
            0 -> _info.value
            1 -> _inputs.value
            2 -> _results.value
            3 -> _code.value
            else -> EditFormulaStateContent.Nothing
        }

        _screenState.update { state ->
            state.copy(
                selectedTab = index,
                content = content
            )
        }
    }
}