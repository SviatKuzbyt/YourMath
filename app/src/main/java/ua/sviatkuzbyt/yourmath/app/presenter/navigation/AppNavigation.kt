package ua.sviatkuzbyt.yourmath.app.presenter.navigation

import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.ProvidableCompositionLocal
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import ua.sviatkuzbyt.yourmath.app.presenter.screens.main.MainScreen

val LocalNavController: ProvidableCompositionLocal<NavController> = staticCompositionLocalOf {
    error("LocalNavController: No installed")
}

@Composable
fun AppNavigation(){
    val modifier = Modifier
        .fillMaxSize()
        .windowInsetsPadding(WindowInsets.systemBars)

    val navController = rememberNavController()

    CompositionLocalProvider(LocalNavController provides navController ) {
        NavHost(
            modifier = modifier,
            navController = navController,
            startDestination = MainRoute,
        ){
            composable<MainRoute> {
                MainScreen()
            }
        }
    }
}