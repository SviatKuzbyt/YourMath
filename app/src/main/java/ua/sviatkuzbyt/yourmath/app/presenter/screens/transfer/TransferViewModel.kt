package ua.sviatkuzbyt.yourmath.app.presenter.screens.transfer

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import ua.sviatkuzbyt.yourmath.app.presenter.controllers.transfer.TransferIntent
import ua.sviatkuzbyt.yourmath.app.presenter.controllers.transfer.TransferState
import ua.sviatkuzbyt.yourmath.app.presenter.controllers.transfer.TransferType
import ua.sviatkuzbyt.yourmath.app.presenter.other.basic.safeBackgroundLaunch
import ua.sviatkuzbyt.yourmath.domain.usecases.transfer.ExportUseCase
import javax.inject.Inject

@HiltViewModel
class TransferViewModel @Inject constructor (
    private val exportUseCase: ExportUseCase,
    sentData: SavedStateHandle
): ViewModel() {
    private val type: TransferType = sentData["type"] ?: TransferType.Error

    private val _screenState = MutableStateFlow(
        when(type){
            TransferType.Import -> TransferState.getImport()
            TransferType.Export -> TransferState.getExport()
            TransferType.Error -> TransferState.getError()
        }
    )
    val screenState: StateFlow<TransferState> = _screenState

    fun onIntent(intent: TransferIntent){
        when(intent){
            TransferIntent.CreateFileToExport -> {
                println("SKLT start")
                safeBackgroundLaunch(
                    code = {
                        val data = exportUseCase.execute()
                        Log.v("SKLT", data)
                    },
                    errorHandling = {}
                )

            }
        }
    }
}