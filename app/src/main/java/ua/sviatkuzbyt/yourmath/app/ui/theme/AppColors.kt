package ua.sviatkuzbyt.yourmath.app.ui.theme

import androidx.compose.ui.graphics.Color

data class AppColors(
    val primary: Color,
    val borderAndInactive: Color,
    val background: Color,
    val textPrimary: Color,
    val textSecondary: Color,
    val container: Color,
    val buttonTopBar: Color,
    val buttonInContainer: Color
) {
    companion object {
        fun getLightColors() = AppColors(
            primary = Color(0xFF0B8CE8),
            borderAndInactive = Color(0xFFC3CDD4),
            background = Color(0xFFF3F9FE),
            textPrimary = Color(0xFF021C2E),
            textSecondary = Color(0xFF7A8B96),
            container = Color(0xFFE7EEF4),
            buttonTopBar = Color(0xFF021C2E),
            buttonInContainer = Color(0xFF0B8CE8),
        )

        fun getDarkColors() = AppColors(
            primary = Color(0xFF0B8CE8),
            borderAndInactive = Color(0xFF32434F),
            background = Color(0xFF021523),
            textPrimary = Color(0xFFF3F9FE),
            textSecondary = Color(0xFF7A8790),
            container = Color(0xFF0C1E2C),
            buttonTopBar = Color(0xFFF3F9FE),
            buttonInContainer = Color(0xFF0B8CE8),
        )
    }
}