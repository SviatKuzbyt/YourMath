package ua.sviatkuzbyt.yourmath.app.presenter.screens.editformula

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import ua.sviatkuzbyt.yourmath.app.presenter.controllers.editformula.EditFormulaIntent
import ua.sviatkuzbyt.yourmath.app.presenter.controllers.editformula.EditFormulaState
import ua.sviatkuzbyt.yourmath.app.presenter.controllers.editformula.EditFormulaStateContent
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
            is EditFormulaIntent.SelectTab -> changeTab(intent.index)
            EditFormulaIntent.AddDataItem -> println("SKLT $intent")
            EditFormulaIntent.SaveChanges -> println("SKLT $intent")
            is EditFormulaIntent.ChangeDescription -> println("SKLT $intent")
            is EditFormulaIntent.ChangeName -> println("SKLT $intent")
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