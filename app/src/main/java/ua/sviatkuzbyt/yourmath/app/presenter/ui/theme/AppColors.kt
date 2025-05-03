package ua.sviatkuzbyt.yourmath.app.presenter.ui.theme

import androidx.compose.ui.graphics.Color

data class AppColors(
    val primary: Color,
    val background: Color,
    val textPrimary: Color,
    val textSecondary: Color,
    val container: Color,
    val containerSearchField: Color,
    val containerField: Color,
    val white: Color
) {
    companion object {
        fun getLightColors() = AppColors(
            primary = Color(0xFF0B8CE8),
            background = Color(0xFFE6EDF1),
            textPrimary = Color(0xFF01070C),
            textSecondary = Color(0xFF737A7F),
            container = Color(0xFFF3F9FE),
            containerSearchField = Color(0xFFDBE1E6),
            containerField = Color(0xFFE6EDF1),
            white = Color(0xFFF3F9FE)
        )

        fun getDarkColors() = AppColors(
            primary = Color(0xFF0B8CE8),
            background = Color(0xFF01070C),
            textPrimary = Color(0xFFF3F9FE),
            textSecondary = Color(0xFF7A8185),
            container = Color(0xFF0D1318),
            containerSearchField = Color(0xFF181F23),
            containerField = Color(0xFF181F23),
            white = Color(0xFFF3F9FE)
        )
    }
}