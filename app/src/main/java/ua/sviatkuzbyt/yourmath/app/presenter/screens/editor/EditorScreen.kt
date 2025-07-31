package ua.sviatkuzbyt.yourmath.app.presenter.screens.editor

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import ua.sviatkuzbyt.yourmath.app.R
import ua.sviatkuzbyt.yourmath.app.presenter.controllers.editor.EditorDialogContent
import ua.sviatkuzbyt.yourmath.app.presenter.controllers.editor.EditorIntent
import ua.sviatkuzbyt.yourmath.app.presenter.controllers.editor.EditorListContent
import ua.sviatkuzbyt.yourmath.app.presenter.controllers.editor.EditorState
import ua.sviatkuzbyt.yourmath.app.presenter.navigation.LocalNavController
import ua.sviatkuzbyt.yourmath.app.presenter.navigation.NavigateIntent
import ua.sviatkuzbyt.yourmath.app.presenter.navigation.onNavigateIntent
import ua.sviatkuzbyt.yourmath.app.presenter.other.basic.showToast
import ua.sviatkuzbyt.yourmath.app.presenter.ui.elements.basic.ScreenTopBar
import ua.sviatkuzbyt.yourmath.app.presenter.ui.elements.basic.button.AddButton
import ua.sviatkuzbyt.yourmath.app.presenter.ui.elements.basic.dialog.DialogError
import ua.sviatkuzbyt.yourmath.app.presenter.ui.elements.editor.dialog.DialogDeleteAll
import ua.sviatkuzbyt.yourmath.app.presenter.ui.elements.editor.dialog.DialogDeleteFormula
import ua.sviatkuzbyt.yourmath.app.presenter.ui.elements.editor.EditorContentList
import ua.sviatkuzbyt.yourmath.domain.usecases.editformula.CreateFormulaUseCase

@Composable
fun EditorScreen(viewModel: EditorViewModel = hiltViewModel()){
    val screenState by viewModel.screenState.collectAsState()
    val navController = LocalNavController.current

    EditorContent(
        screenState = screenState,
        onIntent = viewModel::onIntent,
        onNavigate = { onNavigateIntent(navController, it) }
    )
}

@Composable
fun EditorContent(
    screenState: EditorState,
    onIntent: (EditorIntent) -> Unit,
    onNavigate: (NavigateIntent) -> Unit
){
    val listState = rememberLazyListState()
    val context = LocalContext.current
    val isList = screenState.listContent is EditorListContent.FormulaList

    val onExportClick = remember(isList) {{
        if (isList) onNavigate(NavigateIntent.OpenExportScreen)
        else showToast(R.string.nothing_to_export, context)
    }}

    val onClearClick = remember(isList) {{
        if (isList) onIntent(EditorIntent.OpenDialog(EditorDialogContent.DeleteAll))
        else showToast(R.string.no_items_to_delete, context)
    }}

    Box(Modifier.fillMaxSize()){
        Column(Modifier.fillMaxSize()) {
            ScreenTopBar(
                tittle = stringResource(R.string.editor),
                listState = listState,
                onBack = { onNavigate(NavigateIntent.NavigateBack) }
            )

            EditorContentList(
                listContent = screenState.listContent,
                listState = listState,
                onImportFormulas = { onNavigate(NavigateIntent.OpenImportScreen) },
                onOpenFormula = { onNavigate(NavigateIntent.OpenEditFormulaScreen(it)) },
                onDeleteFormula = { onIntent(EditorIntent.OpenDialog(it)) },
                onMove = { from, to -> onIntent(EditorIntent.MoveItem(from, to)) },
                onExportClick = onExportClick,
                onClearClick = onClearClick
            )
        }

        AddButton(R.string.add_formula) {
            onNavigate(NavigateIntent.OpenEditFormulaScreen(CreateFormulaUseCase.NEW_FORMULA))
        }
    }

    EditorDialog(
        dialogContent = screenState.dialogContent,
        onClose = { onIntent(EditorIntent.CloseDialog) },
        onDeleteFormulaItem = { formulaID -> onIntent(EditorIntent.DeleteFormula(formulaID)) },
        onDeleteAll = { onIntent(EditorIntent.DeleteAllFormulas) }
    )
}

@Composable
private fun EditorDialog(
    dialogContent: EditorDialogContent,
    onClose: () -> Unit,
    onDeleteFormulaItem: (Long) -> Unit,
    onDeleteAll: () -> Unit
){
    when(dialogContent){
        is EditorDialogContent.ErrorDialog -> DialogError(
            data = dialogContent.data,
            onCloseClick = onClose,
        )
        is EditorDialogContent.DeleteFormula -> DialogDeleteFormula(
            formulaName = dialogContent.formulaName,
            onClose = onClose,
            onDelete = { onDeleteFormulaItem(dialogContent.formulaID) }
        )
        EditorDialogContent.DeleteAll -> DialogDeleteAll(
            onClose = onClose,
            onDelete = onDeleteAll
        )
        EditorDialogContent.Nothing -> Unit
    }
}