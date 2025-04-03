package ua.sviatkuzbyt.yourmath.app.presenter.screens.formula

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import ua.sviatkuzbyt.yourmath.app.presenter.controllers.formula.FormulaState
import ua.sviatkuzbyt.yourmath.app.presenter.other.ErrorData
import ua.sviatkuzbyt.yourmath.app.presenter.other.safeBackgroundLaunch
import ua.sviatkuzbyt.yourmath.domain.usecases.formula.GetFormulaUseCase
import javax.inject.Inject

@HiltViewModel
class FormulaViewModel @Inject constructor (
    private val getFormulaUseCase: GetFormulaUseCase,
    sentData: SavedStateHandle
): ViewModel(){
    private val formulaID: Long = sentData["formulaID"] ?: 0
    private val _screenState = MutableStateFlow(FormulaState())
    val screenState: StateFlow<FormulaState> = _screenState

    init {
        loadFormulaData()
    }

    private fun loadFormulaData(){
        safeBackgroundLaunch(
            code = {
                _screenState.value = FormulaState(
                    content = getFormulaUseCase.execute(formulaID)
                )
            },
            errorHandling = {setError()}
        )
    }

    private fun setError(){
        _screenState.value = _screenState.value.copy(errorMessage = ErrorData())
    }

    private fun clearError(){
        _screenState.value = _screenState.value.copy(errorMessage = null)
    }
}