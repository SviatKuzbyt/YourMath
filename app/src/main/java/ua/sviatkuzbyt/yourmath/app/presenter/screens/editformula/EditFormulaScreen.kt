package ua.sviatkuzbyt.yourmath.app.presenter.screens.editformula

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.ScrollableTabRow
import androidx.compose.material3.Tab
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import ua.sviatkuzbyt.yourmath.app.R
import ua.sviatkuzbyt.yourmath.app.presenter.controllers.editformula.EditFormulaIntent
import ua.sviatkuzbyt.yourmath.app.presenter.controllers.editformula.EditFormulaState
import ua.sviatkuzbyt.yourmath.app.presenter.ui.elements.basic.ScreenTopBar
import ua.sviatkuzbyt.yourmath.app.presenter.ui.elements.basic.text.TittleText
import ua.sviatkuzbyt.yourmath.app.presenter.ui.theme.AppSizes

@Composable
fun EditFormulaScreen(viewModel: EditFormulaViewModel = hiltViewModel()){
    val screenState by viewModel.screenState.collectAsState()
    EditFormulaContent(
        screenState = screenState,
        onIntent = viewModel::onIntent
    )
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun EditFormulaContent(
    screenState: EditFormulaState,
    onIntent: (EditFormulaIntent) -> Unit
){
    val listState = rememberLazyListState()
    Column(Modifier.fillMaxSize()) {
        ScreenTopBar(
            tittle = stringResource(R.string.edit_formula),
            listState = listState,
            onBack = {}
        )

        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            state = listState
        ) {
            item {
                TittleText(
                    textRes = R.string.edit_formula,
                    padding = PaddingValues(start = AppSizes.dp16, end = AppSizes.dp16, bottom = AppSizes.dp16)
                )
            }

            stickyHeader {
                ScrollableTabRow(
                    selectedTabIndex = screenState.selectedTab,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    screenState.tabs.forEachIndexed { index, tab ->
                        Tab(
                            selected = screenState.selectedTab == index,
                            onClick = { onIntent(EditFormulaIntent.SelectTab(index)) },
                            text = { Text(stringResource(tab)) }
                        )
                    }
                }
            }

            item {
                Text(
                    text = screenState.content.toString(),
                    modifier = Modifier.height(1000.dp)
                )
            }
        }
    }
}