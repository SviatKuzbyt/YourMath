package ua.sviatkuzbyt.yourmath.app.presenter.ui.elements.editformula.tabs.component

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.material3.ripple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.style.TextAlign
import ua.sviatkuzbyt.yourmath.app.presenter.ui.theme.AppSizes
import ua.sviatkuzbyt.yourmath.app.presenter.ui.theme.AppTheme

@Composable
fun TabItem(
    selected: Boolean,
    text: String,
    onClick: () -> Unit
){
    val textColor =
        if (selected) AppTheme.colors.white
        else AppTheme.colors.textPrimary

    val backgroundColor =
        if (selected) AppTheme.colors.primary
        else AppTheme.colors.container

    val animateBackgroundColor by animateColorAsState(backgroundColor)

    Text(
        text = text,
        style = AppTheme.types.basic,
        color = textColor,
        textAlign = TextAlign.Center,
        modifier = Modifier
            .padding(horizontal = AppSizes.dp4)
            .padding(bottom = AppSizes.dp16)
            .clickable(
                onClick = onClick,
                role = Role.Tab,
                interactionSource = null,
                indication = ripple(
                    color = AppTheme.colors.background
                )
            )
            .drawBehind {
                val radius = size.height / 2
                drawRoundRect(
                    color = animateBackgroundColor,
                    cornerRadius = CornerRadius(radius)
                )
            }
            .padding(vertical = AppSizes.dp10, horizontal = AppSizes.dp20)
    )
}