package ua.sviatkuzbyt.yourmath.app.presenter.screens.editformula

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.ScrollableTabRow
import androidx.compose.material3.Tab
import androidx.compose.material3.Text
import androidx.compose.material3.ripple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.graphics.drawscope.DrawStyle
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import ua.sviatkuzbyt.yourmath.app.R
import ua.sviatkuzbyt.yourmath.app.presenter.controllers.editformula.EditFormulaIntent
import ua.sviatkuzbyt.yourmath.app.presenter.controllers.editformula.EditFormulaState
import ua.sviatkuzbyt.yourmath.app.presenter.ui.elements.basic.ScreenTopBar
import ua.sviatkuzbyt.yourmath.app.presenter.ui.elements.basic.text.TittleText
import ua.sviatkuzbyt.yourmath.app.presenter.ui.elements.editformula.TabItem
import ua.sviatkuzbyt.yourmath.app.presenter.ui.theme.AppSizes
import ua.sviatkuzbyt.yourmath.app.presenter.ui.theme.AppTheme

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
                    padding = PaddingValues(
                        start = AppSizes.dp16,
                        end = AppSizes.dp16,
                        bottom = AppSizes.dp16
                    )
                )
            }

            stickyHeader {
                ScreenTabs(
                    tabs = screenState.tabs,
                    selectedTab = screenState.selectedTab,
                    onSelectTab = { index -> onIntent(EditFormulaIntent.SelectTab(index)) }
                )
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

@Composable
private fun ScreenTabs(
    tabs: List<Int>,
    selectedTab: Int,
    onSelectTab: (Int) -> Unit
){
    ScrollableTabRow(
        selectedTabIndex = selectedTab,
        modifier = Modifier.fillMaxWidth(),
        edgePadding = AppSizes.dp12,
        containerColor = AppTheme.colors.background,
        divider = {},
        indicator = {}
    ) {
        tabs.forEachIndexed { index, tab ->
            TabItem(
                selected = selectedTab == index,
                text = stringResource(tab),
                onClick = { onSelectTab(index) }
            )
        }
    }
}