package ua.sviatkuzbyt.yourmath.app.ui.screens.home

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import ua.sviatkuzbyt.yourmath.app.ui.intents.MainIntent
import ua.sviatkuzbyt.yourmath.app.ui.other.ErrorData
import ua.sviatkuzbyt.yourmath.app.ui.other.safeBackgroundLaunch
import ua.sviatkuzbyt.yourmath.app.ui.states.MainContent
import ua.sviatkuzbyt.yourmath.app.ui.states.MainState
import ua.sviatkuzbyt.yourmath.domain.structures.FormulaItem
import ua.sviatkuzbyt.yourmath.domain.usecases.main.GetFormulasUseCase
import ua.sviatkuzbyt.yourmath.domain.usecases.main.PinFormulaUseCase
import ua.sviatkuzbyt.yourmath.domain.usecases.main.SearchFormulasUseCase
import ua.sviatkuzbyt.yourmath.domain.usecases.main.UnpinFormulaUseCase
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val getFormulasUseCase: GetFormulasUseCase,
    private val pinFormulaUseCase: PinFormulaUseCase,
    private val unpinFormulaUseCase: UnpinFormulaUseCase,
    private val searchFormulasUseCase: SearchFormulasUseCase
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
        safeBackgroundLaunch(
            code = {
                val formulas = searchFormulasUseCase.execute(newText)
                val content = if(formulas.pins.isEmpty() && formulas.unpins.isEmpty()){
                    MainContent.NoSearchResult
                } else{
                    MainContent.Formulas(formulas)
                }
                _screenState.value = _screenState.value.copy(
                    content = content
                )
            },
            errorHandling = {
                setError()
            }
        )
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

                _screenState.value = MainState(
                    content = MainContent.Formulas(formulasLists)
                )
            },
            errorHandling = {
                setError()
            }
        )
    }

    private fun pinFormula(formula: FormulaItem){
        safeBackgroundLaunch(
            code = {
                val formulas = _screenState.value.content as MainContent.Formulas
                    _screenState.value = _screenState.value.copy(
                    content = MainContent.Formulas(
                        pinFormulaUseCase.execute(formula, formulas.lists)
                    )
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
                val formulas = _screenState.value.content as MainContent.Formulas

                _screenState.value = _screenState.value.copy(
                    content = MainContent.Formulas(
                        unpinFormulaUseCase.execute(formula, formulas.lists)
                    )
                )
            },
            errorHandling = {
                setError()
            }
        )
    }
}