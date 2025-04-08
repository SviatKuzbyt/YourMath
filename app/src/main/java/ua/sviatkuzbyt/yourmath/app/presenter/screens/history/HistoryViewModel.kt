package ua.sviatkuzbyt.yourmath.app.presenter.screens.history

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import ua.sviatkuzbyt.yourmath.app.presenter.controllers.history.HistoryIntent
import ua.sviatkuzbyt.yourmath.app.presenter.controllers.history.HistoryState
import ua.sviatkuzbyt.yourmath.app.presenter.other.ErrorData
import ua.sviatkuzbyt.yourmath.app.presenter.other.HistoryListManager
import ua.sviatkuzbyt.yourmath.app.presenter.other.safeBackgroundLaunch
import ua.sviatkuzbyt.yourmath.domain.usecases.history.CleanHistoryUseCase
import ua.sviatkuzbyt.yourmath.domain.usecases.history.GetHistoryUseCase
import javax.inject.Inject

@HiltViewModel
class HistoryViewModel @Inject constructor(
    private val getHistoryUseCase: GetHistoryUseCase,
    private val cleanHistoryUseCase: CleanHistoryUseCase,
    private val listManager: HistoryListManager
): ViewModel() {

    private val _screenState = MutableStateFlow(HistoryState())
    val screenState: StateFlow<HistoryState> = _screenState

    init {
        loadData()
    }

    fun onIntent(intent: HistoryIntent){
        when(intent){
            HistoryIntent.LoadNewItems -> loadData()
            HistoryIntent.ReloadItems -> reloadData()
            HistoryIntent.CleanHistory -> clearHistory()
            is HistoryIntent.SetCleanDialog -> setCleanDialog(intent.isShow)
            HistoryIntent.CloseErrorDialog -> clearError()
        }
    }

    private fun setCleanDialog(isShow: Boolean){
        updateHistoryState{state ->
            state.copy(showCleanDialog = isShow)
        }
    }

    private fun reloadData(){
        listManager.clearData()
        _screenState.value = HistoryState()
        loadData()
    }

    private fun loadData(){
        safeBackgroundLaunch(
            code = {
                val newRecords = getHistoryUseCase.execute(listManager.loadOffset, listManager.loadLimit)
                if (newRecords.isEmpty()){
                    _screenState.value = HistoryState(isRecords = false)
                } else{
                    val formatedNewRecords = listManager.convertList(newRecords)

                    updateHistoryState { state ->
                        state.copy(
                            items = state.items + formatedNewRecords,
                            allDataIsLoaded = listManager.allDataIsLoaded
                        )
                    }
                }
            },
            errorHandling = ::setError
        )
    }

    private fun clearHistory(){
        safeBackgroundLaunch(
            code = {
                cleanHistoryUseCase.execute()
                listManager.clearData()
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