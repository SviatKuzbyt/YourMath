package ua.sviatkuzbyt.yourmath.app.presenter.ui.elements.editor.items

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.Role
import ua.sviatkuzbyt.yourmath.app.R
import ua.sviatkuzbyt.yourmath.app.presenter.ui.elements.basic.Container
import ua.sviatkuzbyt.yourmath.app.presenter.ui.elements.basic.button.ButtonIconNarrow
import ua.sviatkuzbyt.yourmath.app.presenter.ui.theme.AppSizes
import ua.sviatkuzbyt.yourmath.app.presenter.ui.theme.AppTheme

@Composable
fun EditFormulaItem(
    name: String,
    onClick: () -> Unit,
    onDelete: () -> Unit,
    onMoveDown: () -> Unit,
    onMoveUp: () -> Unit
){
    Container(
        Modifier
        .clickable(
            onClick = onClick,
            role = Role.Button,
            interactionSource = null,
            indication = null
        )
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .padding(start = AppSizes.dp20, end = AppSizes.dp8)
        ) {
            Text(
                text = name,
                style = AppTheme.types.basic,
                modifier = Modifier
                    .weight(1f)
                    .padding(end = AppSizes.dp8)
                    .padding(vertical = AppSizes.dp16)
            )

            ButtonIconNarrow(
                imageRes = R.drawable.btn_delete_narrow,
                contentDescription = R.string.delete_item,
                color = AppTheme.colors.primary,
                onClick = onDelete,
                modifier = Modifier.padding(vertical = AppSizes.dp8)
            )

            ButtonIconNarrow(
                imageRes = R.drawable.btn_down,
                contentDescription = R.string.move_item_up,
                color = AppTheme.colors.textSecondary,
                onClick = onMoveDown,
                modifier = Modifier.padding(vertical = AppSizes.dp8)
            )

            ButtonIconNarrow(
                imageRes = R.drawable.btn_up,
                contentDescription = R.string.move_item_down,
                color = AppTheme.colors.textSecondary,
                onClick = onMoveUp,
                modifier = Modifier.padding(vertical = AppSizes.dp8)
            )
        }
    }
}