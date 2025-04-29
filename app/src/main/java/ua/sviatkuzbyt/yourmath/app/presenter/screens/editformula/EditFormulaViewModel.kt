package ua.sviatkuzbyt.yourmath.app.presenter.screens.editformula

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import ua.sviatkuzbyt.yourmath.app.presenter.controllers.editformula.EditFormulaDialog
import ua.sviatkuzbyt.yourmath.app.presenter.controllers.editformula.EditFormulaIntent
import ua.sviatkuzbyt.yourmath.app.presenter.controllers.editformula.EditFormulaState
import ua.sviatkuzbyt.yourmath.app.presenter.controllers.editformula.EditFormulaStateContent
import ua.sviatkuzbyt.yourmath.app.presenter.controllers.editformula.EditList
import ua.sviatkuzbyt.yourmath.app.presenter.controllers.editor.EditorListContent
import ua.sviatkuzbyt.yourmath.app.presenter.other.basic.ErrorData
import ua.sviatkuzbyt.yourmath.app.presenter.other.basic.safeBackgroundLaunch
import ua.sviatkuzbyt.yourmath.domain.usecases.editformula.GetEditFormulaDataUseCase
import ua.sviatkuzbyt.yourmath.domain.usecases.editformula.UpdateFormulaDataUseCase
import ua.sviatkuzbyt.yourmath.domain.usecases.editformula.UpdateInputDataUseCase
import ua.sviatkuzbyt.yourmath.domain.usecases.editformula.UpdateResultDataUseCase
import javax.inject.Inject

