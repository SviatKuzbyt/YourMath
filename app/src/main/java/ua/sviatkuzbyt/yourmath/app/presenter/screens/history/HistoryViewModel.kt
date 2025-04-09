package ua.sviatkuzbyt.yourmath.app.presenter.screens.history

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import ua.sviatkuzbyt.yourmath.app.presenter.controllers.history.HistoryIntent
import ua.sviatkuzbyt.yourmath.app.presenter.controllers.history.HistoryState
import ua.sviatkuzbyt.yourmath.app.presenter.other.ErrorData
import ua.sviatkuzbyt.yourmath.app.presenter.other.safeBackgroundLaunch
import ua.sviatkuzbyt.yourmath.domain.structures.history.GetFiltersUseCase
import ua.sviatkuzbyt.yourmath.domain.structures.history.HistoryItem
import ua.sviatkuzbyt.yourmath.domain.usecases.history.CleanHistoryUseCase
import ua.sviatkuzbyt.yourmath.domain.usecases.history.GetHistoryUseCase
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import javax.inject.Inject

@HiltViewModel
class HistoryViewModel @Inject constructor(
    private val getHistoryUseCase: GetHistoryUseCase,
    private val cleanHistoryUseCase: CleanHistoryUseCase,
    private val getFiltersUseCase: GetFiltersUseCase
): ViewModel() {

    private val _screenState = MutableStateFlow(HistoryState())
    val screenState: StateFlow<HistoryState> = _screenState
    private val formatter = DateTimeFormatter.ofPattern("dd MMM yyyy")
    private var filterFormulaID = 0L

    init {
        loadData(false)
    }

    fun onIntent(intent: HistoryIntent){
        when(intent){
            HistoryIntent.LoadNewItems -> loadData(false)
            HistoryIntent.ReloadItems -> reloadData()
            HistoryIntent.CleanHistory -> clearHistory()
            is HistoryIntent.SetCleanDialog -> setCleanDialog(intent.isShow)
            HistoryIntent.CloseErrorDialog -> clearError()
            HistoryIntent.CloseFilters -> closeFilters()
            HistoryIntent.OpenFilters -> {
                openFilters()
                getFilters()
            }
            is HistoryIntent.SelectFilter -> selectFilter(intent.formulaID)
        }
    }

    private fun selectFilter(formulaID: Long){

        if (formulaID != filterFormulaID){
            filterFormulaID = formulaID

            val updatedFilterList = screenState.value.filterList.map { filter ->
                if (filter.isSelected){
                    filter.copy(isSelected = false)
                } else if(filter.formulaID == formulaID){
                    filter.copy(isSelected = true)
                } else{
                    filter
                }
            }

            updateHistoryState { state ->
                state.copy(
                    filterList = updatedFilterList,
                    showFilterList = false
                )
            }
        }
    }

    private fun closeFilters(){
        updateHistoryState{state ->
            state.copy(showFilterList = false)
        }
    }

    private fun openFilters(){
        updateHistoryState{state ->
            state.copy(showFilterList = true)
        }
    }

    private fun getFilters(){
        if (screenState.value.filterList.isEmpty()){
            safeBackgroundLaunch(
                code = {
                    val filterList = getFiltersUseCase.execute()
                    updateHistoryState{state ->
                        state.copy(filterList = filterList)
                    }
                },
                errorHandling = ::setError
            )
        }
    }

    private fun setCleanDialog(isShow: Boolean){
        updateHistoryState{state ->
            state.copy(showCleanDialog = isShow)
        }
    }

    private fun reloadData(){
        _screenState.value = HistoryState()
        loadData(true)
    }

    private fun loadData(loadFromStart: Boolean){
        safeBackgroundLaunch(
            code = {
                val newRecords = getHistoryUseCase.execute(loadFromStart)
                if (newRecords.isEmpty()){
                    _screenState.value = HistoryState(isRecords = false)
                } else{
                    val formatedNewRecords = formatDateInList(newRecords)

                    updateHistoryState { state ->
                        state.copy(
                            items = state.items + formatedNewRecords,
                            allDataIsLoaded = getHistoryUseCase.isAllLoaded
                        )
                    }
                }
            },
            errorHandling = ::setError
        )
    }

    private fun formatDateInList(list: List<HistoryItem>): List<HistoryItem>{
        return list.map { item ->
            if (item is HistoryItem.Date){
                formatDateItem(item)
            } else{
                item
            }
        }
    }

    private fun formatDateItem(item: HistoryItem.Date): HistoryItem.Date{
        val localDate = LocalDate.ofEpochDay(item.dateLong)
        val formattedDate = localDate.format(formatter)
        return HistoryItem.Date(formattedDate, item.dateLong)
    }

    private fun clearHistory(){
        safeBackgroundLaunch(
            code = {
                cleanHistoryUseCase.execute()
                _screenState.value = HistoryState(isRecords = false)
            },
            errorHandling = ::setError
        )
    }

    private fun setError(exception: Exception){
        updateHistoryState { state ->
            state.copy(errorMessage = ErrorData(detailStr = exception.message))
        }
    }

    private fun clearError(){
        _screenState.value = _screenState.value.copy(errorMessage = null)
    }

    private inline fun updateHistoryState(update: (HistoryState) -> HistoryState) {
        _screenState.value = update(_screenState.value)
    }
}