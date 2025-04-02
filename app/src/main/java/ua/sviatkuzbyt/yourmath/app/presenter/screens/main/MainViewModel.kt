package ua.sviatkuzbyt.yourmath.app.presenter.screens.main

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import ua.sviatkuzbyt.yourmath.app.presenter.controllers.main.MainContent
import ua.sviatkuzbyt.yourmath.app.presenter.controllers.main.MainIntent
import ua.sviatkuzbyt.yourmath.app.presenter.controllers.main.MainState
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
                val pinUnpinFormulas = getFormulasUseCase.execute()

                //set empty screen if no data
                val content = if (pinUnpinFormulas.isEmpty()){
                    MainContent.NoFormulas
                } else {
                    MainContent.Formulas(pinUnpinFormulas)
                }

                //update UI
                updateMainState { state ->
                    state.copy(content = content)
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
                val pinUnpinFormulas = (_screenState.value.content as MainContent.Formulas).lists

                updateMainState{ state ->
                    state.copy(
                        content = MainContent.Formulas(
                            PinUnpinFormulaItems(
                                pins = (pinUnpinFormulas.pins + formula).sortedBy { it.position },
                                unpins = pinUnpinFormulas.unpins - formula
                            )
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
                val pinUnpinFormulas = (_screenState.value.content as MainContent.Formulas).lists

                updateMainState{ state ->
                    state.copy(
                        content = MainContent.Formulas(
                            PinUnpinFormulaItems(
                                pins = pinUnpinFormulas.pins - formula,
                                unpins = (pinUnpinFormulas.unpins + formula).sortedBy { it.position }
                            )
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

                //set empty screen if no search result
                val content = if(pinUnpinFormulas.isEmpty()){
                    MainContent.NoSearchResult
                } else{
                    MainContent.Formulas(pinUnpinFormulas)
                }

                //update UI if it necessary
                if (content != _screenState.value.content){
                    updateMainState { state ->
                        state.copy(content = content)
                    }
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