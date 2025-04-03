package ua.sviatkuzbyt.yourmath.app.presenter.screens.formula

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import ua.sviatkuzbyt.yourmath.app.presenter.controllers.formula.FormulaState
import ua.sviatkuzbyt.yourmath.app.presenter.ui.elements.basic.ScreenTopBar
import ua.sviatkuzbyt.yourmath.app.presenter.ui.theme.AppSizes
import ua.sviatkuzbyt.yourmath.app.presenter.ui.theme.AppTheme

@Composable
fun FormulaScreen(viewModel: FormulaViewModel) {
    val screenState by viewModel.screenState.collectAsState()
    FormulaContent(screenState)
}

@Composable
fun FormulaContent(
    screenState: FormulaState
){
    val listState = rememberLazyListState()

    Column(modifier = Modifier.fillMaxSize()) {
        ScreenTopBar(
            tittle = screenState.content.info.label,
            listState = listState,
            onBack = {}
        )

        LazyColumn(
            state = listState,
            modifier = Modifier.fillMaxSize().padding()
        ) {
            item {
                Text(
                    text = screenState.content.info.label,
                    style = AppTheme.types.tittle,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = AppSizes.dp16)
                        .padding(bottom = AppSizes.dp16)
                )
            }

            screenState.content.info.description?.let { description ->

                item {
                    Text(
                        text = description,
                        style = AppTheme.types.secondary,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = AppSizes.dp16)
                            .padding(bottom = AppSizes.dp16)
                    )
                }

            }

        }
    }
}
