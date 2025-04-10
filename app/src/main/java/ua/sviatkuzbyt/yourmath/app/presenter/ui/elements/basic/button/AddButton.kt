package ua.sviatkuzbyt.yourmath.app.presenter.ui.elements.basic.button

import androidx.annotation.StringRes
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.ripple
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.semantics.Role
import ua.sviatkuzbyt.yourmath.app.R
import ua.sviatkuzbyt.yourmath.app.presenter.ui.theme.AppShapes
import ua.sviatkuzbyt.yourmath.app.presenter.ui.theme.AppSizes
import ua.sviatkuzbyt.yourmath.app.presenter.ui.theme.AppTheme

@Composable
fun BoxScope.AddButton(
    @StringRes descriptionRes: Int,
    onClick: () -> Unit
){
    Icon(
        imageVector = ImageVector.vectorResource(R.drawable.btn_add),
        contentDescription = stringResource(descriptionRes),
        tint = AppTheme.colors.white,

        modifier = Modifier
            .padding(AppSizes.dp16)
            .size(AppSizes.dp56)
            .background(
                color = AppTheme.colors.primary,
                shape = AppShapes.main
            )
            .align(Alignment.BottomEnd)

            .clickable(
                onClick = onClick,
                role = Role.Button,
                interactionSource = null,
                indication = ripple(color = AppTheme.colors.background)
            )
    )
}