package ua.sviatkuzbyt.yourmath.app.presenter.screens.main

import androidx.compose.animation.Crossfade
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import ua.sviatkuzbyt.yourmath.app.R
import ua.sviatkuzbyt.yourmath.app.presenter.controllers.main.MainIntent
import ua.sviatkuzbyt.yourmath.app.presenter.controllers.main.MainState
import ua.sviatkuzbyt.yourmath.app.presenter.controllers.main.ShowOnScreen
import ua.sviatkuzbyt.yourmath.app.presenter.navigation.LocalNavController
import ua.sviatkuzbyt.yourmath.app.presenter.navigation.NavigateIntent
import ua.sviatkuzbyt.yourmath.app.presenter.navigation.onNavigateIntent
import ua.sviatkuzbyt.yourmath.app.presenter.ui.elements.basic.AnimateListItem
import ua.sviatkuzbyt.yourmath.app.presenter.ui.elements.basic.dialog.DialogError
import ua.sviatkuzbyt.yourmath.app.presenter.ui.elements.basic.EmptyScreenInList
import ua.sviatkuzbyt.yourmath.app.presenter.ui.elements.basic.SubTittleText
import ua.sviatkuzbyt.yourmath.app.presenter.ui.elements.home.FieldSearch
import ua.sviatkuzbyt.yourmath.app.presenter.ui.elements.home.FormulaNoPinItemList
import ua.sviatkuzbyt.yourmath.app.presenter.ui.elements.home.FormulaPinnedItemList
import ua.sviatkuzbyt.yourmath.app.presenter.ui.elements.home.HomeTopBar
import ua.sviatkuzbyt.yourmath.app.presenter.ui.theme.AppSizes
import ua.sviatkuzbyt.yourmath.domain.structures.main.PinUnpinFormulaItems

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
            historyOnClick = {
                onNavigate(NavigateIntent.OpenHistoryScreen)
            },
            editOnClick = {
                onNavigate(NavigateIntent.OpenEditorScreen)
            }
        )

        FieldSearch(screenState.searchText) { newText ->
            onIntent(MainIntent.ChangeSearchText(newText))
        }

        //Main content, formulas
        Crossfade(targetState = screenState.showOnScreen) { showOnScreen ->
            when (showOnScreen) {
                ShowOnScreen.Formulas -> FormulasList(
                    lists = screenState.formulas,
                    onIntent = onIntent,
                    onNavigate = onNavigate
                )
                ShowOnScreen.NoFormulas -> EmptyScreenInList(
                    textRes = R.string.no_formulas,
                    iconRes = R.drawable.ic_no_formulas
                )
                ShowOnScreen.NoSearchResult -> EmptyScreenInList(
                    textRes = R.string.no_search_result,
                    iconRes = R.drawable.ic_no_results
                )
                ShowOnScreen.Nothing -> {}
            }
        }

        screenState.errorMessage?.let { error ->
            DialogError(error) {
                onIntent(MainIntent.CloseDialog)
            }
        }
    }
}

@Composable
private fun FormulasList(
    lists: PinUnpinFormulaItems,
    onIntent: (MainIntent) -> Unit,
    onNavigate: (NavigateIntent) -> Unit
) {
    LazyColumn(Modifier.fillMaxSize()) {
        //Pinned formulas
        if (lists.pins.isNotEmpty()) {
            item {
                SubTittleText(R.string.pinned) }
            items(lists.pins, key = { it.id }) { formula ->
                AnimateListItem {
                    FormulaPinnedItemList(
                        text = formula.name,
                        onClick = {
                            onNavigate(NavigateIntent.OpenFormulaScreen(formulaID = formula.id))
                        },
                        unpinOnClick = {
                            onIntent(MainIntent.UnPinFormula(formula))
                        }
                    )
                }
            }
        }

        //Other formulas
        if (lists.unpins.isNotEmpty()) {
            if (lists.pins.isNotEmpty()) {
                item { AnimateListItem { SubTittleText(R.string.other) } }
            }
            items(lists.unpins, key = { it.id }) { formula ->
                AnimateListItem {
                    FormulaNoPinItemList(
                        text = formula.name,
                        onClick = {
                            onNavigate(NavigateIntent.OpenFormulaScreen(formulaID = formula.id))
                        },
                        pinOnClick = {
                            onIntent(MainIntent.PinFormula(formula))
                        }
                    )
                }
            }
        }
    }
}