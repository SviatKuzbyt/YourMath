package ua.sviatkuzbyt.yourmath.app.presenter.ui.elements.basic

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import ua.sviatkuzbyt.yourmath.app.R
import ua.sviatkuzbyt.yourmath.app.presenter.ui.theme.AppShapes
import ua.sviatkuzbyt.yourmath.app.presenter.ui.theme.AppSizes
import ua.sviatkuzbyt.yourmath.app.presenter.ui.theme.AppTheme

@Composable
fun ButtonLargeLoad(){
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .padding(AppSizes.dp16)
            .fillMaxWidth()
            .height(AppSizes.dp48)
            .background(
                color = AppTheme.colors.primary,
                shape = AppShapes.main
            )
    ){
        Icon(
            imageVector = ImageVector.vectorResource(R.drawable.ic_load),
            contentDescription = stringResource(R.string.load),
            tint = Color(0xFFF3F9FE)
        )
    }
}