package ua.sviatkuzbyt.yourmath.app.presenter.ui.elements.formula

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import ua.sviatkuzbyt.yourmath.app.R
import ua.sviatkuzbyt.yourmath.app.presenter.ui.elements.basic.Container
import ua.sviatkuzbyt.yourmath.app.presenter.ui.theme.AppShapes
import ua.sviatkuzbyt.yourmath.app.presenter.ui.theme.AppSizes
import ua.sviatkuzbyt.yourmath.app.presenter.ui.theme.AppTheme

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
        Column(modifier = Modifier.padding(AppSizes.dp16)) {
            Text(
                text = label,
                style = AppTheme.types.basic,
                modifier = Modifier.padding(bottom = AppSizes.dp8)
            )

            OutlinedTextField(
                value = data,
                onValueChange = onDataChange,
                singleLine = true,
                textStyle = AppTheme.types.basic,
                shape = AppShapes.field,

                placeholder = {
                    Text(
                        text = hint ?: stringResource(R.string.enter_data),
                        style = AppTheme.types.secondary
                    )
                },

                colors = OutlinedTextFieldDefaults.colors(
                    focusedContainerColor = AppTheme.colors.containerField,
                    unfocusedContainerColor = AppTheme.colors.containerField,
                    unfocusedBorderColor = AppTheme.colors.containerField,
                    focusedBorderColor = AppTheme.colors.containerField,
                    focusedTextColor = AppTheme.colors.textPrimary,
                    unfocusedTextColor = AppTheme.colors.textPrimary,
                    cursorColor = AppTheme.colors.primary,
                ),

                modifier = Modifier
                    .fillMaxWidth()
                    .height(AppSizes.dp52),

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
}