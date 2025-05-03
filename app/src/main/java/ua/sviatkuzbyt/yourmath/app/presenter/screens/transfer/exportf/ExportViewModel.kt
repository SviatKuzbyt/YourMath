package ua.sviatkuzbyt.yourmath.app.presenter.screens.transfer.exportf

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import ua.sviatkuzbyt.yourmath.app.R
import ua.sviatkuzbyt.yourmath.app.presenter.controllers.transfer.TransferState
import ua.sviatkuzbyt.yourmath.app.presenter.other.basic.safeBackgroundLaunch
import ua.sviatkuzbyt.yourmath.domain.usecases.transfer.ExportUseCase
import javax.inject.Inject

@HiltViewModel
class ExportViewModel @Inject constructor (
    private val exportUseCase: ExportUseCase
): ViewModel() {
    private val _screenState = MutableStateFlow(TransferState.getExport())
    val screenState: StateFlow<TransferState> = _screenState

    fun exportToFile(fileUri: String) = safeBackgroundLaunch(
        code = {
            val isExportNotes = _screenState.value.isExportNotes ?: false

            _screenState.update { state ->
                state.copy(
                    content = R.string.wait,
                    isExportNotes = null,
                    isContinueButton = false,
                    isCanselButton = false
                )
            }

            exportUseCase.execute(fileUri, isExportNotes)

            _screenState.update { state ->
                state.copy(
                    content = R.string.export_finished,
                    isExitButton = true
                )
            }
        },
        errorHandling = { _screenState.value = TransferState.getError(
            tittle = R.string.export_error,
            content = R.string.export_error_details
        ) }
    )

    fun setIsExportNotes(isExport: Boolean){
        _screenState.update { state ->
            state.copy(isExportNotes = isExport)
        }
    }
}