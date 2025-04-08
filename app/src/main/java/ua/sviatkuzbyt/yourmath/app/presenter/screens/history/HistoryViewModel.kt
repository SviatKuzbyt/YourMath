package ua.sviatkuzbyt.yourmath.app.presenter.screens.history

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import ua.sviatkuzbyt.yourmath.app.presenter.controllers.history.HistoryIntent
import ua.sviatkuzbyt.yourmath.app.presenter.controllers.history.HistoryState
import ua.sviatkuzbyt.yourmath.app.presenter.other.HistoryListManager
import ua.sviatkuzbyt.yourmath.app.presenter.other.safeBackgroundLaunch
import ua.sviatkuzbyt.yourmath.domain.usecases.GetHistoryUseCase
import javax.inject.Inject

@HiltViewModel
class HistoryViewModel @Inject constructor(
    private val getHistoryUseCase: GetHistoryUseCase,
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
        }
    }

    private fun loadData(){
        safeBackgroundLaunch(
            code = {
                val newRecords = getHistoryUseCase.execute(listManager.loadOffset, listManager.loadLimit)
                val formatedNewRecords = listManager.convertList(newRecords)

                updateHistoryState { state ->
                    state.copy(
                        items = state.items + formatedNewRecords,
                        allDataIsLoaded = listManager.allDataIsLoaded
                    )
                }
            },
            errorHandling = {}
        )
    }

    private inline fun updateHistoryState(update: (HistoryState) -> HistoryState) {
        _screenState.value = update(_screenState.value)
    }
}