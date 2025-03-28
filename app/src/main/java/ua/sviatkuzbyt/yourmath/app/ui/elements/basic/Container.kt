package ua.sviatkuzbyt.yourmath.app.ui.elements.basic

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import ua.sviatkuzbyt.yourmath.app.ui.theme.AppShapes
import ua.sviatkuzbyt.yourmath.app.ui.theme.AppSizes
import ua.sviatkuzbyt.yourmath.app.ui.theme.AppTheme

@Composable
fun Container(
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit
){
    Box(
        modifier
            .padding(horizontal = AppSizes.dp16)
            .padding(bottom = AppSizes.dp16)
            .fillMaxWidth()
            .border(
                width = AppSizes.dp1,
                color = AppTheme.colors.borderAndInactive,
                shape = AppShapes.main
            )

    ){
        content()
    }
}