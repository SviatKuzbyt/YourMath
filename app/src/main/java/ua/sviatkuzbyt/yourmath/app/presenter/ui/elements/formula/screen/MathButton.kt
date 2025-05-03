package ua.sviatkuzbyt.yourmath.app.presenter.ui.elements.formula.screen

import androidx.compose.animation.Crossfade
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import ua.sviatkuzbyt.yourmath.app.R
import ua.sviatkuzbyt.yourmath.app.presenter.ui.elements.basic.button.ButtonLarge
import ua.sviatkuzbyt.yourmath.app.presenter.ui.elements.basic.button.ButtonLargeLoad
import ua.sviatkuzbyt.yourmath.app.presenter.ui.theme.AppSizes

@Composable
fun MathButton(
    isLoading: Boolean,
    onClick: () -> Unit
){
    Crossfade(isLoading){
        if (it){
            ButtonLargeLoad()
        } else {
            ButtonLarge(
                textRes = R.string.math,
                onClick = onClick,
                modifier = Modifier.padding(AppSizes.dp16)
            )
        }
    }
}