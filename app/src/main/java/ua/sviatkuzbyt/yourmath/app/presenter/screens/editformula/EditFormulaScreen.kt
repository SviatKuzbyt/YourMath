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
import androidx.compose.foundation.lazy.LazyListState
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
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.compose.LocalLifecycleOwner
import kotlinx.coroutines.launch
import ua.sviatkuzbyt.yourmath.app.R
import ua.sviatkuzbyt.yourmath.app.presenter.controllers.editformula.EditFormulaDialogContent
import ua.sviatkuzbyt.yourmath.app.presenter.controllers.editformula.EditFormulaIntent
import ua.sviatkuzbyt.yourmath.app.presenter.controllers.editformula.EditFormulaState
import ua.sviatkuzbyt.yourmath.app.presenter.controllers.editformula.EditFormulaStateContent
import ua.sviatkuzbyt.yourmath.app.presenter.controllers.editformula.FormulaListText
import ua.sviatkuzbyt.yourmath.app.presenter.controllers.editformula.FormulaText
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
import ua.sviatkuzbyt.yourmath.app.presenter.ui.elements.basic.dialog.DialogError
import ua.sviatkuzbyt.yourmath.app.presenter.ui.elements.basic.emptySpaceOfButton
import ua.sviatkuzbyt.yourmath.app.presenter.ui.elements.basic.text.TittleText
import ua.sviatkuzbyt.yourmath.app.presenter.ui.elements.editformula.CodeItem
import ua.sviatkuzbyt.yourmath.app.presenter.ui.elements.editformula.DialogDeleteItem
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

@Composable
fun EditFormulaContent(
    screenState: EditFormulaState,
    onIntent: (EditFormulaIntent) -> Unit,
    onNavigate: (NavigateIntent) -> Unit
){
    val listState = rememberLazyListState()
    val scope = rememberCoroutineScope()
    val focusManager = LocalFocusManager.current

    fun navigateBack(){
        focusManager.clearFocus(true)
        onIntent(EditFormulaIntent.Exit)
    }

    Box(Modifier.fillMaxSize()){
        Column(Modifier.fillMaxSize()) {
            EditFormulaTopBar(listState, ::navigateBack)

            EditFormulaList(
                listState = listState,
                tabs = screenState.tabs,
                selectedTab = screenState.selectedTab,
                content = screenState.content,
                onSelectTab = { tabIndex ->
                    scope.launch { listState.animateScrollToItem(0) }.invokeOnCompletion {
                        onIntent(EditFormulaIntent.SelectTab(tabIndex))
                    }
                },
                onChangeText = { type, text ->
                    onIntent(EditFormulaIntent.ChangeFormulaText(type, text))
                },
                onChangeIsNote = { isNote ->
                    onIntent(EditFormulaIntent.ChangeIsNote(isNote))
                },
                onSaveText = { type ->
                    onIntent(EditFormulaIntent.SaveFormulaText(type))
                },
                onListChangeText = { type, index, text ->
                    onIntent(EditFormulaIntent.ChangeListText(type, index, text))
                },
                onListSaveText = { type, index ->
                    onIntent(EditFormulaIntent.SaveListText(type, index))
                },
                onListMove = { from, to ->
                    onIntent(EditFormulaIntent.MoveItem(from, to))
                },
                onListDelete = { index ->
                    onIntent(EditFormulaIntent.OpenDialog(
                        EditFormulaDialogContent.DeleteFormulaContent(index)
                    ))
                }
            )
        }

        AddDataButton(
            isShow =
                screenState.content is EditFormulaStateContent.Inputs ||
                screenState.content is EditFormulaStateContent.Results,
            onClick = { onIntent(EditFormulaIntent.AddDataItem) }
        )
    }

    EditFormulaDialog(
        dialog = screenState.dialog,
        onClose = { onIntent(EditFormulaIntent.CloseDialog) },
        onListDelete = { index -> onIntent(EditFormulaIntent.DeleteItem(index)) }
    )

    if (screenState.isNavigateBack){
        onNavigate(NavigateIntent.NavigateBack)
    }

    ClearFocusOnStop(focusManager)
    BackHandler { navigateBack() }
}

