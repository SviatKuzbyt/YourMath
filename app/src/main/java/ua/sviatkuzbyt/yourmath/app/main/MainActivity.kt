package ua.sviatkuzbyt.yourmath.app.main

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import ua.sviatkuzbyt.yourmath.app.navigation.AppNavigation
import ua.sviatkuzbyt.yourmath.app.ui.theme.YourMathTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            YourMathTheme {
                AppNavigation()
            }
        }
    }
}