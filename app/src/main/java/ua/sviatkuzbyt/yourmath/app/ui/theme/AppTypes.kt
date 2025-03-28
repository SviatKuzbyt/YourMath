package ua.sviatkuzbyt.yourmath.app.ui.theme

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import ua.sviatkuzbyt.yourmath.app.R

//types store and init
data class AppTypes(
    val tittle: TextStyle,
    val basic: TextStyle,
    val bold: TextStyle,
    val secondary: TextStyle,
    val white: TextStyle
){
    companion object{
        private val fontFamily = FontFamily(
            Font(R.font.inter_regular, FontWeight.Normal),
            Font(R.font.inter_bold, FontWeight.Bold)
        )

        fun getTypographies(colors: AppColors) = AppTypes(
            tittle = TextStyle(
                fontFamily = fontFamily,
                fontWeight = FontWeight.Bold,
                fontSize = AppSizes.sp24,
                color = colors.textPrimary
            ),
            basic = TextStyle(
                fontFamily = fontFamily,
                fontWeight = FontWeight.Normal,
                fontSize = AppSizes.sp16,
                color = colors.textPrimary
            ),
            bold = TextStyle(
                fontFamily = fontFamily,
                fontWeight = FontWeight.Bold,
                fontSize = AppSizes.sp16,
                color = colors.textPrimary
            ),
            secondary = TextStyle(
                fontFamily = fontFamily,
                fontWeight = FontWeight.Normal,
                fontSize = AppSizes.sp16,
                color = colors.textSecondary
            ),
            white = TextStyle(
                fontFamily = fontFamily,
                fontWeight = FontWeight.Bold,
                fontSize = AppSizes.sp16,
                color = Color(0xFFF3F9FE)
            )
        )
    }
}