package ua.sviatkuzbyt.yourmath.app.presenter.screens.formula

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import ua.sviatkuzbyt.yourmath.app.presenter.ui.elements.basic.ScreenTopBar

@Composable
fun FormulaScreen() {
    val listState = rememberLazyListState()

    Column(modifier = Modifier.fillMaxSize()) {

        ScreenTopBar(
            tittle = "",
            listState = listState,
            onBack = {}
        )
        LazyColumn(state = listState) {

        }
    }
}
