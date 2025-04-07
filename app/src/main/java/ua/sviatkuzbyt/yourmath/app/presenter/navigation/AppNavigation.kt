package ua.sviatkuzbyt.yourmath.app.presenter.navigation

import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.ProvidableCompositionLocal
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import ua.sviatkuzbyt.yourmath.app.presenter.screens.EmptyScreen
import ua.sviatkuzbyt.yourmath.app.presenter.screens.formula.FormulaScreen
import ua.sviatkuzbyt.yourmath.app.presenter.screens.formula.FormulaViewModel
import ua.sviatkuzbyt.yourmath.app.presenter.screens.history.HistoryScreen
import ua.sviatkuzbyt.yourmath.app.presenter.screens.main.MainScreen

val LocalNavController: ProvidableCompositionLocal<NavController> = staticCompositionLocalOf {
    error("LocalNavController: No installed")
}

private val enterTransition = scaleIn(
    initialScale = 0.7f,
    animationSpec = tween(300)
) + fadeIn(
    animationSpec = tween(250, 50)
)

private val popEnterTransition = scaleIn(
    initialScale = 1.3f,
    animationSpec = tween(300)
) + fadeIn(
    animationSpec = tween(250, 50)
)

private val exitTransition = scaleOut(
    targetScale = 1.3f,
    animationSpec = tween(300)
) + fadeOut(
    animationSpec = tween(250, 50)
)

private val popExitTransition = scaleOut(
    targetScale = 0.7f,
    animationSpec = tween(300)
) + fadeOut(
    animationSpec = tween(250, 50)
)

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
            enterTransition = { enterTransition },
            popEnterTransition = { popEnterTransition },
            exitTransition = { exitTransition },
            popExitTransition = { popExitTransition }
        ){
            composable<MainRoute> {
                MainScreen()
            }

            composable<FormulaRoute> {
                val viewModel: FormulaViewModel = hiltViewModel()
                FormulaScreen(viewModel)
            }

            composable<HistoryRoute> {
                HistoryScreen()
            }

            composable<EditorRoute> {
                EmptyScreen()
            }
        }
    }
}