package ua.sviatkuzbyt.yourmath.app.presenter.ui.elements.basic

import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import ua.sviatkuzbyt.yourmath.app.presenter.ui.theme.AppTheme

@Composable
fun CheckboxBlue(
    modifier: Modifier = Modifier,
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit
){
    Checkbox(
        checked = checked,
        onCheckedChange = onCheckedChange,
        colors = CheckboxDefaults.colors(
            checkmarkColor = AppTheme.colors.white,
            uncheckedColor = AppTheme.colors.textPrimary,
            checkedColor = AppTheme.colors.primary
        ),
        modifier = modifier
    )
}