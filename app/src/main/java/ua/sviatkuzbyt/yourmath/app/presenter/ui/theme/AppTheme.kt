package ua.sviatkuzbyt.yourmath.app.presenter.ui.theme

import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.Modifier

val LocalThemeColors = staticCompositionLocalOf<AppColors> { error("No colors yet") }
val LocalThemeTypes = staticCompositionLocalOf<AppTypes> { error("No types yet") }

@Composable
fun YourMathTheme(
    isDark: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colors = if(isDark){
        AppColors.getDarkColors()
    } else{
        AppColors.getLightColors()
    }

    val type = AppTypes.getTypographies(colors)

    CompositionLocalProvider(
        LocalThemeColors provides colors,
        LocalThemeTypes provides type,
    ){
        Box(modifier = Modifier.fillMaxSize().background(colors.background)) {
            content()
        }
    }
}

object AppTheme {
    val colors: AppColors
        @Composable
        get() = LocalThemeColors.current

    val types: AppTypes
        @Composable
        get() = LocalThemeTypes.current
}