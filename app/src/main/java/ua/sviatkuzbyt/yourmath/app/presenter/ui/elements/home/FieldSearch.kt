package ua.sviatkuzbyt.yourmath.app.presenter.ui.elements.home

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.input.ImeAction
import ua.sviatkuzbyt.yourmath.app.R
import ua.sviatkuzbyt.yourmath.app.presenter.ui.theme.AppShapes
import ua.sviatkuzbyt.yourmath.app.presenter.ui.theme.AppSizes
import ua.sviatkuzbyt.yourmath.app.presenter.ui.theme.AppTheme

@Composable
fun FieldSearch(
    text: String,
    onTextChange: (String) -> Unit
){
    val localKeyboard = LocalSoftwareKeyboardController.current

    OutlinedTextField(

        value = text,
        onValueChange = onTextChange,
        singleLine = true,
        textStyle = AppTheme.types.basic,
        shape = AppShapes.main,

        placeholder = {
            Text(
                text = stringResource(R.string.search),
                style = AppTheme.types.secondary
            )
        },

        leadingIcon = {
            Icon(
                imageVector = ImageVector.vectorResource(R.drawable.ic_search),
                contentDescription = stringResource(R.string.search),
                tint = AppTheme.colors.textSecondary
            )
        },

        keyboardActions = KeyboardActions(
            onSearch = {
                localKeyboard?.hide()
            }
        ),

        colors = OutlinedTextFieldDefaults.colors(
            focusedContainerColor = AppTheme.colors.containerSearchField,
            unfocusedContainerColor = AppTheme.colors.containerSearchField,
            unfocusedBorderColor = AppTheme.colors.containerSearchField,
            focusedBorderColor = AppTheme.colors.containerSearchField,
            focusedTextColor = AppTheme.colors.textPrimary,
            unfocusedTextColor = AppTheme.colors.textPrimary,
            cursorColor = AppTheme.colors.primary,
        ),

        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = AppSizes.dp16)
            .padding(bottom = AppSizes.dp16)
            .height(AppSizes.dp52),

        keyboardOptions = KeyboardOptions.Default.copy(
            imeAction = ImeAction.Search
        )
    )
}