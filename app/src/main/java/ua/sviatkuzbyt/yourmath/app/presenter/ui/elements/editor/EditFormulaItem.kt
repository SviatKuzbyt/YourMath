package ua.sviatkuzbyt.yourmath.app.presenter.ui.elements.editor

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
    onDelete: () -> Unit
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
                contentDescription = R.string.delete_formula,
                color = AppTheme.colors.primary,
                onClick = onDelete,
                modifier = Modifier.padding(vertical = AppSizes.dp8)
            )

            ButtonIconNarrow(
                imageRes = R.drawable.btn_move,
                contentDescription = R.string.move_formula,
                color = AppTheme.colors.buttonSecondary,
                onClick = { println("SKLT onMove") }, //temp
                modifier = Modifier.padding(vertical = AppSizes.dp8)
            )
        }
    }
}