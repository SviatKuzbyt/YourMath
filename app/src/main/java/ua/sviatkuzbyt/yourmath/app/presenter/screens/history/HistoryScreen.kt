package ua.sviatkuzbyt.yourmath.app.presenter.screens.history
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collectLatest
import ua.sviatkuzbyt.yourmath.app.presenter.controllers.history.HistoryAboveScreenContent
import ua.sviatkuzbyt.yourmath.app.presenter.controllers.history.HistoryIntent
import ua.sviatkuzbyt.yourmath.app.presenter.controllers.history.HistoryState
import ua.sviatkuzbyt.yourmath.app.presenter.navigation.LocalNavController
import ua.sviatkuzbyt.yourmath.app.presenter.navigation.NavigateIntent
import ua.sviatkuzbyt.yourmath.app.presenter.navigation.onNavigateIntent
import ua.sviatkuzbyt.yourmath.app.presenter.ui.elements.basic.dialog.DialogError
import ua.sviatkuzbyt.yourmath.app.presenter.ui.elements.history.above.ClearDialog
import ua.sviatkuzbyt.yourmath.app.presenter.ui.elements.history.above.FilterSheet
import ua.sviatkuzbyt.yourmath.app.presenter.ui.elements.history.screen.HistoryContentList
import ua.sviatkuzbyt.yourmath.app.presenter.ui.elements.history.screen.HistoryTopBar

val isHistoryUpdate = MutableStateFlow(false)

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
        HistoryTopBar(
            onBack = { onNavigate(NavigateIntent.NavigateBack) },
            onClear = { onIntent(HistoryIntent.OpenCleanDialog) },
            onFilter = { onIntent(HistoryIntent.OpenFilters) },
            listState = listState
        )

        HistoryContentList(
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
fun ObserveHistoryChange(onReload: () -> Unit){
    LaunchedEffect(Unit) {
        isHistoryUpdate.collectLatest { isUpdate ->
            if (isUpdate){
                onReload()
                isHistoryUpdate.value = false
            }
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