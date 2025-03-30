package ua.sviatkuzbyt.yourmath.app.ui.elements.basic

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
import ua.sviatkuzbyt.yourmath.app.ui.theme.AppSizes
import ua.sviatkuzbyt.yourmath.app.ui.theme.AppTheme

@Composable
private fun ButtonIcon(
    @DrawableRes imageRes: Int,
    contentDescription: String,
    color: Color,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
){
    Icon(
        imageVector = ImageVector.vectorResource(imageRes),
        contentDescription = contentDescription,
        tint = color,
        modifier = modifier
            .size(AppSizes.dp48)
            .clickable(
                onClick = onClick,
                role = Role.Button,
                interactionSource = null,
                indication = ripple(
                    color = AppTheme.colors.textSecondary,
                    radius = AppSizes.dp20
                )
            )
    )
}

@Composable
fun ButtonTopBar(
    @DrawableRes imageRes: Int,
    @StringRes contentDescriptionRes: Int,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
){
    ButtonIcon(
        imageRes = imageRes,
        contentDescription = stringResource(contentDescriptionRes),
        color = AppTheme.colors.textPrimary,
        modifier = modifier,
        onClick = onClick
    )
}

@Composable
fun ButtonContainer(
    @DrawableRes imageRes: Int,
    @StringRes contentDescriptionRes: Int,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
){
    ButtonIcon(
        imageRes = imageRes,
        contentDescription = stringResource(contentDescriptionRes),
        color = AppTheme.colors.primary,
        modifier = modifier,
        onClick = onClick
    )
}