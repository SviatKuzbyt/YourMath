package ua.sviatkuzbyt.yourmath.app.presenter.screens.history

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import ua.sviatkuzbyt.yourmath.app.R
import ua.sviatkuzbyt.yourmath.app.presenter.controllers.history.HistoryIntent
import ua.sviatkuzbyt.yourmath.app.presenter.controllers.history.HistoryState
import ua.sviatkuzbyt.yourmath.app.presenter.other.HistoryItem
import ua.sviatkuzbyt.yourmath.app.presenter.ui.elements.basic.ScreenTopBar
import ua.sviatkuzbyt.yourmath.app.presenter.ui.elements.basic.text.TittleText
import ua.sviatkuzbyt.yourmath.app.presenter.ui.elements.history.ClearButton
import ua.sviatkuzbyt.yourmath.app.presenter.ui.elements.history.FilterButton
import ua.sviatkuzbyt.yourmath.app.presenter.ui.theme.AppSizes
import ua.sviatkuzbyt.yourmath.app.presenter.ui.theme.AppTheme

@Composable
fun HistoryScreen(viewModel: HistoryViewModel = hiltViewModel()){
    val screenState by viewModel.screenState.collectAsState()
    HistoryContent(screenState, viewModel::onIntent)
}

@Composable
fun HistoryContent(
    screenState: HistoryState,
    onIntent: (HistoryIntent) -> Unit
){
    val listState = rememberLazyListState()

    Column(modifier = Modifier.fillMaxSize()) {
        TopBar(
            onBack = {},
            onClear = {},
            onFilter = {},
            listState = listState
        )

        HistoryList(
            listState,
            list = screenState.items,
            onLoadMore = { onIntent(HistoryIntent.LoadNewItems) },
            showLoadMoreButton = !screenState.allDataIsLoaded
        )
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
    list: List<HistoryItem>,
    onLoadMore: () -> Unit,
    showLoadMoreButton: Boolean
){
    LazyColumn(
        state = listState,
        modifier = Modifier.fillMaxSize()
    ){
        item {
            TittleText(R.string.history)
        }

        items(list){
            when(it){
                is HistoryItem.Date -> Text(
                    text = it.string,
                    style = AppTheme.types.bold,
                    modifier = Modifier.padding(AppSizes.dp16)
                )
                is HistoryItem.Formula -> Text(
                    text = it.toString(),
                    style = AppTheme.types.basic,
                    modifier = Modifier.padding(AppSizes.dp16)
                )
            }
        }

        if (showLoadMoreButton){
            item {
                Button(
                    onClick = onLoadMore
                ) {
                    Text("Load more")
                }
            }
        }
    }
}