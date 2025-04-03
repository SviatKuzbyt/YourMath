package ua.sviatkuzbyt.yourmath.app.presenter.ui.elements.basic

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
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
    label: String,
    hint: String,
    data: String,
    modifier: Modifier = Modifier,
    onDataChange: (String) -> Unit
){
    Column(modifier = modifier.padding(horizontal = AppSizes.dp16)) {
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
                    text = hint,
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
                .height(AppSizes.dp52)
        )
    }
}