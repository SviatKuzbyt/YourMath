package ua.sviatkuzbyt.yourmath.app.presenter.screens.history

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import kotlinx.coroutines.flow.collectLatest
import ua.sviatkuzbyt.yourmath.app.R
import ua.sviatkuzbyt.yourmath.app.presenter.controllers.history.HistoryIntent
import ua.sviatkuzbyt.yourmath.app.presenter.controllers.history.HistoryState
import ua.sviatkuzbyt.yourmath.app.presenter.navigation.LocalNavController
import ua.sviatkuzbyt.yourmath.app.presenter.navigation.NavigateIntent
import ua.sviatkuzbyt.yourmath.app.presenter.navigation.onNavigateIntent
import ua.sviatkuzbyt.yourmath.app.presenter.other.GlobalEvent
import ua.sviatkuzbyt.yourmath.app.presenter.other.GlobalEventType
import ua.sviatkuzbyt.yourmath.app.presenter.other.HistoryItem
import ua.sviatkuzbyt.yourmath.app.presenter.ui.elements.basic.AnimateListItem
import ua.sviatkuzbyt.yourmath.app.presenter.ui.elements.basic.EmptyScreenInList
import ua.sviatkuzbyt.yourmath.app.presenter.ui.elements.basic.ScreenTopBar
import ua.sviatkuzbyt.yourmath.app.presenter.ui.elements.basic.dialog.ShowDialogError
import ua.sviatkuzbyt.yourmath.app.presenter.ui.elements.basic.text.SubTittleText
import ua.sviatkuzbyt.yourmath.app.presenter.ui.elements.basic.text.TittleText
import ua.sviatkuzbyt.yourmath.app.presenter.ui.elements.history.ClearButton
import ua.sviatkuzbyt.yourmath.app.presenter.ui.elements.history.ClearDialog
import ua.sviatkuzbyt.yourmath.app.presenter.ui.elements.history.FilterButton
import ua.sviatkuzbyt.yourmath.app.presenter.ui.elements.history.HistoryContainer
import ua.sviatkuzbyt.yourmath.app.presenter.ui.elements.history.LoadMoreButton
import ua.sviatkuzbyt.yourmath.app.presenter.ui.theme.AppSizes

@Composable
fun HistoryScreen(viewModel: HistoryViewModel = hiltViewModel()){
    val screenState by viewModel.screenState.collectAsState()
    val navController = LocalNavController.current

    HistoryContent(
        screenState,
        viewModel::onIntent,
        onNavigate = { onNavigateIntent(navController, it) }
    )
}



@Composable
fun HistoryContent(
    screenState: HistoryState,
    onIntent: (HistoryIntent) -> Unit,
    onNavigate: (NavigateIntent) -> Unit
){
    val listState = rememberLazyListState()

    ObserveHistoryChange{ onIntent(HistoryIntent.ReloadItems) }

    Column(modifier = Modifier.fillMaxSize()) {
        TopBar(
            onBack = {onNavigate(NavigateIntent.NavigateBack)},
            onClear = {onIntent(HistoryIntent.SetCleanDialog(true))},
            onFilter = {},
            listState = listState
        )

        HistoryList(
            listState,
            data = screenState.items,
            onLoadMore = { onIntent(HistoryIntent.LoadNewItems) },
            showLoadMoreButton = !screenState.allDataIsLoaded,
            onOpenFormula = { formulaID, historyID ->
                onNavigate(NavigateIntent.OpenFormulaScreenHistory(formulaID, historyID))
            },
            isRecords = screenState.isRecords
        )
    }

    if (screenState.showCleanDialog){
        ClearDialog(
            onClose = {
                onIntent(HistoryIntent.SetCleanDialog(false))
            },
            onClear = {
                onIntent(HistoryIntent.CleanHistory)
            }
        )
    }

    ShowDialogError(screenState.errorMessage) {
        onIntent(HistoryIntent.CloseErrorDialog)
    }
}

@Composable
fun ObserveHistoryChange(
    onReload: () -> Unit
){
    LaunchedEffect(Unit) {
        GlobalEvent.event.collectLatest { event ->
            if (event == GlobalEventType.AddHistoryRecord){
                onReload()

                GlobalEvent.clearEvent()
            }
        }
    }
}

@Composable
private fun TopBar(
    onBack: () -> Unit,
    onClear: () -> Unit,
    onFilter: () -> Unit,
    listState: LazyListState
) {
    ScreenTopBar(
        tittle = stringResource(R.string.history),
        listState = listState,
        onBack = onBack,
        toolButtons = {
            ClearButton(onClear)
            FilterButton(onFilter)
        }
    )
}

@Composable
private fun HistoryList(
    listState: LazyListState,
    data: List<HistoryItem>,
    showLoadMoreButton: Boolean,
    onLoadMore: () -> Unit,
    onOpenFormula: (Long, Long) -> Unit,
    isRecords: Boolean
){
    LazyColumn(
        state = listState,
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(horizontal = AppSizes.dp16)
    ) {
        item {
            TittleText(
                textRes = R.string.history,
                modifier = Modifier.padding(bottom = AppSizes.dp8)
            )
        }

        if (isRecords){
            items(
                items = data,
                key = {
                    when(it){
                        is HistoryItem.Date -> "d${it.id}"
                        is HistoryItem.Formula -> "f${it.historyID}"
                    }
                }) { historyItem ->
                AnimateListItem {
                    when (historyItem) {
                        is HistoryItem.Date -> {
                            SubTittleText(text = historyItem.dateStr)
                        }

                        is HistoryItem.Formula -> {
                            HistoryContainer(
                                formulaName = historyItem.name,
                                formulaData = historyItem.inputOutputData,
                                onClick = {
                                    onOpenFormula(historyItem.formulaID, historyItem.historyID)
                                }
                            )
                        }
                    }
                }
            }

            item {
                AnimateListItem {
                    if (showLoadMoreButton) {
                        LoadMoreButton(onLoadMore)
                    }
                }
            }
        } else {
            item {
                AnimateListItem {
                    EmptyScreenInList(
                        textRes = R.string.no_history,
                        iconRes = R.drawable.ic_no_history,
                        modifier = Modifier.fillParentMaxHeight()
                    )
                }
            }
        }
    }
}