@file:OptIn(ExperimentalMaterial3Api::class)

package ua.sviatkuzbyt.yourmath.app.presenter.screens.history
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyItemScope
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import ua.sviatkuzbyt.yourmath.app.R
import ua.sviatkuzbyt.yourmath.app.presenter.controllers.history.HistoryAboveScreenContent
import ua.sviatkuzbyt.yourmath.app.presenter.controllers.history.HistoryIntent
import ua.sviatkuzbyt.yourmath.app.presenter.controllers.history.HistoryListContent
import ua.sviatkuzbyt.yourmath.app.presenter.controllers.history.HistoryState
import ua.sviatkuzbyt.yourmath.app.presenter.navigation.LocalNavController
import ua.sviatkuzbyt.yourmath.app.presenter.navigation.NavigateIntent
import ua.sviatkuzbyt.yourmath.app.presenter.navigation.onNavigateIntent
import ua.sviatkuzbyt.yourmath.app.presenter.other.basic.GlobalEvent
import ua.sviatkuzbyt.yourmath.app.presenter.other.basic.GlobalEventType
import ua.sviatkuzbyt.yourmath.app.presenter.ui.elements.basic.AnimateListItem
import ua.sviatkuzbyt.yourmath.app.presenter.ui.elements.basic.EmptyScreenInList
import ua.sviatkuzbyt.yourmath.app.presenter.ui.elements.basic.ScreenTopBar
import ua.sviatkuzbyt.yourmath.app.presenter.ui.elements.basic.dialog.DialogError
import ua.sviatkuzbyt.yourmath.app.presenter.ui.elements.basic.text.SubTittleText
import ua.sviatkuzbyt.yourmath.app.presenter.ui.elements.basic.text.TittleText
import ua.sviatkuzbyt.yourmath.app.presenter.ui.elements.history.ClearButton
import ua.sviatkuzbyt.yourmath.app.presenter.ui.elements.history.ClearDialog
import ua.sviatkuzbyt.yourmath.app.presenter.ui.elements.history.FilterButton
import ua.sviatkuzbyt.yourmath.app.presenter.ui.elements.history.FilterSheet
import ua.sviatkuzbyt.yourmath.app.presenter.ui.elements.history.HistoryContainer
import ua.sviatkuzbyt.yourmath.app.presenter.ui.elements.history.LoadMoreButton
import ua.sviatkuzbyt.yourmath.app.presenter.ui.theme.AppSizes
import ua.sviatkuzbyt.yourmath.app.presenter.other.history.HistoryItem

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
            onBack = { onNavigate(NavigateIntent.NavigateBack) },
            onClear = { onIntent(HistoryIntent.OpenCleanDialog) },
            onFilter = { onIntent(HistoryIntent.OpenFilters) },
            listState = listState
        )

        ContentList(
            listState = listState,
            content = screenState.listContent,
            onLoadMore = { onIntent(HistoryIntent.LoadNewItems) },
            onOpenFormula = { formulaID, historyID ->
                onNavigate(NavigateIntent.OpenFormulaScreenHistory(formulaID, historyID))
            }
        )
    }

    AboveScreenContent(
        content = screenState.aboveScreenContent,
        onClear = { onIntent(HistoryIntent.CleanHistory) },
        onClose = { onIntent(HistoryIntent.CloseAboveScreenContentDialog) },
        onSelectFilter = { formulaID -> onIntent(HistoryIntent.SelectFilter(formulaID)) }
    )
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
private fun ContentList(
    listState: LazyListState,
    content: HistoryListContent,
    onLoadMore: () -> Unit,
    onOpenFormula: (Long, Long) -> Unit,
){
    LazyColumn(
        state = listState,
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(horizontal = AppSizes.dp16)
    ) {
        item {
            TittleText(R.string.history)
        }

        when(content){
            is HistoryListContent.EmptyScreen -> item {
                AnimateListItem {
                    EmptyScreenInList(content.info)
                }
            }
            is HistoryListContent.Items -> {
                items(
                    items = content.list,
                    key = { historyItem -> getItemKey(historyItem) },
                    itemContent = { historyItem -> ItemList(historyItem, onOpenFormula) }
                )

                item {
                    AnimateListItem {
                        if (content.isLoadMore) { LoadMoreButton(onLoadMore) }
                    }
                }
            }
            HistoryListContent.Nothing -> {}
        }
    }
}

private fun getItemKey(item: HistoryItem) = when(item){
    is HistoryItem.Date -> "d${item.dateLong}"
    is HistoryItem.Formula -> "f${item.historyID}"
}

@Composable
fun LazyItemScope.ItemList(
    item: HistoryItem,
    onClick: (Long, Long) -> Unit
){
    AnimateListItem {
        when (item) {
            is HistoryItem.Date -> SubTittleText(text = item.dateStr)

            is HistoryItem.Formula -> HistoryContainer(
                formulaName = item.name,
                formulaData = item.inputOutputData,
                onClick = { onClick(item.formulaID, item.historyID) }
            )
        }
    }
}

@Composable
private fun AboveScreenContent(
    content: HistoryAboveScreenContent,
    onClear: () -> Unit,
    onClose: () -> Unit,
    onSelectFilter: (Long) -> Unit
){
    when(content){
        HistoryAboveScreenContent.CleanDialog -> ClearDialog(
            onClose = onClose,
            onClear = onClear
        )
        is HistoryAboveScreenContent.ErrorDialog -> DialogError(
            data = content.data,
            onCloseClick = onClose,
        )
        is HistoryAboveScreenContent.FilterSheet -> FilterSheet(
            filterList = content.list,
            onSelect = { formulaID ->
                onSelectFilter(formulaID)
            },
            onClose = onClose
        )
        HistoryAboveScreenContent.Nothing -> {}
    }
}