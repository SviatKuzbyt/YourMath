package ua.sviatkuzbyt.yourmath.app.presenter.navigation

import androidx.navigation.NavController

sealed class NavigateIntent {
    data object NavigateUp: NavigateIntent()
    data class OpenFormulaScreen(val formulaID: Long): NavigateIntent()
    data object OpenHistoryScreen: NavigateIntent()
    data object OpenEditorScreen: NavigateIntent()
}

fun onNavigateIntent(
    navController: NavController,
    intent: NavigateIntent
) {
    when(intent){
        NavigateIntent.NavigateUp -> navController.navigateUp()
        NavigateIntent.OpenEditorScreen -> navController.navigate(EditorRoute)
        is NavigateIntent.OpenFormulaScreen -> navController.navigate(FormulaRoute(intent.formulaID))
        NavigateIntent.OpenHistoryScreen -> navController.navigate(HistoryRoute)
    }
}