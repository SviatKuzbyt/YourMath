package ua.sviatkuzbyt.yourmath.app.presenter.ui.elements.history.items

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.semantics.Role
import ua.sviatkuzbyt.yourmath.app.R
import ua.sviatkuzbyt.yourmath.app.presenter.ui.theme.AppSizes
import ua.sviatkuzbyt.yourmath.app.presenter.ui.theme.AppTheme

@Composable
fun LoadMoreButton(
    onClick: () -> Unit
){
    Row (
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .padding(bottom = AppSizes.dp16)
            .fillMaxWidth()
            .height(AppSizes.dp48)
            .clickable(
                onClick = onClick,
                role = Role.Button,
                interactionSource = null,
                indication = null
            )
    ){
        val text = stringResource(R.string.load_more)
        Icon(
            imageVector = ImageVector.vectorResource(R.drawable.ic_load_more),
            contentDescription = text,
            tint = AppTheme.colors.primary,
            modifier = Modifier.height(AppSizes.dp20).padding(end = AppSizes.dp16)
        )

        Text(
            text = text,
            style = AppTheme.types.blueButton
        )
    }
}