package ua.sviatkuzbyt.yourmath.app.presenter.screens.transfer.importf

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import ua.sviatkuzbyt.yourmath.app.R
import ua.sviatkuzbyt.yourmath.app.presenter.controllers.transfer.TransferState
import ua.sviatkuzbyt.yourmath.app.presenter.other.basic.safeBackgroundLaunch
import ua.sviatkuzbyt.yourmath.domain.usecases.transfer.ImportUseCase
import javax.inject.Inject

@HiltViewModel
class ImportViewModel @Inject constructor (
    private val importUseCase: ImportUseCase
): ViewModel() {
    private val _screenState = MutableStateFlow(TransferState.getImport())
    val screenState: StateFlow<TransferState> = _screenState

    fun importFromFile(fileUri: String) = safeBackgroundLaunch(
        code = {
            _screenState.update { state ->
                state.copy(
                    content = R.string.wait,
                    isContinueButton = false,
                    isCanselButton = false
                )
            }

            importUseCase.execute(fileUri)

            _screenState.update { state ->
                state.copy(
                    content = R.string.import_finished,
                    isExitButton = true
                )
            }
        },
        errorHandling = { _screenState.value = TransferState.getError(
            tittle = R.string.import_error,
            content = R.string.import_error_details
        ) }
    )
}