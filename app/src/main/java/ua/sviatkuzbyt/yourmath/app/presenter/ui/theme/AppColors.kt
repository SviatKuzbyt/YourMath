package ua.sviatkuzbyt.yourmath.app.presenter.ui.theme

import androidx.compose.ui.graphics.Color

data class AppColors(
    val primary: Color,
    val primaryTransparent: Color,
    val background: Color,
    val textPrimary: Color,
    val textSecondary: Color,
    val container: Color,
    val containerSearchField: Color,
    val containerField: Color,
    val buttonSecondary: Color,
) {
    companion object {
        fun getLightColors() = AppColors(
            primary = Color(0xFF0B8CE8),
            primaryTransparent = Color(0xFFBBDAF0),
            background = Color(0xFFE6EDF1),
            textPrimary = Color(0xFF01070C),
            textSecondary = Color(0xFF737A7F),
            container = Color(0xFFF3F9FE),
            containerSearchField = Color(0xFFDBE1E6),
            containerField = Color(0xFFE6EDF1),
            buttonSecondary = Color(0xFFC2C9CD)
        )

        fun getDarkColors() = AppColors(
            primary = Color(0xFF0B8CE8),
            primaryTransparent = Color(0xFF00233C),
            background = Color(0xFF01070C),
            textPrimary = Color(0xFFF3F9FE),
            textSecondary = Color(0xFF7A8185),
            container = Color(0xFF0D1318),
            containerSearchField = Color(0xFF181F23),
            containerField = Color(0xFF181F23),
            buttonSecondary = Color(0xFF3A4146)
        )
    }
}