@HiltViewModel
class EditFormulaViewModel @Inject constructor(
    sentData: SavedStateHandle,
    private val getEditFormulaDataUseCase: GetEditFormulaDataUseCase,
    private val updateFormulaDataUseCase: UpdateFormulaDataUseCase,
    private val updateInputDataUseCase: UpdateInputDataUseCase,
    private val updateResultDataUseCase: UpdateResultDataUseCase
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
                addItem()
            is EditFormulaIntent.ChangeItemLabel ->
                changeItemLabel(intent.index, intent.newText, intent.list)
            is EditFormulaIntent.ChangeItemCodeLabel ->
                changeItemCodeLabel(intent.index, intent.newText, intent.list)
            is EditFormulaIntent.DeleteItem ->
                deleteItem(intent.index, intent.list)
            is EditFormulaIntent.MoveItem ->
                moveItem(intent.from, intent.to, intent.list)
            is EditFormulaIntent.ChangeInputDefaultData ->
                changeInputDefaultData(intent.index, intent.newText)
            is EditFormulaIntent.ChangeCodeText ->
                changeCodeText(intent.newText)

            is EditFormulaIntent.SaveCodeText ->
                updateFormulaCode()
            is EditFormulaIntent.SaveDescription ->
                updateFormulaDescription()
            is EditFormulaIntent.SaveInputDefaultData ->
                updateInputDefaultData(intent.index)
            is EditFormulaIntent.SaveItemCodeLabel ->
                updateItemCodeLabel(intent.index, intent.list)
            is EditFormulaIntent.SaveItemLabel ->
                updateItemTextLabel(intent.index, intent.list)
            is EditFormulaIntent.SaveName ->
                updateFormulaLabel()

            EditFormulaIntent.Exit -> checkCompleteEdit()
            is EditFormulaIntent.OpenDialog -> openDialog(intent.dialog)
            EditFormulaIntent.CloseDialog -> closeDialog()
        }
    }

    private fun addItem() = safeBackgroundLaunch(
        code = {
            if (screenState.value.content is EditFormulaStateContent.Inputs){
                val input = updateInputDataUseCase.add(formulaID)
                updateInputs { it.copy(list = it.list + input) }
            } else if(screenState.value.content is EditFormulaStateContent.Results){
                val result = updateResultDataUseCase.add(formulaID)
                updateResults { it.copy(list = it.list + result) }
            }
        },
        errorHandling = ::setError
    )

    private fun moveItem(fromIndex: Int, toIndex: Int, list: EditList) = safeBackgroundLaunch(
        code = {
            when(list){
                EditList.Inputs -> {
                    val inputsList = _inputs.value.list
                    if (toIndex in inputsList.indices){
                        val fromID = inputsList[fromIndex].id
                        val toID = inputsList[toIndex].id
                        updateInputDataUseCase.moveItem(fromID, fromIndex, toID, toIndex)

                        updateInputs {
                            it.copy(list = it.list.toMutableList().apply {
                                add(toIndex, removeAt(fromIndex))
                            })
                        }
                    }
                }
                EditList.Results -> {
                    val inputsList = _results.value.list
                    if (toIndex in inputsList.indices){
                        val fromID = inputsList[fromIndex].id
                        val toID = inputsList[toIndex].id
                        updateResultDataUseCase.moveItem(fromID, fromIndex, toID, toIndex)

                        updateResults {
                            it.copy(list = it.list.toMutableList().apply {
                                add(toIndex, removeAt(fromIndex))
                            })
                        }
                    }
                }
            }
        },
        errorHandling = ::setError
    )

    private fun openDialog(dialog: EditFormulaDialog){
        _screenState.update { state ->
            state.copy(dialog = dialog)
        }
    }

    private fun closeDialog(){
        _screenState.update { state ->
            state.copy(dialog = EditFormulaDialog.Nothing)
        }
    }

    private fun deleteItem(index: Int, list: EditList) = safeBackgroundLaunch(
        code = {
            closeDialog()

            when(list){
                EditList.Inputs -> {
                    val inputData = _inputs.value.list[index]
                    updateInputs { it.copy(list = it.list - inputData) }
                    updateInputDataUseCase.deleteItem(formulaID, inputData.id, index)
                }
                EditList.Results -> {
                    val resultData = _results.value.list[index]
                    updateResults { it.copy(list = it.list - resultData) }
                    updateResultDataUseCase.deleteItem(formulaID, resultData.id, index)
                }
            }
        },
        errorHandling = ::setError
    )

    private fun checkCompleteEdit(){
        _screenState.update { it.copy(isNavigateBack = true) }
    }

    private fun updateFormulaLabel() = safeBackgroundLaunch(
        code = {
            updateFormulaDataUseCase.updateLabel(_info.value.name, formulaID)
        },
        errorHandling = ::setError
    )

    private fun updateFormulaDescription() = safeBackgroundLaunch(
        code = {
            updateFormulaDataUseCase.updateDescription(_info.value.description.orEmpty(), formulaID)
        },
        errorHandling = ::setError
    )

    private fun updateItemTextLabel(index: Int, list: EditList) = safeBackgroundLaunch(
        code = {
            when(list){
                EditList.Inputs -> updateInputDataUseCase.updateTextLabel(
                    _inputs.value.list[index].label, _inputs.value.list[index].id
                )
                EditList.Results -> updateResultDataUseCase.updateTextLabel(
                    _results.value.list[index].label, _results.value.list[index].id
                )
            }
        },
        errorHandling = ::setError
    )

    private fun updateItemCodeLabel(index: Int, list: EditList) = safeBackgroundLaunch(
        code = {
            when(list){
                EditList.Inputs -> updateInputDataUseCase.updateCodeLabel(
                    _inputs.value.list[index].codeLabel, _inputs.value.list[index].id
                )
                EditList.Results -> updateResultDataUseCase.updateCodeLabel(
                    _results.value.list[index].codeLabel, _results.value.list[index].id
                )
            }
        },
        errorHandling = ::setError
    )

    private fun updateInputDefaultData(index: Int) = safeBackgroundLaunch(
        code = {
            updateInputDataUseCase.updateDefaultData(
                _inputs.value.list[index].defaultData.orEmpty(), _inputs.value.list[index].id
            )
        },
        errorHandling = ::setError
    )

    private fun updateFormulaCode() = safeBackgroundLaunch(
        code = {
            updateFormulaDataUseCase.updateCode(_code.value.text, formulaID)
        },
        errorHandling = ::setError
    )

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
        errorHandling = ::setError
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

    private fun setError(exception: Exception){
        _screenState.update { state ->
            state.copy(
                dialog = EditFormulaDialog.ErrorDialog(
                    ErrorData(detailStr = exception.message)
                )
            )
        }
    }
}