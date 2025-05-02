package ua.sviatkuzbyt.yourmath.app.presenter.screens.editformula

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import ua.sviatkuzbyt.yourmath.app.presenter.controllers.editformula.EditFormulaDialogContent
import ua.sviatkuzbyt.yourmath.app.presenter.controllers.editformula.EditFormulaIntent
import ua.sviatkuzbyt.yourmath.app.presenter.controllers.editformula.EditFormulaState
import ua.sviatkuzbyt.yourmath.app.presenter.controllers.editformula.EditFormulaStateContent
import ua.sviatkuzbyt.yourmath.app.presenter.controllers.editformula.FormulaListText
import ua.sviatkuzbyt.yourmath.app.presenter.controllers.editformula.FormulaText
import ua.sviatkuzbyt.yourmath.app.presenter.other.basic.ErrorData
import ua.sviatkuzbyt.yourmath.app.presenter.other.basic.GlobalEvent
import ua.sviatkuzbyt.yourmath.app.presenter.other.basic.GlobalEventType
import ua.sviatkuzbyt.yourmath.app.presenter.other.basic.safeBackgroundLaunch
import ua.sviatkuzbyt.yourmath.domain.usecases.editformula.CreateFormulaUseCase
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
    private val updateResultDataUseCase: UpdateResultDataUseCase,
    private val createFormulaUseCase: CreateFormulaUseCase
): ViewModel() {

    private var formulaID: Long = sentData["formulaID"] ?: CreateFormulaUseCase.NEW_FORMULA

    private val _info = MutableStateFlow(EditFormulaStateContent.Info(
        "", null, true)
    )
    private val _inputs = MutableStateFlow(EditFormulaStateContent.Inputs(listOf()))
    private val _results = MutableStateFlow(EditFormulaStateContent.Results(listOf()))
    private val _code = MutableStateFlow(EditFormulaStateContent.Code(""))

    private val _screenState = MutableStateFlow(EditFormulaState())
    val screenState: StateFlow<EditFormulaState> = _screenState

    init { loadData() }

    private fun loadData() = safeBackgroundLaunch(
        code = {
            val data = if (formulaID == CreateFormulaUseCase.NEW_FORMULA){
                createFormulaUseCase.execute().also {
                    formulaID = it.info.id
                    setGlobalEvent()
                }
            } else {
                getEditFormulaDataUseCase.execute(formulaID)
            }

            _info.value = EditFormulaStateContent.Info(
                name = data.info.name,
                description = data.info.description,
                isNote = data.info.isNote
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

    fun onIntent(intent: EditFormulaIntent){
        when(intent){
            is EditFormulaIntent.SelectTab ->
                changeTab(intent.index)
            is EditFormulaIntent.ChangeFormulaText ->
                changeFormulaText(intent.type, intent.text)
            is EditFormulaIntent.ChangeListText ->
                changeListText(intent.type, intent.index, intent.text)
            is EditFormulaIntent.SaveFormulaText ->
                saveFormulaText(intent.type)
            is EditFormulaIntent.SaveListText ->
                saveListText(intent.type, intent.index)
            is EditFormulaIntent.ChangeIsNote ->
                changeIsNote(intent.isNote)
            EditFormulaIntent.AddDataItem ->
                addItem()
            is EditFormulaIntent.MoveItem ->
                moveItem(intent.from, intent.to)
            is EditFormulaIntent.DeleteItem ->
                deleteItem(intent.index)
            is EditFormulaIntent.OpenDialog ->
                openDialog(intent.dialog)
            EditFormulaIntent.CloseDialog ->
                closeDialog()
            EditFormulaIntent.Exit ->
                setNavigateBack()
        }
    }

    // SELECT TAB

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

    //CHANGE TEXTS

    private fun changeFormulaText(type: FormulaText, text: String){
        when(type){
            FormulaText.Name -> changeFormulaName(text)
            FormulaText.Description -> changeFormulaDescription(text)
            FormulaText.Code -> changeFormulaCode(text)
        }
    }

    private fun changeFormulaName(text: String){
        updateInfo { it.copy(name = text) }
        setGlobalEvent()
    }

    private fun changeFormulaDescription(text: String){
        updateInfo { it.copy(description = text) }
    }

    private fun changeFormulaCode(text: String){
        updateCode { it.copy(text = text) }
    }

    //CHANGE TEXTS IN LIST

    private fun changeListText(type: FormulaListText, index: Int, text: String){
        when(type){
            FormulaListText.InputLabel -> changeInputLabel(index, text)
            FormulaListText.InputCodeLabel -> changeInputCodeLabel(index, text)
            FormulaListText.InputDefaultData -> changeInputDefaultData(index, text)
            FormulaListText.ResultLabel -> changeResultLabel(index, text)
            FormulaListText.ResultCodeLabel -> changeResultCodeLabel(index, text)
        }
    }

    private fun changeInputLabel(changeIndex: Int, text: String) {
        updateInputs { inputs ->
            inputs.copy(list = inputs.list.mapIndexed { index, input ->
                if (index == changeIndex) input.copy(label = text)
                else input
            })
        }
    }

    private fun changeInputCodeLabel(changeIndex: Int, text: String) {
        updateInputs { inputs ->
            inputs.copy(list = inputs.list.mapIndexed { index, input ->
                if (index == changeIndex) input.copy(codeLabel = text)
                else input
            })
        }
    }

    private fun changeInputDefaultData(changeIndex: Int, text: String) {
        updateInputs { inputs ->
            inputs.copy(list = inputs.list.mapIndexed { index, input ->
                if (index == changeIndex) input.copy(defaultData = text)
                else input
            })
        }
    }

    private fun changeResultLabel(changeIndex: Int, text: String) {
        updateResults { results ->
            results.copy(list = results.list.mapIndexed { index, result ->
                if (index == changeIndex) result.copy(label = text)
                else result
            })
        }
    }

    private fun changeResultCodeLabel(changeIndex: Int, text: String) {
        updateResults { results ->
            results.copy(list = results.list.mapIndexed { index, result ->
                if (index == changeIndex) result.copy(codeLabel = text)
                else result
            })
        }
    }

    //SAVE CHANGED TEXT IN DB

    private fun saveFormulaText(type: FormulaText) = safeBackgroundLaunch(
        code = {
            when(type){
                FormulaText.Name -> saveFormulaName()
                FormulaText.Description -> saveFormulaDescription()
                FormulaText.Code -> saveFormulaCode()
            }
        },
        errorHandling = ::setError
    )

    private fun saveFormulaName(){
        updateFormulaDataUseCase.updateLabel(_info.value.name, formulaID)
    }

    private fun saveFormulaDescription(){
        updateFormulaDataUseCase.updateDescription(_info.value.description.orEmpty(), formulaID)
    }

    private fun saveFormulaCode(){
        updateFormulaDataUseCase.updateCode(_code.value.text, formulaID)
    }

    //SAVE CHANGED LIST TEXT IN DB

    private fun saveListText(type: FormulaListText, index: Int) = safeBackgroundLaunch(
        code = {
            when(type){
                FormulaListText.InputLabel -> saveInputLabel(index)
                FormulaListText.InputCodeLabel -> saveInputCodeLabel(index)
                FormulaListText.InputDefaultData -> saveInputDefaultData(index)
                FormulaListText.ResultLabel -> saveResultLabel(index)
                FormulaListText.ResultCodeLabel -> saveResultCodeLabel(index)
            }
        },
        errorHandling = ::setError
    )

    private fun saveInputLabel(index: Int){
        if (index in _inputs.value.list.indices){
            updateInputDataUseCase.updateTextLabel(
                _inputs.value.list[index].label, _inputs.value.list[index].id
            )
        }
    }

    private fun saveInputCodeLabel(index: Int){
        if (index in _inputs.value.list.indices) {
            updateInputDataUseCase.updateCodeLabel(
                _inputs.value.list[index].codeLabel, _inputs.value.list[index].id
            )
        }
    }

    private fun saveInputDefaultData(index: Int){
        if (index in _inputs.value.list.indices) {
            updateInputDataUseCase.updateDefaultData(
                _inputs.value.list[index].defaultData.orEmpty(), _inputs.value.list[index].id
            )
        }
    }

    private fun saveResultLabel(index: Int){
        if (index in _results.value.list.indices) {
            updateResultDataUseCase.updateTextLabel(
                _results.value.list[index].label, _results.value.list[index].id
            )
        }
    }

    private fun saveResultCodeLabel(index: Int){
        if (index in _results.value.list.indices) {
            updateResultDataUseCase.updateCodeLabel(
                _results.value.list[index].codeLabel, _results.value.list[index].id
            )
        }
    }

    //CHANGE IS NOTE

    private fun changeIsNote(isNote: Boolean) = safeBackgroundLaunch(
        code = {
            updateInfo { it.copy(isNote = isNote) }
            updateFormulaDataUseCase.changeIsNote(isNote, formulaID)
            setGlobalEvent()
        },
        errorHandling = ::setError
    )

    //ADD LIST ITEM

    private fun addItem() = safeBackgroundLaunch(
        code = {
            if (screenState.value.content is EditFormulaStateContent.Inputs){
                addInputItem()
            } else if(screenState.value.content is EditFormulaStateContent.Results){
                addResultItem()
            }
        },
        errorHandling = ::setError
    )

    private fun addInputItem(){
        val input = updateInputDataUseCase.add(formulaID)
        updateInputs { it.copy(list = it.list + input) }
    }

    private fun addResultItem(){
        val result = updateResultDataUseCase.add(formulaID)
        updateResults { it.copy(list = it.list + result) }
    }

    //MOVE ITEMS

    private fun moveItem(fromIndex: Int, toIndex: Int) = safeBackgroundLaunch(
        code = {
            if (screenState.value.content is EditFormulaStateContent.Inputs){
                moveInput(fromIndex, toIndex)
            } else if(screenState.value.content is EditFormulaStateContent.Results){
                moveResult(fromIndex, toIndex)
            }
        },
        errorHandling = ::setError
    )

    private fun moveInput(fromIndex: Int, toIndex: Int){
        val list = _inputs.value.list
        if (toIndex in list.indices){
            val fromID = list[fromIndex].id
            val toID = list[toIndex].id
            updateInputDataUseCase.moveItem(fromID, fromIndex, toID, toIndex)

            updateInputs {
                it.copy(list = it.list.toMutableList().apply {
                    add(toIndex, removeAt(fromIndex))
                })
            }
        }
    }

    private fun moveResult(fromIndex: Int, toIndex: Int){
        val results = _results.value.list
        if (toIndex in results.indices){
            val fromID = results[fromIndex].id
            val toID = results[toIndex].id
            updateResultDataUseCase.moveItem(fromID, fromIndex, toID, toIndex)

            updateResults {
                it.copy(list = it.list.toMutableList().apply {
                    add(toIndex, removeAt(fromIndex))
                })
            }
        }
    }

    //DELETE ITEM

    private fun deleteItem(index: Int) = safeBackgroundLaunch(
        code = {
            closeDialog()

            if (screenState.value.content is EditFormulaStateContent.Inputs){
                deleteInput(index)
            } else if(screenState.value.content is EditFormulaStateContent.Results){
                deleteResult(index)
            }
        },
        errorHandling = ::setError
    )

    private fun deleteInput(index: Int){
        val inputData = _inputs.value.list[index]
        updateInputs { it.copy(list = it.list - inputData) }
        updateInputDataUseCase.deleteItem(formulaID, inputData.id, index)
    }

    private fun deleteResult(index: Int){
        val resultData = _results.value.list[index]
        updateResults { it.copy(list = it.list - resultData) }
        updateResultDataUseCase.deleteItem(formulaID, resultData.id, index)
    }

    //SET DIALOG

    private fun openDialog(dialog: EditFormulaDialogContent){
        _screenState.update { state ->
            state.copy(dialog = dialog)
        }
    }

    private fun closeDialog(){
        _screenState.update { state ->
            state.copy(dialog = EditFormulaDialogContent.Nothing)
        }
    }

    private fun setNavigateBack(){
        _screenState.update { it.copy(isNavigateBack = true) }
    }

    // UPDATE STATES

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

    // OTHERS

    private fun setGlobalEvent(){
        GlobalEvent.sendEvent(GlobalEventType.ChangeEditorFormulaList)
    }

    private fun setError(exception: Exception){
        _screenState.update { state ->
            state.copy(
                dialog = EditFormulaDialogContent.ErrorDialogContent(
                    ErrorData(detailStr = exception.message)
                )
            )
        }
    }
}