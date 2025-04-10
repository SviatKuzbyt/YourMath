package ua.sviatkuzbyt.yourmath.app.presenter.ui.elements.basic.button

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.ripple
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.semantics.Role
import ua.sviatkuzbyt.yourmath.app.presenter.ui.theme.AppSizes
import ua.sviatkuzbyt.yourmath.app.presenter.ui.theme.AppTheme

@Composable
fun ButtonIconNarrow(
    @DrawableRes imageRes: Int,
    @StringRes contentDescription: Int,
    color: Color,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    Icon(
        //Button appearance
        imageVector = ImageVector.vectorResource(imageRes),
        contentDescription = stringResource(contentDescription),
        tint = color,

        modifier = modifier
            .size(height = AppSizes.dp48, width = AppSizes.dp32)

            //Button click
            .clickable(
                onClick = onClick,
                role = Role.Button,
                interactionSource = null,
                indication = ripple(
                    color = AppTheme.colors.textSecondary,
                    radius = AppSizes.dp16
                )
            )
    )
}