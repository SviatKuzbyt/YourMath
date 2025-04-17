package ua.sviatkuzbyt.yourmath.app.presenter.screens.editor

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import kotlinx.coroutines.flow.collectLatest
import ua.sviatkuzbyt.yourmath.app.R
import ua.sviatkuzbyt.yourmath.app.presenter.controllers.editor.EditorDialogContent
import ua.sviatkuzbyt.yourmath.app.presenter.controllers.editor.EditorIntent
import ua.sviatkuzbyt.yourmath.app.presenter.controllers.editor.EditorListContent
import ua.sviatkuzbyt.yourmath.app.presenter.controllers.editor.EditorState
import ua.sviatkuzbyt.yourmath.app.presenter.navigation.LocalNavController
import ua.sviatkuzbyt.yourmath.app.presenter.navigation.NavigateIntent
import ua.sviatkuzbyt.yourmath.app.presenter.navigation.onNavigateIntent
import ua.sviatkuzbyt.yourmath.app.presenter.other.basic.GlobalEvent
import ua.sviatkuzbyt.yourmath.app.presenter.other.basic.GlobalEventType
import ua.sviatkuzbyt.yourmath.app.presenter.other.basic.showToast
import ua.sviatkuzbyt.yourmath.app.presenter.screens.editformula.EditFormulaViewModel
import ua.sviatkuzbyt.yourmath.app.presenter.ui.elements.basic.AnimateListItem
import ua.sviatkuzbyt.yourmath.app.presenter.ui.elements.basic.EmptyScreenInListInSize
import ua.sviatkuzbyt.yourmath.app.presenter.ui.elements.basic.ScreenTopBar
import ua.sviatkuzbyt.yourmath.app.presenter.ui.elements.basic.button.AddButton
import ua.sviatkuzbyt.yourmath.app.presenter.ui.elements.basic.dialog.DialogError
import ua.sviatkuzbyt.yourmath.app.presenter.ui.elements.editor.ActionsItems
import ua.sviatkuzbyt.yourmath.app.presenter.ui.elements.basic.text.SubTittleText
import ua.sviatkuzbyt.yourmath.app.presenter.ui.elements.basic.text.TittleText
import ua.sviatkuzbyt.yourmath.app.presenter.ui.elements.editor.DialogDeleteAll
import ua.sviatkuzbyt.yourmath.app.presenter.ui.elements.editor.DialogDeleteFormula
import ua.sviatkuzbyt.yourmath.app.presenter.ui.elements.editor.EditFormulaItem
import ua.sviatkuzbyt.yourmath.app.presenter.ui.theme.AppSizes
import ua.sviatkuzbyt.yourmath.domain.usecases.editformula.GetEditFormulaDataUseCase

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

    val onExportClick = remember(isList) {
        {
            if (isList) {
                onNavigate(NavigateIntent.OpenExportScreen)
            } else {
                showToast(R.string.nothing_to_export, context)
            }
        }
    }

    val onClearClick = remember(isList) {
        {
            if (isList) {
                onIntent(EditorIntent.OpenDialog(EditorDialogContent.DeleteAll))
            } else {
                showToast(R.string.no_items_to_delete, context)
            }
        }
    }

    ObserveFormulasChange{
        onIntent(EditorIntent.LoadImportedFormulas)
    }

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
            onNavigate(NavigateIntent.OpenEditFormulaScreen(GetEditFormulaDataUseCase.NEW_FORMULA))
        }
    }

    when(val dialog = screenState.dialogContent){
        is EditorDialogContent.ErrorDialog -> DialogError(
            data = dialog.data,
            onCloseClick = { onIntent(EditorIntent.CloseDialog) },
        )
        is EditorDialogContent.DeleteFormula -> DialogDeleteFormula(
            formulaName = dialog.formulaName,
            onClose = { onIntent(EditorIntent.CloseDialog) },
            onDelete = {
                onIntent(EditorIntent.DeleteFormula(dialog.formulaID))
            }
        )
        EditorDialogContent.DeleteAll -> DialogDeleteAll(
            onClose = {onIntent(EditorIntent.CloseDialog)},
            onDelete = {onIntent(EditorIntent.DeleteAllFormulas)}
        )
        EditorDialogContent.Nothing -> Unit
    }
}

@Composable
fun EditorContentList(
    listContent: EditorListContent,
    listState: LazyListState,
    onImportFormulas: () -> Unit,
    onExportClick: () -> Unit,
    onClearClick: () -> Unit,
    onOpenFormula: (Long) -> Unit,
    onDeleteFormula: (EditorDialogContent) -> Unit,
    onMove: (Int, Int) -> Unit
){
    LazyColumn(
        state = listState,
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(horizontal = AppSizes.dp16)
    ) {
        item{ TittleText(R.string.editor) }

        item {
            ActionsItems(
                onImportClick = onImportFormulas,
                onExportClick = onExportClick,
                onClearClick = onClearClick
            )
        }

        item {
            SubTittleText(
                textRes = R.string.formulas,
                modifier = Modifier.padding(top = AppSizes.dp16)
            )
        }

        when(listContent){
            is EditorListContent.EmptyScreen -> item(key = "empty") {
                EmptyScreenInListInSize(listContent.info)
            }

            is EditorListContent.FormulaList ->{
                itemsIndexed(
                    items = listContent.formulas,
                    key = { _, formula -> formula.id }
                ){ index, formula ->
                    AnimateListItem {
                        EditFormulaItem(
                            name = formula.name,
                            onClick = { onOpenFormula(formula.id) },
                            onMoveDown = { onMove(index, index+1) },
                            onMoveUp = { onMove(index, index-1) },
                            onDelete = {
                                onDeleteFormula(
                                    EditorDialogContent.DeleteFormula(
                                        formulaID = formula.id,
                                        formulaName = formula.name
                                    )
                                )
                            }
                        )
                    }
                }
                item {
                    Spacer(Modifier.height(AppSizes.dp72))
                }
            }

            EditorListContent.Nothing -> Unit
        }
    }
}

@Composable
private fun ObserveFormulasChange(
    onLoadImported: () -> Unit
){
    LaunchedEffect(Unit) {
        GlobalEvent.event.collectLatest { event ->
            if (event == GlobalEventType.ImportedFormulas){
                onLoadImported()
                GlobalEvent.clearEvent()
            }
        }
    }
}