package ua.sviatkuzbyt.yourmath.app.presenter.screens.editformula

import androidx.activity.compose.BackHandler
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.ScrollableTabRow
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.compose.LocalLifecycleOwner
import kotlinx.coroutines.launch
import ua.sviatkuzbyt.yourmath.app.R
import ua.sviatkuzbyt.yourmath.app.presenter.controllers.editformula.EditFormulaIntent
import ua.sviatkuzbyt.yourmath.app.presenter.controllers.editformula.EditFormulaState
import ua.sviatkuzbyt.yourmath.app.presenter.controllers.editformula.EditFormulaStateContent
import ua.sviatkuzbyt.yourmath.app.presenter.controllers.editformula.EditList
import ua.sviatkuzbyt.yourmath.app.presenter.navigation.LocalNavController
import ua.sviatkuzbyt.yourmath.app.presenter.navigation.NavigateIntent
import ua.sviatkuzbyt.yourmath.app.presenter.navigation.onNavigateIntent
import ua.sviatkuzbyt.yourmath.app.presenter.other.basic.EmptyScreenInfo
import ua.sviatkuzbyt.yourmath.app.presenter.other.basic.showToast
import ua.sviatkuzbyt.yourmath.app.presenter.ui.elements.basic.EmptyScreenInListFullSize
import ua.sviatkuzbyt.yourmath.app.presenter.ui.elements.editformula.InfoItems
import ua.sviatkuzbyt.yourmath.app.presenter.ui.elements.basic.ScreenTopBar
import ua.sviatkuzbyt.yourmath.app.presenter.ui.elements.basic.button.AddButton
import ua.sviatkuzbyt.yourmath.app.presenter.ui.elements.basic.button.ButtonIconTopBar
import ua.sviatkuzbyt.yourmath.app.presenter.ui.elements.basic.emptySpaceOfButton
import ua.sviatkuzbyt.yourmath.app.presenter.ui.elements.basic.text.TittleText
import ua.sviatkuzbyt.yourmath.app.presenter.ui.elements.editformula.CodeItem
import ua.sviatkuzbyt.yourmath.app.presenter.ui.elements.editformula.InputItem
import ua.sviatkuzbyt.yourmath.app.presenter.ui.elements.editformula.ResultItem
import ua.sviatkuzbyt.yourmath.app.presenter.ui.elements.editformula.TabItem
import ua.sviatkuzbyt.yourmath.app.presenter.ui.theme.AppSizes
import ua.sviatkuzbyt.yourmath.app.presenter.ui.theme.AppTheme

