package ua.sviatkuzbyt.yourmath.app.presenter.other.editformula

import androidx.compose.runtime.MutableState
import androidx.compose.ui.focus.FocusState

fun saveField(
    focusState: FocusState,
    isFocused: MutableState<Boolean>,
    isChanged: MutableState<Boolean>,
    onSave: () -> Unit
){
    if (isFocused.value && isChanged.value && !focusState.isFocused){
        onSave()
        isChanged.value = false
    }
    isFocused.value = focusState.isFocused
}

fun setFieldChanged(isChanged: MutableState<Boolean>){
    if (!isChanged.value){
        isChanged.value = true
    }
}