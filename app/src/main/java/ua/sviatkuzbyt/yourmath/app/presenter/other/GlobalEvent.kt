package ua.sviatkuzbyt.yourmath.app.presenter.other

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

enum class GlobalEventType{
    Nothing, AddHistoryRecord, ChangeFormulaList
}

object GlobalEvent {
    private val _event = MutableStateFlow(GlobalEventType.Nothing)
    val event: StateFlow<GlobalEventType> = _event

    fun sendEvent(event: GlobalEventType) {
        _event.value = event
    }

    fun clearEvent() {
        _event.value = GlobalEventType.Nothing
    }
}