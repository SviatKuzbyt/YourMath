package ua.sviatkuzbyt.yourmath.app.presenter.screens.formula

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import ua.sviatkuzbyt.yourmath.app.R
import ua.sviatkuzbyt.yourmath.app.presenter.controllers.formula.FormulaIntent
import ua.sviatkuzbyt.yourmath.app.presenter.controllers.formula.FormulaState
import ua.sviatkuzbyt.yourmath.app.presenter.other.CopyToClipboardFormulaManager
import ua.sviatkuzbyt.yourmath.app.presenter.other.ErrorData
import ua.sviatkuzbyt.yourmath.app.presenter.other.safeBackgroundLaunch
import ua.sviatkuzbyt.yourmath.data.MathException
import ua.sviatkuzbyt.yourmath.data.NoAllDataEnterException
import ua.sviatkuzbyt.yourmath.domain.usecases.formula.GetFormulaUseCase
import ua.sviatkuzbyt.yourmath.domain.usecases.formula.MathFormulaUseCase
import javax.inject.Inject

@HiltViewModel
class FormulaViewModel @Inject constructor (
    private val getFormulaUseCase: GetFormulaUseCase,
    private val mathFormulaUseCase: MathFormulaUseCase,
    private val copyManager: CopyToClipboardFormulaManager,
    sentData: SavedStateHandle
): ViewModel(){
    private val formulaID: Long = sentData["formulaID"] ?: 0
    private val _screenState = MutableStateFlow(FormulaState())
    val screenState: StateFlow<FormulaState> = _screenState

    init {
        loadFormulaData()
    }

    fun onIntent(intent: FormulaIntent){
        when(intent){
            is FormulaIntent.ChangeInputData -> changeInputData(intent.position, intent.newData)
            FormulaIntent.MathFormula -> mathFormula()
            FormulaIntent.CloseDialog -> clearError()
            FormulaIntent.CopyToClipboard -> copyData()
        }
    }

    private fun loadFormulaData(){
        safeBackgroundLaunch(
            code = {
                updateFormulaState{
                    FormulaState(content = getFormulaUseCase.execute(formulaID))
                }
            },
            errorHandling = { setError(it) }
        )
    }

    private fun changeInputData(position: Int, newText: String) {
        try {
            updateFormulaState { state ->
                state.copy(
                    content = state.content.copy(
                        inputData = state.content.inputData.mapIndexed { index, item ->
                            if (index == position) item.copy(data = newText) else item
                        }
                    )
                )
            }
        } catch (e: Exception){
            setError(e)
        }
    }

    private fun copyData() {
        try {
            copyManager.copy(screenState.value.content)
            copyManager.showToast()
        } catch (e: Exception){
            setError(e)
        }
    }

    private fun mathFormula(){
        safeBackgroundLaunch(
            code = {
                val mathResult = mathFormulaUseCase.execute(
                    formulaID = formulaID,
                    inputData = _screenState.value.content.inputData
                )
                updateFormulaState { state ->
                    state.copy(content = state.content.copy(
                        resultData = mathResult
                    ))
                }
            },
            errorHandling = {
                setError(it)
            }
        )
    }

    private fun setError(exception: Exception){
        val errorData = when(exception){
            is NoAllDataEnterException -> ErrorData(R.string.enter_data, R.string.no_all_data)
            is MathException -> ErrorData(R.string.math_error, R.string.math_error_description, exception.message)
            else -> ErrorData()
        }
        _screenState.value = _screenState.value.copy(errorMessage = errorData)
    }

    private fun clearError(){
        _screenState.value = _screenState.value.copy(errorMessage = null)
    }

    private inline fun updateFormulaState(update: (FormulaState) -> FormulaState) {
        _screenState.value = update(_screenState.value)
    }

}