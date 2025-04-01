package ua.sviatkuzbyt.yourmath.app.ui.screens.home

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import ua.sviatkuzbyt.yourmath.app.ui.intents.MainIntent
import ua.sviatkuzbyt.yourmath.app.ui.other.ErrorData
import ua.sviatkuzbyt.yourmath.app.ui.other.safeBackgroundLaunch
import ua.sviatkuzbyt.yourmath.app.ui.states.ContentOnScreen
import ua.sviatkuzbyt.yourmath.app.ui.states.MainState
import ua.sviatkuzbyt.yourmath.domain.structures.FormulaItem
import ua.sviatkuzbyt.yourmath.domain.usecases.main.GetFormulasUseCase
import ua.sviatkuzbyt.yourmath.domain.usecases.main.PinFormulaUseCase
import ua.sviatkuzbyt.yourmath.domain.usecases.main.UnpinFormulaUseCase
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val getFormulasUseCase: GetFormulasUseCase,
    private val pinFormulaUseCase: PinFormulaUseCase,
    private val unpinFormulaUseCase: UnpinFormulaUseCase,
): ViewModel() {

    private val _screenState = MutableStateFlow(MainState())
    val screenState: StateFlow<MainState> = _screenState

    init {
        loadFormulas()
    }

    fun onIntent(intent: MainIntent){
        when(intent){
            is MainIntent.PinFormula -> pinFormula(intent.formula)
            is MainIntent.UnPinFormula -> unpinFormula(intent.formula)
            MainIntent.CloseDialog -> clearError()
            is MainIntent.ChangeSearchText -> changeSearch(intent.newText)
        }
    }

    private fun changeSearch(newText: String){
        _screenState.value = _screenState.value.copy(searchText = newText)
    }

    private fun clearError(){
        _screenState.value = _screenState.value.copy(errorMessage = null)
    }

    private fun setError(){
        _screenState.value = _screenState.value.copy(errorMessage = ErrorData())
    }

    private fun loadFormulas(){
        safeBackgroundLaunch(
            code = {
                val formulasLists = getFormulasUseCase.execute()

                _screenState.value =
                    if (formulasLists.pins.isEmpty() && formulasLists.unpins.isEmpty()){
                        MainState(contentOnScreen = ContentOnScreen.NoFormulas)
                    } else{
                        MainState(formulas = formulasLists)
                    }
            },
            errorHandling = {
                setError()
            }
        )
    }

    private fun pinFormula(formula: FormulaItem){
        safeBackgroundLaunch(
            code = {
                _screenState.value = _screenState.value.copy(
                    formulas = pinFormulaUseCase.execute(formula, _screenState.value.formulas)
                )
            },
            errorHandling = {
                setError()
            }
        )
    }

    private fun unpinFormula(formula: FormulaItem){
        safeBackgroundLaunch(
            code = {
                _screenState.value = _screenState.value.copy(
                    formulas = unpinFormulaUseCase.execute(formula, _screenState.value.formulas)
                )
            },
            errorHandling = {
                setError()
            }
        )
    }
}