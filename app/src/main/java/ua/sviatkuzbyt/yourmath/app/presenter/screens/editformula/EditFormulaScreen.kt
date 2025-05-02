package ua.sviatkuzbyt.yourmath.app.presenter.screens.editformula

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.hilt.navigation.compose.hiltViewModel
import kotlinx.coroutines.launch
import ua.sviatkuzbyt.yourmath.app.presenter.controllers.editformula.EditFormulaDialogContent
import ua.sviatkuzbyt.yourmath.app.presenter.controllers.editformula.EditFormulaIntent
import ua.sviatkuzbyt.yourmath.app.presenter.controllers.editformula.EditFormulaState
import ua.sviatkuzbyt.yourmath.app.presenter.controllers.editformula.EditFormulaStateContent
import ua.sviatkuzbyt.yourmath.app.presenter.navigation.LocalNavController
import ua.sviatkuzbyt.yourmath.app.presenter.navigation.NavigateIntent
import ua.sviatkuzbyt.yourmath.app.presenter.navigation.onNavigateIntent
import ua.sviatkuzbyt.yourmath.app.presenter.other.editformula.ClearFocusOnStop
import ua.sviatkuzbyt.yourmath.app.presenter.ui.elements.basic.dialog.DialogError
import ua.sviatkuzbyt.yourmath.app.presenter.ui.elements.editformula.screen.AddDataButton
import ua.sviatkuzbyt.yourmath.app.presenter.ui.elements.editformula.DialogDeleteItem
import ua.sviatkuzbyt.yourmath.app.presenter.ui.elements.editformula.screen.EditFormulaList
import ua.sviatkuzbyt.yourmath.app.presenter.ui.elements.editformula.screen.EditFormulaTopBar

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