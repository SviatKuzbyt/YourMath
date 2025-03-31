package ua.sviatkuzbyt.yourmath.app.ui.screens.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import ua.sviatkuzbyt.yourmath.app.ui.intents.MainIntent
import ua.sviatkuzbyt.yourmath.app.ui.states.MainState
import ua.sviatkuzbyt.yourmath.domain.structures.FormulaItem
import ua.sviatkuzbyt.yourmath.domain.usecases.main.GetFormulasUseCase
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val getFormulasUseCase: GetFormulasUseCase
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
        _screenState.value = MainState(
            formulas = getFormulasUseCase.execute()
        )
    }

    private fun pinFormula(formula: FormulaItem){
        val formulas = _screenState.value.formulas
        _screenState.value = _screenState.value.copy(
            formulas = formulas.copy(
                (formulas.pins + formula).sortedBy { it.position },
                    formulas.unpins - formula
            )
        )
    }

    private fun unpinFormula(formula: FormulaItem){
        val formulas = _screenState.value.formulas
        _screenState.value = _screenState.value.copy(
            formulas = formulas.copy(
                formulas.pins - formula,
                (formulas.unpins + formula).sortedBy { it.position }
            )
        )
    }
}