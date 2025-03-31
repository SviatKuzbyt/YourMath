package ua.sviatkuzbyt.yourmath.app.ui.screens.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import ua.sviatkuzbyt.yourmath.app.ui.states.MainState
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

    private fun loadFormulas() = viewModelScope.launch(Dispatchers.IO){
        val formulas = getFormulasUseCase.execute()
        val (pinnedList, noPinnedList) = formulas.partition { i2 -> i2.isPinned }
        _screenState.value = _screenState.value.copy(
            listPinnedFormulas = pinnedList,
            listNoPinnedFormulas = noPinnedList
        )
    }
}