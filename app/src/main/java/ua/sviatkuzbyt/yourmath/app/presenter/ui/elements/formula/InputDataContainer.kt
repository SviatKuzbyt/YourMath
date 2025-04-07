package ua.sviatkuzbyt.yourmath.app.presenter.ui.elements.formula

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import ua.sviatkuzbyt.yourmath.app.R
import ua.sviatkuzbyt.yourmath.app.presenter.ui.elements.basic.Container
import ua.sviatkuzbyt.yourmath.app.presenter.ui.elements.basic.text.TextFieldWithLabel
import ua.sviatkuzbyt.yourmath.app.presenter.ui.theme.AppSizes

@Composable
fun InputDataContainer(
    label: String,
    data: String,
    hint: String?,
    onDataChange: (String) -> Unit,
    isDoneButton: Boolean,
    focusManager: FocusManager,
    onDone: () -> Unit
){
    Container {
        TextFieldWithLabel(
            label = label,
            text = data,
            hint = hint ?: stringResource(R.string.enter_data),
            onTextChange = onDataChange,
            modifier = Modifier.padding(horizontal =  AppSizes.dp16),

            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Number,
                imeAction = if(isDoneButton){
                    ImeAction.Done
                } else{
                    ImeAction.Next
                }
            ),

            keyboardActions = KeyboardActions(
                onNext = {
                    focusManager.moveFocus(FocusDirection.Next)
                },
                onDone = {
                    focusManager.clearFocus()
                    onDone()
                }
            )
        )
    }
}