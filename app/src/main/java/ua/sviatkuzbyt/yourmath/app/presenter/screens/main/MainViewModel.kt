package ua.sviatkuzbyt.yourmath.app.presenter.screens.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import ua.sviatkuzbyt.yourmath.app.presenter.controllers.main.MainListContent
import ua.sviatkuzbyt.yourmath.app.presenter.controllers.main.MainIntent
import ua.sviatkuzbyt.yourmath.app.presenter.controllers.main.MainState
import ua.sviatkuzbyt.yourmath.app.presenter.other.basic.EmptyScreenInfo
import ua.sviatkuzbyt.yourmath.app.presenter.other.basic.ErrorData
import ua.sviatkuzbyt.yourmath.app.presenter.other.basic.safeBackgroundLaunch
import ua.sviatkuzbyt.yourmath.domain.structures.main.FormulaItem
import ua.sviatkuzbyt.yourmath.domain.usecases.main.ObserveFormulasUseCase
import ua.sviatkuzbyt.yourmath.domain.usecases.main.PinFormulaUseCase
import ua.sviatkuzbyt.yourmath.domain.usecases.main.SplitFormulaItemsUseCase
import ua.sviatkuzbyt.yourmath.domain.usecases.main.UnpinFormulaUseCase
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val observeFormulasUseCase: ObserveFormulasUseCase,
    private val pinFormulaUseCase: PinFormulaUseCase,
    private val unpinFormulaUseCase: UnpinFormulaUseCase,
    private val spiltFormulaItemsUseCase: SplitFormulaItemsUseCase
): ViewModel() {

    private val _screenState = MutableStateFlow(MainState())
    val screenState: StateFlow<MainState> = _screenState

    init { observeFormulas() }

    private fun observeFormulas() {
        combine(
            observeFormulasUseCase.execute(),
            _screenState.map { it.searchText }.distinctUntilChanged()
        ) { formulas, searchText ->
            val filtered = if (searchText.isBlank()) {
                formulas
            } else {
                formulas.filter { it.name.contains(searchText, ignoreCase = true) }
            }

            if (filtered.isEmpty()) {
                if (searchText.isBlank()){
                    MainListContent.EmptyScreen(EmptyScreenInfo.noFormulas())
                } else {
                    MainListContent.EmptyScreen(EmptyScreenInfo.noSearchResult())
                }

            } else {
                MainListContent.FormulaList(
                    spiltFormulaItemsUseCase.execute(filtered)
                )
            }
        }
            .onEach { listContent ->
                _screenState.update { it.copy(listContent = listContent) }
            }
            .catch { e -> setError(e) }
            .launchIn(viewModelScope)
    }

    fun onIntent(intent: MainIntent){
        when(intent){
            is MainIntent.PinFormula -> pinFormula(intent.formula)
            is MainIntent.UnPinFormula -> unpinFormula(intent.formula)
            is MainIntent.ChangeSearchText ->updateSearchText(intent.newText)
            MainIntent.CloseDialog -> clearError()
        }
    }

    private fun pinFormula(formula: FormulaItem){
        safeBackgroundLaunch(
            code = { pinFormulaUseCase.execute(formula) },
            errorHandling = ::setError
        )
    }

    private fun unpinFormula(formula: FormulaItem){
        safeBackgroundLaunch(
            code = { unpinFormulaUseCase.execute(formula) },
            errorHandling = ::setError
        )
    }

    private fun updateSearchText(newText: String){
        _screenState.update { state ->
            state.copy(searchText = newText)
        }
    }

    private fun setError(exception: Throwable){
        _screenState.update { state ->
            state.copy(errorMessage = ErrorData(detailStr = exception.message))
        }
    }

    private fun clearError(){
        _screenState.value = _screenState.value.copy(errorMessage = null)
    }
}