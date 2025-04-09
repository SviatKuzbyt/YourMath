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
private fun ButtonIconBasic(
    @DrawableRes imageRes: Int,
    contentDescription: String,
    color: Color,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
){
    Icon(
        //Button appearance
        imageVector = ImageVector.vectorResource(imageRes),
        contentDescription = contentDescription,
        tint = color,

        modifier = modifier
            .size(AppSizes.dp48)

            //Button click
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
fun ButtonIconTopBar(
    @DrawableRes imageRes: Int,
    @StringRes contentDescriptionRes: Int,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
){
    ButtonIconBasic(
        imageRes = imageRes,
        contentDescription = stringResource(contentDescriptionRes),
        color = AppTheme.colors.textPrimary,
        modifier = modifier,
        onClick = onClick
    )
}

@Composable
fun ButtonIconContainer(
    @DrawableRes imageRes: Int,
    @StringRes contentDescriptionRes: Int,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
){
    ButtonIconBasic(
        imageRes = imageRes,
        contentDescription = stringResource(contentDescriptionRes),
        color = AppTheme.colors.primary,
        modifier = modifier,
        onClick = onClick
    )
}