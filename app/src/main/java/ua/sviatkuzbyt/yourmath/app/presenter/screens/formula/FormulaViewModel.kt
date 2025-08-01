package ua.sviatkuzbyt.yourmath.app.presenter.screens.formula

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import ua.sviatkuzbyt.yourmath.app.R
import ua.sviatkuzbyt.yourmath.app.presenter.controllers.formula.FormulaIntent
import ua.sviatkuzbyt.yourmath.app.presenter.controllers.formula.FormulaState
import ua.sviatkuzbyt.yourmath.app.presenter.other.formula.CopyFormulaToClipboardManager
import ua.sviatkuzbyt.yourmath.app.presenter.other.basic.ErrorData
import ua.sviatkuzbyt.yourmath.app.presenter.other.basic.safeBackgroundLaunch
import ua.sviatkuzbyt.yourmath.app.presenter.screens.history.isHistoryUpdate
import ua.sviatkuzbyt.yourmath.data.other.MathException
import ua.sviatkuzbyt.yourmath.data.other.NoAllDataEnterException
import ua.sviatkuzbyt.yourmath.domain.usecases.formula.GetFormulaUseCase
import ua.sviatkuzbyt.yourmath.domain.usecases.formula.GetFormulaWithHistoryDataUseCase
import ua.sviatkuzbyt.yourmath.domain.usecases.formula.MathFormulaUseCase
import ua.sviatkuzbyt.yourmath.domain.usecases.formula.SaveFormulaToHistoryUseCase
import java.time.LocalDate
import javax.inject.Inject

@HiltViewModel
class FormulaViewModel @Inject constructor (
    private val getFormulaUseCase: GetFormulaUseCase,
    private val mathFormulaUseCase: MathFormulaUseCase,
    private val saveFormulaToHistoryUseCase: SaveFormulaToHistoryUseCase,
    private val getFormulaWithHistoryDataUseCase: GetFormulaWithHistoryDataUseCase,
    private val copyManager: CopyFormulaToClipboardManager,
    sentData: SavedStateHandle
): ViewModel(){

    private val formulaID: Long = sentData["formulaID"] ?: 0L
    private val historyID: Long? = sentData["historyID"]
    private val _screenState = MutableStateFlow(FormulaState())
    val screenState: StateFlow<FormulaState> = _screenState

    init {
        loadFormulaData()
    }

    fun onIntent(intent: FormulaIntent){
        when(intent){
            is FormulaIntent.ChangeInputData -> changeInputData(intent.position, intent.newData)
            FormulaIntent.MathFormula -> mathFormula()
            FormulaIntent.CopyFormulaToClipboard -> copyData()
            is FormulaIntent.CopyTextToClipboard -> copyTextToClipboard(intent.text)
            FormulaIntent.CloseDialog -> clearError()
        }
    }

    private fun loadFormulaData(){
        safeBackgroundLaunch(
            code = {
                //get empty formula data and set it in UI
                val formula = if(historyID != null){
                    getFormulaWithHistoryDataUseCase.execute(formulaID, historyID)
                } else {
                    getFormulaUseCase.execute(formulaID)
                }
                _screenState.value = FormulaState(content = formula)
            },
            errorHandling = ::setError
        )
    }

    private inline fun updateFormulaState(update: (FormulaState) -> FormulaState) {
        _screenState.value = update(_screenState.value)
    }

    private fun changeInputData(position: Int, newText: String) {
        updateFormulaState { state ->
            //set changed text in list
            val updatedInputs = state.content.inputData.mapIndexed { index, item ->
                if (index == position) item.copy(data = newText) else item
            }
            //update UI
            state.copy(
                content = state.content.copy(inputData = updatedInputs)
            )
        }
    }

    private fun mathFormula(){
        safeBackgroundLaunch(
            code = {
                //set loading
                updateFormulaState { state ->
                    state.copy(isLoading = true)
                }

                //calculate the formula
                val mathResult = mathFormulaUseCase.execute(
                    formulaID = formulaID,
                    formulaInputList = _screenState.value.content.inputData
                )

                //set UI
                updateFormulaState { state ->
                    state.copy(
                        content = state.content.copy(
                            resultData = mathResult
                        ),
                        isLoading = false
                    )
                }

                //save results in history
                saveFormulaToHistoryUseCase.execute(
                    formulaContent = screenState.value.content,
                    formulaID = formulaID,
                    date = LocalDate.now().toEpochDay()
                )

                historyID?.let {
                    isHistoryUpdate.value = true
                }
            },
            errorHandling = ::setError
        )
    }

    private fun copyTextToClipboard(text: String){
        try {
            copyManager.copyText(text)
            copyManager.showToast()
        } catch (e: Exception){
            setError(e)
        }
    }

    private fun copyData() {
        try {
            copyManager.copyFormula(screenState.value.content)
            copyManager.showToast()
        } catch (e: Exception){
            setError(e)
        }
    }

    private fun setError(exception: Exception){
        //set error info
        val errorData = when(exception){
            is NoAllDataEnterException -> ErrorData(
                tittleRes = R.string.enter_data,
                detailRes = R.string.no_all_data
            )
            is MathException -> ErrorData(
                tittleRes = R.string.math_error,
                detailRes = R.string.math_error_description,
                detailStr = exception.message
            )
            else -> ErrorData(detailStr = exception.message)
        }

        //show dialog in UI
        updateFormulaState { state ->
            state.copy(
                isLoading = false,
                errorMessage = errorData
            )
        }
    }

    private fun clearError(){
        _screenState.value = _screenState.value.copy(errorMessage = null)
    }
}