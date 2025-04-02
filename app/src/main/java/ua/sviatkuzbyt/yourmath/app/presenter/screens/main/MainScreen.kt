package ua.sviatkuzbyt.yourmath.app.presenter.screens.main

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
import ua.sviatkuzbyt.yourmath.app.presenter.controllers.main.MainContent
import ua.sviatkuzbyt.yourmath.app.presenter.controllers.main.MainIntent
import ua.sviatkuzbyt.yourmath.app.presenter.controllers.main.MainState
import ua.sviatkuzbyt.yourmath.app.presenter.ui.elements.basic.AnimateListItem
import ua.sviatkuzbyt.yourmath.app.presenter.ui.elements.basic.dialog.DialogError
import ua.sviatkuzbyt.yourmath.app.presenter.ui.elements.basic.EmptyScreenInList
import ua.sviatkuzbyt.yourmath.app.presenter.ui.elements.basic.SubTittleText
import ua.sviatkuzbyt.yourmath.app.presenter.ui.elements.home.FieldSearch
import ua.sviatkuzbyt.yourmath.app.presenter.ui.elements.home.FormulaNoPinItemList
import ua.sviatkuzbyt.yourmath.app.presenter.ui.elements.home.FormulaPinnedItemList
import ua.sviatkuzbyt.yourmath.app.presenter.ui.elements.home.HomeTopBar

@Composable
fun MainScreen(viewModel: MainViewModel = hiltViewModel()){
    val screenState by viewModel.screenState.collectAsState()
    MainContent(
        screenState = screenState,
        onIntent = { intent ->
            viewModel.onIntent(intent)
        }
    )
}

@Composable
fun MainContent(
    screenState: MainState,
    onIntent: (MainIntent) -> Unit
){
    Column(modifier = Modifier.fillMaxSize().padding()) {
        HomeTopBar(
            historyOnClick = {},
            editOnClick = {}
        )

        FieldSearch(screenState.searchText) { newText ->
            onIntent(MainIntent.ChangeSearchText(newText))
        }

        when(screenState.content){
            MainContent.Empty -> {}
            is MainContent.Formulas -> {
                LazyColumn(Modifier.fillMaxSize()) {
                    if (screenState.content.lists.pins.isNotEmpty()) {
                        item {
                            SubTittleText(R.string.pinned)
                        }

                        items(
                            items = screenState.content.lists.pins,
                            key = { formula -> formula.id }
                        ) { formula ->
                            AnimateListItem {
                                FormulaPinnedItemList(
                                    text = formula.name,
                                    onClick = { },
                                    unpinOnClick = {
                                        onIntent(MainIntent.UnPinFormula(formula))
                                    }
                                )
                            }
                        }
                    }

                    if (screenState.content.lists.unpins.isNotEmpty()) {
                        if (screenState.content.lists.pins.isNotEmpty()) {
                            item {
                                AnimateListItem {
                                    SubTittleText(R.string.other)
                                }
                            }
                        }
                        items(
                            items = screenState.content.lists.unpins,
                            key = { formula -> formula.id }
                        ) { formula ->
                            AnimateListItem {
                                FormulaNoPinItemList(
                                    text = formula.name,
                                    onClick = { },
                                    pinOnClick = {
                                        onIntent(MainIntent.PinFormula(formula))
                                    }
                                )
                            }
                        }
                    }
                }
            }
            MainContent.NoFormulas -> {
                EmptyScreenInList(
                    textRes = R.string.no_formulas,
                    iconRes = R.drawable.ic_no_formulas
                )
            }
            MainContent.NoSearchResult -> {
                EmptyScreenInList(
                    textRes = R.string.no_search_result,
                    iconRes = R.drawable.ic_no_results
                )
            }
        }

        if (screenState.errorMessage != null) {
            DialogError(screenState.errorMessage) {
                onIntent(MainIntent.CloseDialog)
            }
        }
    }
}