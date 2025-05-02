package ua.sviatkuzbyt.yourmath.app.presenter.ui.elements.history

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyItemScope
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import ua.sviatkuzbyt.yourmath.app.R
import ua.sviatkuzbyt.yourmath.app.presenter.controllers.history.HistoryListContent
import ua.sviatkuzbyt.yourmath.app.presenter.other.history.HistoryItem
import ua.sviatkuzbyt.yourmath.app.presenter.ui.elements.basic.AnimateListItem
import ua.sviatkuzbyt.yourmath.app.presenter.ui.elements.basic.EmptyScreenInListFullSize
import ua.sviatkuzbyt.yourmath.app.presenter.ui.elements.basic.text.SubTittleText
import ua.sviatkuzbyt.yourmath.app.presenter.ui.elements.basic.text.TittleText
import ua.sviatkuzbyt.yourmath.app.presenter.ui.theme.AppSizes

@Composable
fun HistoryContentList(
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
                    EmptyScreenInListFullSize(content.info)
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

@Composable
private fun LazyItemScope.ItemList(
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

private fun getItemKey(item: HistoryItem) = when(item){
    is HistoryItem.Date -> "d${item.dateLong}"
    is HistoryItem.Formula -> "f${item.historyID}"
}