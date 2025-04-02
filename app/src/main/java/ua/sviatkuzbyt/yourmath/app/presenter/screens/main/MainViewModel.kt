package ua.sviatkuzbyt.yourmath.app.presenter.screens.main

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import ua.sviatkuzbyt.yourmath.app.presenter.controllers.main.MainIntent
import ua.sviatkuzbyt.yourmath.app.presenter.controllers.main.MainState
import ua.sviatkuzbyt.yourmath.app.presenter.controllers.main.ShowOnScreen
import ua.sviatkuzbyt.yourmath.app.presenter.other.ErrorData
import ua.sviatkuzbyt.yourmath.app.presenter.other.safeBackgroundLaunch
import ua.sviatkuzbyt.yourmath.domain.structures.FormulaItem
import ua.sviatkuzbyt.yourmath.domain.structures.PinUnpinFormulaItems
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

    private fun loadFormulas(){
        safeBackgroundLaunch(
            code = {
                //get all data from DB and set in UI
                val formulasFromDB = getFormulasUseCase.execute()

                val showOnScreen = if (formulasFromDB.isEmpty()){
                    ShowOnScreen.NoFormulas
                } else {
                    ShowOnScreen.Formulas
                }

                updateMainState { state ->
                    state.copy(
                        showOnScreen = showOnScreen,
                        formulas = formulasFromDB
                    )
                }
            },
            errorHandling = {
                setError()
            }
        )
    }

    private inline fun updateMainState(update: (MainState) -> MainState) {
        _screenState.value = update(_screenState.value)
    }

    private fun setError(){
        _screenState.value = _screenState.value.copy(errorMessage = ErrorData())
    }

    fun onIntent(intent: MainIntent){
        when(intent){
            is MainIntent.PinFormula -> pinFormula(intent.formula)
            is MainIntent.UnPinFormula -> unpinFormula(intent.formula)
            is MainIntent.ChangeSearchText -> updateSearchText(intent.newText)
            MainIntent.CloseDialog -> clearError()
        }
    }

    private fun pinFormula(formula: FormulaItem){
        safeBackgroundLaunch(
            code = {
                //update record in DB
                pinFormulaUseCase.execute(formula)

                //move formula up in UI
                updateMainState{ state ->
                    val oldFormulas = state.formulas

                    state.copy(
                        formulas =
                            PinUnpinFormulaItems(
                                pins = (oldFormulas.pins + formula).sortedBy { it.position },
                                unpins = oldFormulas.unpins - formula
                            )
                        )

                }
            },
            errorHandling = {
                setError()
            }
        )
    }

    private fun unpinFormula(formula: FormulaItem){
        safeBackgroundLaunch(
            code = {
                //update record in DB
                unpinFormulaUseCase.execute(formula)

                //move formula down in UI
                updateMainState{ state ->
                    val oldFormulas = state.formulas

                    state.copy(
                        formulas =
                            PinUnpinFormulaItems(
                                pins = oldFormulas.pins - formula,
                                unpins = (oldFormulas.unpins + formula).sortedBy { it.position }
                            )

                    )
                }
            },
            errorHandling = {
                setError()
            }
        )
    }

    private fun updateSearchText(newText: String){
        _screenState.value = _screenState.value.copy(searchText = newText)
        searchFormulas(newText)
    }

    private fun searchFormulas(newText: String){
        safeBackgroundLaunch(
            code = {
                //get formulas by search text from DB
                val pinUnpinFormulas = searchFormulasUseCase.execute(newText)

                val showOnScreen = if(pinUnpinFormulas.isEmpty()){
                    ShowOnScreen.NoSearchResult
                } else{
                    ShowOnScreen.Formulas
                }

                val newState = _screenState.value.copy(
                    showOnScreen = showOnScreen,
                    formulas = pinUnpinFormulas
                )

                if (_screenState.value != newState){
                    _screenState.value = newState
                }
            },
            errorHandling = {
                setError()
            }
        )
    }

    private fun clearError(){
        _screenState.value = _screenState.value.copy(errorMessage = null)
    }
}