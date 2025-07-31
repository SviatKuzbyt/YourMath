package ua.sviatkuzbyt.yourmath.app.presenter.screens.main

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import kotlinx.coroutines.flow.collectLatest
import ua.sviatkuzbyt.yourmath.app.presenter.controllers.main.MainIntent
import ua.sviatkuzbyt.yourmath.app.presenter.controllers.main.MainState
import ua.sviatkuzbyt.yourmath.app.presenter.navigation.LocalNavController
import ua.sviatkuzbyt.yourmath.app.presenter.navigation.NavigateIntent
import ua.sviatkuzbyt.yourmath.app.presenter.navigation.onNavigateIntent
import ua.sviatkuzbyt.yourmath.app.presenter.other.basic.GlobalEvent
import ua.sviatkuzbyt.yourmath.app.presenter.other.basic.GlobalEventType
import ua.sviatkuzbyt.yourmath.app.presenter.ui.elements.basic.dialog.ShowDialogError
import ua.sviatkuzbyt.yourmath.app.presenter.ui.elements.main.FieldSearch
import ua.sviatkuzbyt.yourmath.app.presenter.ui.elements.main.HomeTopBar
import ua.sviatkuzbyt.yourmath.app.presenter.ui.elements.main.MainListContent

@Composable
fun MainScreen(viewModel: MainViewModel = hiltViewModel()) {
    val screenState by viewModel.screenState.collectAsState()
    val navController = LocalNavController.current
    MainContent(
        screenState = screenState,
        onIntent = viewModel::onIntent,
        onNavigate = { onNavigateIntent(navController, it) }
    )
}

@Composable
fun MainContent(
    screenState: MainState,
    onIntent: (MainIntent) -> Unit,
    onNavigate: (NavigateIntent) -> Unit
) {
    Column(modifier = Modifier.fillMaxSize()) {
        //TopBar and search field
        HomeTopBar(
            historyOnClick = { onNavigate(NavigateIntent.OpenHistoryScreen) },
            editOnClick = { onNavigate(NavigateIntent.OpenEditorScreen) }
        )

        FieldSearch(screenState.searchText) { newText ->
            onIntent(MainIntent.ChangeSearchText(newText))
        }

        //Main content, formulas
        MainListContent(
            listContent = screenState.listContent,
            onIntent = onIntent,
            onNavigate = onNavigate
        )
    }

    ShowDialogError(
        errorData = screenState.errorMessage,
        onCloseClick = { onIntent(MainIntent.CloseDialog) }
    )
}