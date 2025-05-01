package ua.sviatkuzbyt.yourmath.app.core.main

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.core.view.WindowCompat
import dagger.hilt.android.AndroidEntryPoint
import ua.sviatkuzbyt.yourmath.app.presenter.navigation.AppNavigation
import ua.sviatkuzbyt.yourmath.app.presenter.ui.theme.YourMathTheme

@AndroidEntryPoint
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