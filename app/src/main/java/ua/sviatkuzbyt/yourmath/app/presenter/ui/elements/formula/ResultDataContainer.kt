package ua.sviatkuzbyt.yourmath.app.presenter.ui.elements.formula

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.selection.selectable
import androidx.compose.material3.Text
import androidx.compose.material3.ripple
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.Role
import ua.sviatkuzbyt.yourmath.app.presenter.ui.elements.basic.Container
import ua.sviatkuzbyt.yourmath.app.presenter.ui.theme.AppSizes
import ua.sviatkuzbyt.yourmath.app.presenter.ui.theme.AppTheme

@Composable
fun ResultDataContainer(
    title: String,
    content: String,
    onTextClick: () -> Unit
) {
    Container {
        Column(
            modifier = Modifier.padding(AppSizes.dp16)
        ) {
            Text(
                text = title,
                style = AppTheme.types.basic,
                modifier = Modifier.padding(bottom = AppSizes.dp4)
            )

            Text(
                text = content,
                style = AppTheme.types.bold,
                modifier = Modifier.clickable(
                    onClick = onTextClick,
                    interactionSource = null,
                    indication = ripple(
                        color = AppTheme.colors.textSecondary,
                    )
                )
            )
        }
    }
}