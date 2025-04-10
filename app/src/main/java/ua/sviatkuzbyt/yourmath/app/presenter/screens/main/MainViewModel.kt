package ua.sviatkuzbyt.yourmath.app.presenter.screens.main

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import ua.sviatkuzbyt.yourmath.app.presenter.controllers.main.MainListContent
import ua.sviatkuzbyt.yourmath.app.presenter.controllers.main.MainIntent
import ua.sviatkuzbyt.yourmath.app.presenter.controllers.main.MainState
import ua.sviatkuzbyt.yourmath.app.presenter.other.basic.EmptyScreenInfo
import ua.sviatkuzbyt.yourmath.app.presenter.other.basic.ErrorData
import ua.sviatkuzbyt.yourmath.app.presenter.other.basic.safeBackgroundLaunch
import ua.sviatkuzbyt.yourmath.domain.structures.main.FormulaItem
import ua.sviatkuzbyt.yourmath.domain.structures.main.SplitFormulaItems
import ua.sviatkuzbyt.yourmath.domain.usecases.main.GetFormulasListUseCase
import ua.sviatkuzbyt.yourmath.domain.usecases.main.PinFormulaUseCase
import ua.sviatkuzbyt.yourmath.domain.usecases.main.SearchFormulasUseCase
import ua.sviatkuzbyt.yourmath.domain.usecases.main.UnpinFormulaUseCase
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val getFormulasListUseCase: GetFormulasListUseCase,
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
                val formulasFromDB = getFormulasListUseCase.execute()

                val listContent = if(formulasFromDB.isEmpty()){
                    MainListContent.EmptyScreen(EmptyScreenInfo.noFormulas())
                } else{
                    MainListContent.FormulaList(formulasFromDB)
                }

                _screenState.update { state ->
                    state.copy(listContent = listContent)
                }
            },
            errorHandling = ::setError
        )
    }

    fun onIntent(intent: MainIntent){
        when(intent){
            is MainIntent.PinFormula -> pinFormula(intent.formula)
            is MainIntent.UnPinFormula -> unpinFormula(intent.formula)
            is MainIntent.ChangeSearchText ->{
                updateSearchText(intent.newText)
                searchFormulas(intent.newText)
            }
            MainIntent.CloseDialog -> clearError()
            MainIntent.Reload -> reloadFormulas()
        }
    }

    private fun pinFormula(formula: FormulaItem){
        safeBackgroundLaunch(
            code = {
                //update record in DB
                pinFormulaUseCase.execute(formula)

                _screenState.update { state ->
                    val oldFormulas = (state.listContent as MainListContent.FormulaList).formulas
                    state.copy(
                        listContent = MainListContent.FormulaList(
                            SplitFormulaItems(
                                pins = (oldFormulas.pins + formula).sortedBy { it.position },
                                unpins = oldFormulas.unpins - formula
                            )
                        )
                    )
                }
            },
            errorHandling = ::setError
        )
    }

    private fun unpinFormula(formula: FormulaItem){
        safeBackgroundLaunch(
            code = {
                //update record in DB
                unpinFormulaUseCase.execute(formula)

                _screenState.update { state ->
                    val oldFormulas = (state.listContent as MainListContent.FormulaList).formulas
                    state.copy(
                        listContent = MainListContent.FormulaList(
                            SplitFormulaItems(
                                pins = oldFormulas.pins - formula,
                                unpins = (oldFormulas.unpins + formula).sortedBy { it.position }
                            )
                        )
                    )
                }
            },
            errorHandling = ::setError
        )
    }

    private fun updateSearchText(newText: String){
        _screenState.update { state ->
            state.copy(searchText = newText)
        }
    }

    private fun searchFormulas(newText: String){
        safeBackgroundLaunch(
            code = {
                //search formulas in DB
                val searchFormulas = searchFormulasUseCase.execute(newText)

                //update UI if it necessary
                val listContent = if(searchFormulas.isEmpty()){
                    MainListContent.EmptyScreen(EmptyScreenInfo.noSearchResult())
                } else{
                    MainListContent.FormulaList(searchFormulas)
                }

                _screenState.update { state ->
                    state.copy(listContent = listContent)
                }
            },
            errorHandling = ::setError
        )
    }

    private fun reloadFormulas(){
        if(screenState.value.searchText.isNotEmpty()){
            searchFormulas(screenState.value.searchText)
        } else {
            loadFormulas()
        }
    }

    private fun setError(exception: Exception){
        _screenState.update { state ->
            state.copy(errorMessage = ErrorData(detailStr = exception.message))
        }
    }

    private fun clearError(){
        _screenState.value = _screenState.value.copy(errorMessage = null)
    }
}