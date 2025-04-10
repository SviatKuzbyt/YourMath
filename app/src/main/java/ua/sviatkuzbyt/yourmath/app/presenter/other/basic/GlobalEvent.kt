package ua.sviatkuzbyt.yourmath.app.presenter.other.basic

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

enum class GlobalEventType{
    Nothing, AddHistoryRecord, ChangeFormulaList
}

object GlobalEvent {
    private val _event = MutableStateFlow(GlobalEventType.Nothing)
    val event: StateFlow<GlobalEventType> = _event

    fun sendEvent(newEvent: GlobalEventType) {
        if (newEvent != _event.value){
            _event.value = newEvent
        }
    }

    fun clearEvent() {
        _event.value = GlobalEventType.Nothing
    }
}