@Composable
private fun EditFormulaTopBar(
    listState: LazyListState,
    onBack: () -> Unit
){
    val context = LocalContext.current
    ScreenTopBar(
        tittle = stringResource(R.string.edit_formula),
        listState = listState,
        onBack = onBack,
        toolButtons = {
            ButtonIconTopBar(
                imageRes = R.drawable.ic_auto_save,
                contentDescriptionRes = R.string.change_auto_save,
                onClick = { showToast(R.string.change_auto_save, context) }
            )
        }
    )
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun EditFormulaList(
    listState: LazyListState,
    tabs: List<Int>,
    selectedTab: Int,
    content: EditFormulaStateContent,
    onSelectTab: (Int) -> Unit,
    onChangeText: (FormulaText, String) -> Unit,
    onChangeIsNote: (Boolean) -> Unit,
    onSaveText: (FormulaText) -> Unit,
    onListChangeText: (FormulaListText, Int, String) -> Unit,
    onListSaveText: (FormulaListText, Int) -> Unit,
    onListMove: (Int, Int) -> Unit,
    onListDelete: (Int) -> Unit
){
    LazyColumn(modifier = Modifier.fillMaxSize(), state = listState) {
        item { Tittle() }

        stickyHeader {
            ScreenTabs(
                tabs = tabs,
                selectedTab = selectedTab,
                onSelectTab = onSelectTab
            )
        }

        when(content){
            is EditFormulaStateContent.Info -> item("info") {
                InfoItems(
                    info = content,
                    onNameChange = { text -> onChangeText(FormulaText.Name, text) },
                    onDescriptionChange = { text -> onChangeText(FormulaText.Description, text) },
                    onNameSave = { onSaveText(FormulaText.Name) },
                    onDescriptionSave = { onSaveText(FormulaText.Description) },
                    onChangeIsNote = onChangeIsNote
                )
            }

            is EditFormulaStateContent.Inputs -> {
                if (content.list.isEmpty()){
                    item(key = "empty") {
                        EmptyScreenInListFullSize(EmptyScreenInfo.noEditRecords())
                    }
                } else {
                    itemsIndexed(
                        items = content.list,
                        key = { _, input -> "input${input.id}" }
                    ){ index, input ->
                        InputItem(
                            input = input,
                            onLabelChange = { text ->
                                onListChangeText(FormulaListText.InputLabel, index, text)
                            },
                            onCodeLabelChange = { text ->
                                onListChangeText(FormulaListText.InputCodeLabel, index, text)
                            },
                            onDefaultDataChange = { text ->
                                onListChangeText(FormulaListText.InputDefaultData, index, text)
                            },
                            onLabelSave = {
                                onListSaveText(FormulaListText.InputLabel, index)
                            },
                            onCodeLabelSave = {
                                onListSaveText(FormulaListText.InputCodeLabel, index)
                            },
                            onDefaultDataSave = {
                                onListSaveText(FormulaListText.InputDefaultData, index)
                            },
                            onDelete = { onListDelete(index) },
                            onMoveDown = { onListMove(index, index + 1) },
                            onMoveUp = { onListMove(index, index - 1) }
                        )
                    }

                    emptySpaceOfButton()
                }
            }

            is EditFormulaStateContent.Results -> {
                if (content.list.isEmpty()){
                    item(key = "empty") {
                        EmptyScreenInListFullSize(EmptyScreenInfo.noEditRecords())
                    }
                } else{
                    itemsIndexed(
                        items = content.list,
                        key = {_, result -> "result${result.id}"}
                    ){ index, result ->
                        ResultItem(
                            result = result,
                            onLabelChange = { text ->
                                onListChangeText(FormulaListText.ResultLabel, index, text)
                            },
                            onCodeLabelChange = { text ->
                                onListChangeText(FormulaListText.ResultCodeLabel, index, text)
                            },
                            onLabelSave = {
                                onListSaveText(FormulaListText.ResultLabel, index)
                            },
                            onCodeLabelSave = {
                                onListSaveText(FormulaListText.ResultCodeLabel, index)
                            },
                            onDelete = { onListDelete(index) },
                            onMoveDown = { onListMove(index, index + 1) },
                            onMoveUp = { onListMove(index, index - 1) }
                        )
                    }
                    emptySpaceOfButton()
                }
            }

            is EditFormulaStateContent.Code -> {
                item {
                    CodeItem(
                        text = content.text,
                        onTextChange = { text -> onChangeText(FormulaText.Code, text) },
                        onSaveText = { onSaveText(FormulaText.Code) }
                    )
                }
            }

            EditFormulaStateContent.Nothing -> Unit
        }
    }
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
private fun EditFormulaDialog(
    dialog: EditFormulaDialogContent,
    onClose: () -> Unit,
    onListDelete: (Int) -> Unit
){
    when(dialog){
        is EditFormulaDialogContent.DeleteFormulaContent -> DialogDeleteItem(
            onClose = onClose,
            onDelete = { onListDelete(dialog.index) }
        )
        is EditFormulaDialogContent.ErrorDialogContent -> DialogError(
            data = dialog.data,
            onCloseClick = onClose,
        )
        EditFormulaDialogContent.Nothing -> Unit
    }
}

@Composable
private fun ClearFocusOnStop(focusManager: FocusManager){
    val lifecycleOwner = LocalLifecycleOwner.current

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