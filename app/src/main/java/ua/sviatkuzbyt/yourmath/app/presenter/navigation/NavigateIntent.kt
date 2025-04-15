package ua.sviatkuzbyt.yourmath.app.presenter.navigation

import androidx.navigation.NavController

sealed class NavigateIntent {
    data object NavigateBack: NavigateIntent()
    data class OpenFormulaScreen(val formulaID: Long): NavigateIntent()
    data object OpenHistoryScreen: NavigateIntent()
    data object OpenEditorScreen: NavigateIntent()
    data class OpenFormulaScreenHistory(
        val formulaID: Long,
        val historyID: Long
    ): NavigateIntent()
    data class OpenFormulaEditScreen(val formulaID: Long): NavigateIntent()
    data object OpenExportScreen: NavigateIntent()
    data object OpenImportScreen: NavigateIntent()
}

fun onNavigateIntent(
    navController: NavController,
    intent: NavigateIntent
) {
    when(intent){
        NavigateIntent.NavigateBack ->
            navController.navigateUp()

        NavigateIntent.OpenEditorScreen ->
            navController.navigate(EditorRoute)

        is NavigateIntent.OpenFormulaScreen ->
            navController.navigate(FormulaRoute(intent.formulaID))

        NavigateIntent.OpenHistoryScreen ->
            navController.navigate(HistoryRoute)

        is NavigateIntent.OpenFormulaScreenHistory ->
            navController.navigate(FormulaRoute(intent.formulaID, intent.historyID))

        is NavigateIntent.OpenFormulaEditScreen ->
            navController.navigate(FormulaEditRoute(intent.formulaID))

        NavigateIntent.OpenExportScreen ->
            navController.navigate(ExportRoute)

        NavigateIntent.OpenImportScreen ->
            navController.navigate(ImportRoute)
    }
}