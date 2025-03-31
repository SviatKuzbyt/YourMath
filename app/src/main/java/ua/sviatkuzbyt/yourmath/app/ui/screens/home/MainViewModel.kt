package ua.sviatkuzbyt.yourmath.app.ui.screens.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import ua.sviatkuzbyt.yourmath.app.ui.intents.MainIntent
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
        }
    }

    private fun loadFormulas() = viewModelScope.launch(Dispatchers.IO){
        val formulasLists = getFormulasUseCase.execute()

        _screenState.value =
            if (formulasLists.pins.isNotEmpty() && formulasLists.unpins.isNotEmpty()){
                MainState(formulas = formulasLists)
            } else{
                MainState(contentOnScreen = ContentOnScreen.NoFormulas)
            }
    }

    private fun pinFormula(formula: FormulaItem){
        pinFormulaUseCase.execute(formula.id)

        val oldLists = _screenState.value.formulas
        _screenState.value = _screenState.value.copy(
            formulas = oldLists.copy(
                (oldLists.pins + formula).sortedBy { it.position },
                oldLists.unpins - formula
            )
        )
    }

    private fun unpinFormula(formula: FormulaItem){
        unpinFormulaUseCase.execute(formula.id)

        val oldLists = _screenState.value.formulas
        _screenState.value = _screenState.value.copy(
            formulas = oldLists.copy(
                oldLists.pins - formula,
                (oldLists.unpins + formula).sortedBy { it.position }
            )
        )
    }
}