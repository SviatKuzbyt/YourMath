package ua.sviatkuzbyt.yourmath.app.presenter.screens.history

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import ua.sviatkuzbyt.yourmath.app.presenter.controllers.history.EmptyScreenInfo
import ua.sviatkuzbyt.yourmath.app.presenter.controllers.history.HistoryAboveScreenContent
import ua.sviatkuzbyt.yourmath.app.presenter.controllers.history.HistoryIntent
import ua.sviatkuzbyt.yourmath.app.presenter.controllers.history.HistoryListContent
import ua.sviatkuzbyt.yourmath.app.presenter.controllers.history.HistoryState
import ua.sviatkuzbyt.yourmath.app.presenter.other.ErrorData
import ua.sviatkuzbyt.yourmath.app.presenter.other.FormatHistoryItems
import ua.sviatkuzbyt.yourmath.app.presenter.other.safeBackgroundLaunch
import ua.sviatkuzbyt.yourmath.domain.structures.history.FormulaFilterItem
import ua.sviatkuzbyt.yourmath.domain.usecases.history.GetFiltersUseCase
import ua.sviatkuzbyt.yourmath.app.presenter.other.HistoryItem
import ua.sviatkuzbyt.yourmath.domain.usecases.history.CleanHistoryUseCase
import ua.sviatkuzbyt.yourmath.domain.usecases.history.GetHistoryUseCase
import javax.inject.Inject

private const val ALL_ITEMS = 0L

@HiltViewModel
class HistoryViewModel @Inject constructor(
    private val getHistoryUseCase: GetHistoryUseCase,
    private val cleanHistoryUseCase: CleanHistoryUseCase,
    private val getFiltersUseCase: GetFiltersUseCase,
    private val formatHistoryItems: FormatHistoryItems
): ViewModel() {

    private val _screenState = MutableStateFlow(HistoryState())
    val screenState: StateFlow<HistoryState> = _screenState

    private var filterFormulaID = ALL_ITEMS
    private val itemList = mutableListOf<HistoryItem>()
    private val filterList = mutableListOf<FormulaFilterItem>()
    private var isLoadMore = true

    init {
        loadData()
    }

    fun onIntent(intent: HistoryIntent){
        when(intent){
            HistoryIntent.LoadNewItems -> loadData()
            HistoryIntent.ReloadItems -> reloadData()
            HistoryIntent.OpenCleanDialog -> updateAboveScreenContent(HistoryAboveScreenContent.CleanDialog)
            HistoryIntent.CleanHistory -> clearHistory()
            HistoryIntent.OpenFilters -> openFilters()
            is HistoryIntent.SelectFilter -> selectFilter(intent.formulaID)
            HistoryIntent.CloseAboveScreenContentDialog -> closeAboveScreenContentDialog()
        }
    }

    private fun loadData() = safeBackgroundLaunch(
        code = {
            val newRecords = getHistoryUseCase.execute(
                formulaID = filterFormulaID,
                offset = formatHistoryItems.loadOffset,
                limit = formatHistoryItems.loadLimit
            )

            val listContent = if(newRecords.isEmpty()){
                val info = if(filterFormulaID == ALL_ITEMS)
                    EmptyScreenInfo.noItemsInHistory()
                else
                    EmptyScreenInfo.noFilterResultHistory()
                HistoryListContent.EmptyScreen(info)
            }
                else {
                    val formatedNewRecords = formatHistoryItems.convertList(newRecords)
                    itemList.addAll(formatedNewRecords)

                    if (newRecords.size < formatHistoryItems.loadLimit){
                        isLoadMore = false
                    }

                    HistoryListContent.Items(itemList.toList(), isLoadMore)
                }

            _screenState.update { state ->
                state.copy(listContent = listContent)
            }
        },
        errorHandling = ::setError
    )


    private fun reloadData(){
        itemList.clear()
        formatHistoryItems.clear()
        isLoadMore = true
        loadData()
    }

    private fun clearHistory() = safeBackgroundLaunch(
        code = {
            cleanHistoryUseCase.execute()
            _screenState.value = HistoryState(
                listContent = HistoryListContent.EmptyScreen(
                    EmptyScreenInfo.noItemsInHistory()
                )
            )
            filterList.clear()
            filterFormulaID = ALL_ITEMS
        },
        errorHandling = ::setError
    )

    private fun openFilters() = safeBackgroundLaunch(
        code = {
            if (filterList.isEmpty()){
                filterList.addAll(getFiltersUseCase.execute())
            }

            updateAboveScreenContent(
                HistoryAboveScreenContent.FilterSheet(filterList.toList())
            )
        },
        errorHandling = ::setError
    )

    private fun selectFilter(formulaID: Long){
        if (formulaID != filterFormulaID){
            filterFormulaID = formulaID

            val toSelectIndex = filterList.indexOfFirst { it.formulaID == formulaID }
            val toUnselectIndex = filterList.indexOfFirst { it.isSelected }

            if (toSelectIndex >= 0) {
                filterList[toSelectIndex] = filterList[toSelectIndex].copy(isSelected = true)
            }
            if (toUnselectIndex >= 0) {
                filterList[toUnselectIndex] = filterList[toUnselectIndex].copy(isSelected = false)
            }

            updateAboveScreenContent(HistoryAboveScreenContent.Nothing)
            reloadData()
        }
    }

    private fun updateAboveScreenContent(content: HistoryAboveScreenContent) {
        _screenState.update { it.copy(aboveScreenContent = content) }
    }

    private fun closeAboveScreenContentDialog(){
        _screenState.update { state ->
            state.copy(aboveScreenContent = HistoryAboveScreenContent.Nothing)
        }
    }

    private fun setError(exception: Exception){
        _screenState.update { state ->
            state.copy(
                aboveScreenContent = HistoryAboveScreenContent.ErrorDialog(
                    ErrorData(detailStr = exception.message)
                )
            )
        }
    }
}