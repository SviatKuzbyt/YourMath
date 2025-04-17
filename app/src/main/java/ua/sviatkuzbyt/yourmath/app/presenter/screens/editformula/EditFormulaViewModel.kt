package ua.sviatkuzbyt.yourmath.app.presenter.screens.editformula

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import ua.sviatkuzbyt.yourmath.app.presenter.controllers.editformula.EditFormulaIntent
import ua.sviatkuzbyt.yourmath.app.presenter.controllers.editformula.EditFormulaState
import ua.sviatkuzbyt.yourmath.app.presenter.controllers.editformula.EditFormulaStateContent
import javax.inject.Inject

@HiltViewModel
class EditFormulaViewModel @Inject constructor(): ViewModel() {

    private val _screenState = MutableStateFlow(EditFormulaState())
    val screenState: StateFlow<EditFormulaState> = _screenState

    fun onIntent(intent: EditFormulaIntent){
        when(intent){
            is EditFormulaIntent.SelectTab -> changeTab(intent.index)
            EditFormulaIntent.AddDataItem -> println("SKLT $intent")
            EditFormulaIntent.SaveChanges -> println("SKLT $intent")
        }
    }

    private fun changeTab(index: Int){
        val content = when(index){
            0 -> EditFormulaStateContent.InfoList
            1 -> EditFormulaStateContent.InputList
            2 -> EditFormulaStateContent.ResultList
            3 -> EditFormulaStateContent.CodeField
            else -> EditFormulaStateContent.Nothing
        }

        _screenState.update { state ->
            state.copy(
                selectedTab = index,
                content = content
            )
        }
    }

    companion object{
        const val NEW_FORMULA = 0L
    }
}