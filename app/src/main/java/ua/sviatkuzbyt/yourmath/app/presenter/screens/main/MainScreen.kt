package ua.sviatkuzbyt.yourmath.app.presenter.screens.main

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import ua.sviatkuzbyt.yourmath.app.R
import ua.sviatkuzbyt.yourmath.app.presenter.controllers.main.MainIntent
import ua.sviatkuzbyt.yourmath.app.presenter.controllers.main.MainListContent
import ua.sviatkuzbyt.yourmath.app.presenter.controllers.main.MainState
import ua.sviatkuzbyt.yourmath.app.presenter.navigation.LocalNavController
import ua.sviatkuzbyt.yourmath.app.presenter.navigation.NavigateIntent
import ua.sviatkuzbyt.yourmath.app.presenter.navigation.onNavigateIntent
import ua.sviatkuzbyt.yourmath.app.presenter.ui.elements.basic.AnimateListItem
import ua.sviatkuzbyt.yourmath.app.presenter.ui.elements.basic.EmptyScreenInList
import ua.sviatkuzbyt.yourmath.app.presenter.ui.elements.basic.text.SubTittleText
import ua.sviatkuzbyt.yourmath.app.presenter.ui.elements.basic.dialog.ShowDialogError
import ua.sviatkuzbyt.yourmath.app.presenter.ui.elements.main.FieldSearch
import ua.sviatkuzbyt.yourmath.app.presenter.ui.elements.main.FormulaNoPinItemList
import ua.sviatkuzbyt.yourmath.app.presenter.ui.elements.main.FormulaPinnedItemList
import ua.sviatkuzbyt.yourmath.app.presenter.ui.elements.main.HomeTopBar
import ua.sviatkuzbyt.yourmath.app.presenter.ui.theme.AppSizes

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
        ListContent(
            listContent = screenState.listContent,
            onIntent = onIntent,
            onNavigate = onNavigate
        )

        ShowDialogError(
            errorData = screenState.errorMessage,
            onCloseClick = {
                onIntent(MainIntent.CloseDialog)
            }
        )
    }
}

@Composable
private fun ListContent(
    listContent: MainListContent,
    onIntent: (MainIntent) -> Unit,
    onNavigate: (NavigateIntent) -> Unit
) {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(horizontal = AppSizes.dp16)
    ) {
        when(listContent){
            is MainListContent.FormulaList -> {
                if (listContent.formulas.pins.isNotEmpty()) {
                    item (key = "s1") {
                        AnimateListItem {
                            SubTittleText(R.string.pinned)
                        }
                    }
                    items(listContent.formulas.pins, key = { it.id }) { formula ->
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
                if (listContent.formulas.unpins.isNotEmpty()) {
                    if (listContent.formulas.pins.isNotEmpty()) {
                        item(key = "s2") { AnimateListItem { SubTittleText(R.string.other) } }
                    }
                    items(listContent.formulas.unpins, key = { it.id }) { formula ->
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
            is MainListContent.EmptyScreen -> item(key = "e0"){
                AnimateListItem {
                    EmptyScreenInList(listContent.info)
                }
            }
            MainListContent.Nothing -> {}
        }
    }
}