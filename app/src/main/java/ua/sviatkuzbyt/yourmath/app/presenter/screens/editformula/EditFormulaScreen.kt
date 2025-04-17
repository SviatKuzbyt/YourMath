package ua.sviatkuzbyt.yourmath.app.presenter.screens.editformula

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.ScrollableTabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import ua.sviatkuzbyt.yourmath.app.R
import ua.sviatkuzbyt.yourmath.app.presenter.controllers.editformula.EditFormulaIntent
import ua.sviatkuzbyt.yourmath.app.presenter.controllers.editformula.EditFormulaState
import ua.sviatkuzbyt.yourmath.app.presenter.controllers.editformula.EditFormulaStateContent
import ua.sviatkuzbyt.yourmath.app.presenter.screens.editformula.tabs.InfoTab
import ua.sviatkuzbyt.yourmath.app.presenter.ui.elements.basic.ScreenTopBar
import ua.sviatkuzbyt.yourmath.app.presenter.ui.elements.basic.button.AddButton
import ua.sviatkuzbyt.yourmath.app.presenter.ui.elements.basic.button.ButtonLarge
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

        Box(Modifier.weight(1f)){
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                state = listState
            ) {
                item { Tittle() }

                stickyHeader {
                    ScreenTabs(
                        tabs = screenState.tabs,
                        selectedTab = screenState.selectedTab,
                        onSelectTab = { index -> onIntent(EditFormulaIntent.SelectTab(index)) }
                    )
                }

                when(screenState.content){
                    is EditFormulaStateContent.Info -> item {
                        InfoTab(
                            info = screenState.content,
                            onNameChange = { name ->
                                onIntent(EditFormulaIntent.ChangeName(name))
                            },
                            onDescriptionChange = { description ->
                                onIntent(EditFormulaIntent.ChangeDescription(description))
                            }
                        )
                    }
                    is EditFormulaStateContent.Inputs -> Unit
                    is EditFormulaStateContent.Code -> Unit
                    is EditFormulaStateContent.Results -> Unit
                    EditFormulaStateContent.Nothing -> Unit
                }
            }

            AddDataButton(
                isShow =
                    screenState.content is EditFormulaStateContent.Inputs ||
                    screenState.content is EditFormulaStateContent.Results,
                onClick = { onIntent(EditFormulaIntent.AddDataItem) }
            )
        }

        ButtonLarge(
            textRes = R.string.save_changes,
            modifier = Modifier.padding(AppSizes.dp16),
            onClick = { onIntent(EditFormulaIntent.SaveChanges) }
        )
    }
}

@Composable
private fun Tittle(){
    TittleText(
        textRes = R.string.edit_formula,
        padding = PaddingValues(
            start = AppSizes.dp16,
            end = AppSizes.dp16,
            bottom = AppSizes.dp16
        )
    )
}

@Composable
private fun BoxScope.AddDataButton(
    isShow: Boolean,
    onClick: () -> Unit
){
    AnimatedVisibility(
        visible = isShow,
        enter = fadeIn(),
        exit = fadeOut(),
        modifier = Modifier.align(Alignment.BottomEnd)
    ) {
        AddButton(
            descriptionRes = R.string.add_item,
            onClick = onClick
        )
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
        modifier = Modifier.fillMaxWidth().padding(bottom = AppSizes.dp16),
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