@Composable
fun EditFormulaScreen(viewModel: EditFormulaViewModel = hiltViewModel()){
    val screenState by viewModel.screenState.collectAsState()
    val navController = LocalNavController.current
    EditFormulaContent(
        screenState = screenState,
        onIntent = viewModel::onIntent,
        onNavigate = { onNavigateIntent(navController, it) }
    )
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun EditFormulaContent(
    screenState: EditFormulaState,
    onIntent: (EditFormulaIntent) -> Unit,
    onNavigate: (NavigateIntent) -> Unit
){
    val listState = rememberLazyListState()
    val scope = rememberCoroutineScope()
    val context = LocalContext.current
    val focusManager = LocalFocusManager.current
    val lifecycleOwner = LocalLifecycleOwner.current

    if (screenState.isNavigateBack){
        onNavigate(NavigateIntent.NavigateBack)
    }

    DisposableEffect(lifecycleOwner) {
        val observer = LifecycleEventObserver { _, event ->
            if (event == Lifecycle.Event.ON_STOP) {
                focusManager.clearFocus(true)
            }
        }
        lifecycleOwner.lifecycle.addObserver(observer)

        onDispose {
            lifecycleOwner.lifecycle.removeObserver(observer)
        }
    }

    BackHandler {
        focusManager.clearFocus(true)
        onIntent(EditFormulaIntent.Exit)
    }

    Box(Modifier.fillMaxSize()){
        Column(Modifier.fillMaxSize()) {
            ScreenTopBar(
                tittle = stringResource(R.string.edit_formula),
                listState = listState,
                onBack = {
                    focusManager.clearFocus(true)
                    onIntent(EditFormulaIntent.Exit)
                },
                toolButtons = {
                    ButtonIconTopBar(
                        imageRes = R.drawable.ic_auto_save,
                        contentDescriptionRes = R.string.change_auto_save,
                        onClick = { showToast(R.string.change_auto_save, context) }
                    )
                }
            )

            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                state = listState
            ) {
                item { Tittle() }

                stickyHeader {
                    ScreenTabs(
                        tabs = screenState.tabs,
                        selectedTab = screenState.selectedTab,
                        onSelectTab = { index ->
                            scope.launch {
                                listState.animateScrollToItem(0)
                            }.invokeOnCompletion {
                                onIntent(EditFormulaIntent.SelectTab(index))
                            }
                        }
                    )
                }

                when(screenState.content){
                    is EditFormulaStateContent.Info -> item("info") {
                        InfoItems(
                            info = screenState.content,
                            onNameChange = { name ->
                                onIntent(EditFormulaIntent.ChangeName(name))
                            },
                            onDescriptionChange = { description ->
                                onIntent(EditFormulaIntent.ChangeDescription(description))
                            },
                            onNameSave = {
                                onIntent(EditFormulaIntent.SaveName)
                            },
                            onDescriptionSave = {
                                onIntent(EditFormulaIntent.SaveDescription)
                            },
                        )
                    }
                    is EditFormulaStateContent.Inputs -> {
                        if (screenState.content.list.isEmpty()){
                            item(key = "empty") {
                                EmptyScreenInListFullSize(
                                    EmptyScreenInfo.noEditRecords()
                                )
                            }
                        } else{
                            itemsIndexed(
                                items = screenState.content.list,
                                key = {_, input -> "input${input.id}"}
                            ){ index, input ->
                                InputItem(
                                    input = input,
                                    onLabelChange = { newText ->
                                        onIntent(
                                            EditFormulaIntent.ChangeItemLabel(
                                                index,
                                                newText,
                                                EditList.Inputs
                                            )
                                        )
                                    },
                                    onCodeLabelChange = { newText ->
                                        onIntent(
                                            EditFormulaIntent.ChangeItemCodeLabel(
                                                index,
                                                newText,
                                                EditList.Inputs
                                            )
                                        )
                                    },
                                    onDefaultDataChange = { newText ->
                                        onIntent(
                                            EditFormulaIntent.ChangeInputDefaultData(
                                                index,
                                                newText
                                            )
                                        )
                                    },
                                    onDelete = {
                                        onIntent(
                                            EditFormulaIntent.DeleteItem(
                                                index,
                                                EditList.Inputs
                                            )
                                        )
                                    },
                                    onMoveDown = {
                                        onIntent(
                                            EditFormulaIntent.MoveItem(
                                                index,
                                                index + 1,
                                                EditList.Inputs
                                            )
                                        )
                                    },
                                    onMoveUp = {
                                        onIntent(
                                            EditFormulaIntent.MoveItem(
                                                index,
                                                index - 1,
                                                EditList.Inputs
                                            )
                                        )
                                    },
                                    onLabelSave = {
                                        onIntent(EditFormulaIntent.SaveItemLabel(index, EditList.Inputs))
                                    },
                                    onCodeLabelSave = {
                                        onIntent(EditFormulaIntent.SaveItemCodeLabel(index, EditList.Inputs))
                                    },
                                    onDefaultDataSave = {
                                        onIntent(EditFormulaIntent.SaveInputDefaultData(index))
                                    }
                                )
                            }

                            emptySpaceOfButton()
                        }
                    }

                    is EditFormulaStateContent.Code -> {
                        item {
                            CodeItem(
                                text = screenState.content.text,
                                onTextChange = { onIntent(EditFormulaIntent.ChangeCodeText(it)) },
                                onSaveText = {
                                    onIntent(EditFormulaIntent.SaveCodeText)
                                }
                            )
                        }
                    }
                    is EditFormulaStateContent.Results -> {
                        if (screenState.content.list.isEmpty()){
                            item(key = "empty") {
                                EmptyScreenInListFullSize(
                                    EmptyScreenInfo.noEditRecords()
                                )
                            }
                        } else{
                            itemsIndexed(
                                items = screenState.content.list,
                                key = {_, result -> "result${result.id}"}
                            ){ index, result ->
                                ResultItem(
                                    result = result,
                                    onLabelChange = { newText ->
                                        onIntent(
                                            EditFormulaIntent.ChangeItemLabel(
                                                index,
                                                newText,
                                                EditList.Results
                                            )
                                        )
                                    },
                                    onCodeLabelChange = { newText ->
                                        onIntent(
                                            EditFormulaIntent.ChangeItemCodeLabel(
                                                index,
                                                newText,
                                                EditList.Results
                                            )
                                        )
                                    },
                                    onDelete = {
                                        onIntent(
                                            EditFormulaIntent.DeleteItem(
                                                index,
                                                EditList.Results
                                            )
                                        )
                                    },
                                    onMoveDown = {
                                        onIntent(
                                            EditFormulaIntent.MoveItem(
                                                index,
                                                index + 1,
                                                EditList.Results
                                            )
                                        )
                                    },
                                    onMoveUp = {
                                        onIntent(
                                            EditFormulaIntent.MoveItem(
                                                index,
                                                index - 1,
                                                EditList.Results
                                            )
                                        )
                                    },
                                    onLabelSave = {
                                        onIntent(EditFormulaIntent.SaveItemLabel(index, EditList.Results))
                                    },
                                    onCodeLabelSave = {
                                        onIntent(EditFormulaIntent.SaveItemCodeLabel(index, EditList.Results))
                                    }
                                )
                            }
                            emptySpaceOfButton()
                        }
                    }
                    EditFormulaStateContent.Nothing -> Unit
                }
            }
        }
        AddDataButton(
            isShow =
            screenState.content is EditFormulaStateContent.Inputs ||
                    screenState.content is EditFormulaStateContent.Results,
            onClick = { onIntent(EditFormulaIntent.AddDataItem) }
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
    onSelectTab: (Int) -> Unit,
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