package ua.sviatkuzbyt.yourmath.app.presenter.screens.history

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import ua.sviatkuzbyt.yourmath.app.R
import ua.sviatkuzbyt.yourmath.app.presenter.ui.elements.basic.ScreenTopBar
import ua.sviatkuzbyt.yourmath.app.presenter.ui.elements.basic.text.TittleText
import ua.sviatkuzbyt.yourmath.app.presenter.ui.elements.formula.CopyButton
import ua.sviatkuzbyt.yourmath.app.presenter.ui.elements.history.ClearButton
import ua.sviatkuzbyt.yourmath.app.presenter.ui.elements.history.FilterButton

@Composable
fun HistoryScreen(){
    HistoryContent()
}

@Composable
fun HistoryContent(){
    val listState = rememberLazyListState()

    Column(modifier = Modifier.fillMaxSize()) {
        TopBar(
            onBack = {},
            onClear = {},
            onFilter = {},
            listState = listState
        )

        HistoryList(listState)
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
    listState: LazyListState
){
    LazyColumn(
        state = listState,
        modifier = Modifier.fillMaxSize()
    ){
        item {
            TittleText(R.string.history)
        }
    }
}