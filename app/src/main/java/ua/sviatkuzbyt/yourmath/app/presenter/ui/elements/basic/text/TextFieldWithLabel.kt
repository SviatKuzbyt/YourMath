package ua.sviatkuzbyt.yourmath.app.presenter.ui.elements.basic.text

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import ua.sviatkuzbyt.yourmath.app.presenter.ui.theme.AppShapes
import ua.sviatkuzbyt.yourmath.app.presenter.ui.theme.AppSizes
import ua.sviatkuzbyt.yourmath.app.presenter.ui.theme.AppTheme

@Composable
fun TextFieldWithLabel(
    modifier: Modifier = Modifier,
    label: String,
    text: String,
    hint: String,
    onTextChange: (String) -> Unit,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    keyboardActions: KeyboardActions = KeyboardActions.Default,
    singleLine: Boolean = true,
    maxLines: Int = 1
) {
    Column(
        modifier = modifier
    ) {
        Text(
            text = label,
            style = AppTheme.types.basic,
            modifier = Modifier.padding(bottom = AppSizes.dp8)
        )

        OutlinedTextField(
            value = text,
            onValueChange = onTextChange,
            singleLine = singleLine,
            textStyle = AppTheme.types.basic,
            shape = AppShapes.field,
            keyboardOptions = keyboardOptions,
            keyboardActions = keyboardActions,

            maxLines = maxLines,

            placeholder = {
                Text(
                    text = hint,
                    style = AppTheme.types.secondary
                )
            },

            modifier = Modifier.fillMaxWidth(),

            colors = OutlinedTextFieldDefaults.colors(
                focusedContainerColor = AppTheme.colors.containerField,
                unfocusedContainerColor = AppTheme.colors.containerField,
                unfocusedBorderColor = AppTheme.colors.containerField,
                focusedBorderColor = AppTheme.colors.containerField,
                focusedTextColor = AppTheme.colors.textPrimary,
                unfocusedTextColor = AppTheme.colors.textPrimary,
                cursorColor = AppTheme.colors.primary,
            )
        )
    }
}