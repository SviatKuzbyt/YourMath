package ua.sviatkuzbyt.yourmath.app.presenter.ui.elements.history.screen

import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import ua.sviatkuzbyt.yourmath.app.R
import ua.sviatkuzbyt.yourmath.app.presenter.ui.elements.basic.ScreenTopBar
import ua.sviatkuzbyt.yourmath.app.presenter.ui.elements.basic.button.ButtonIconTopBar

@Composable
fun HistoryTopBar(
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
private fun ClearButton(
    onClick: () -> Unit
) {
    ButtonIconTopBar(
        imageRes = R.drawable.btn_clear,
        contentDescriptionRes = R.string.clear_history,
        onClick = onClick
    )
}

@Composable
private fun FilterButton(
    onClick: () -> Unit
) {
    ButtonIconTopBar(
        imageRes = R.drawable.btn_filter,
        contentDescriptionRes = R.string.open_filters,
        onClick = onClick
    )
}