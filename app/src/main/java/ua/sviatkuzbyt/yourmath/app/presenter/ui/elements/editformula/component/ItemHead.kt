package ua.sviatkuzbyt.yourmath.app.presenter.ui.elements.editformula.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.HorizontalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import ua.sviatkuzbyt.yourmath.app.R
import ua.sviatkuzbyt.yourmath.app.presenter.ui.elements.basic.button.ButtonIconNarrow
import ua.sviatkuzbyt.yourmath.app.presenter.ui.elements.basic.text.SubTittleText
import ua.sviatkuzbyt.yourmath.app.presenter.ui.theme.AppSizes
import ua.sviatkuzbyt.yourmath.app.presenter.ui.theme.AppTheme

@Composable
fun ItemHead(
    onDelete: () -> Unit,
    onMoveUp: () -> Unit,
    onMoveDown: () -> Unit
){
    Column(Modifier.fillMaxWidth()) {
        Row(Modifier.fillMaxWidth()) {
            SubTittleText(
                textRes = R.string.item,
                modifier = Modifier
                    .padding(start = AppSizes.dp16, top = AppSizes.dp16)
                    .weight(1f)
            )
            ButtonIconNarrow(
                imageRes = R.drawable.btn_delete_narrow,
                contentDescription = R.string.delete_item,
                color = AppTheme.colors.primary,
                onClick = onDelete
            )
            ButtonIconNarrow(
                imageRes = R.drawable.btn_down,
                contentDescription = R.string.move_item_up,
                color = AppTheme.colors.textSecondary,
                onClick = onMoveDown
            )
            ButtonIconNarrow(
                imageRes = R.drawable.btn_up,
                contentDescription = R.string.move_item_down,
                color = AppTheme.colors.textSecondary,
                onClick = onMoveUp,
                modifier = Modifier.padding(end = AppSizes.dp8)
            )
        }

        HorizontalDivider(color = AppTheme.colors.background)
    }
}