package ua.sviatkuzbyt.yourmath.app.presenter.screens.editor

import android.widget.Toast
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
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
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

    Box(modifier = Modifier.fillMaxSize()){
        Column(modifier = Modifier.fillMaxSize()) {
            ScreenTopBar(
                tittle = stringResource(R.string.editor),
                listState = listState,
                onBack = { onNavigate(NavigateIntent.NavigateBack) }
            )

            EditorContentList(
                listContent = screenState.listContent,
                listState = listState,
                onImportFormulas = {
                    onNavigate(NavigateIntent.OpenImportScreen)
                },
                onExportClick = {
                    if (screenState.listContent is EditorListContent.FormulaList){
                        onNavigate(NavigateIntent.OpenExportScreen)
                    } else {
                        Toast.makeText(context, R.string.nothing_to_export, Toast.LENGTH_SHORT).show()
                    }
                },
                onClearClick = {
                    if (screenState.listContent is EditorListContent.FormulaList){
                        onIntent(EditorIntent.OpenDialog(EditorDialogContent.DeleteAll))
                    } else {
                        Toast.makeText(context, R.string.no_items_to_delete, Toast.LENGTH_SHORT).show()
                    }
                },
                onOpenFormula = {
                    onNavigate(NavigateIntent.OpenFormulaEditScreen(it))
                },
                onDeleteFormula = {onIntent(EditorIntent.OpenDialog(it))},
                onMove = {from, to ->
                    onIntent(EditorIntent.MoveItem(from, to))
                }
            )
        }

        AddButton(R.string.add_formula) {
            onIntent(EditorIntent.AddFormula)
        }
    }

    when(screenState.dialogContent){
        is EditorDialogContent.ErrorDialog -> DialogError(
            data = screenState.dialogContent.data,
            onCloseClick = { onIntent(EditorIntent.CloseDialog) },
        )
        EditorDialogContent.Nothing -> {}
        EditorDialogContent.DeleteAll -> DialogDeleteAll(
            onClose = {onIntent(EditorIntent.CloseDialog)},
            onDelete = {onIntent(EditorIntent.DeleteAllFormulas)}
        )
        is EditorDialogContent.DeleteFormula -> DialogDeleteFormula(
            formulaName = screenState.dialogContent.formulaName,
            onClose = { onIntent(EditorIntent.CloseDialog) },
            onDelete = {
                onIntent(EditorIntent.DeleteFormula(screenState.dialogContent.formulaID))
            }
        )
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
        item{
            TittleText(R.string.editor)
        }

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
                    key = {_, formula -> formula.id}
                ){ index, formula ->
                    AnimateListItem {
                        EditFormulaItem(
                            name = formula.name,
                            onClick = { onOpenFormula(formula.id) },
                            onDelete = {
                                onDeleteFormula(
                                    EditorDialogContent.DeleteFormula(
                                        formulaID = formula.id,
                                        formulaName = formula.name
                                    )
                                )
                            },
                            onMoveUp = {onMove(index, index+1)},
                            onMoveDown = {onMove(index, index-1)},
                        )
                    }
                }
                item {
                    Spacer(Modifier.height(AppSizes.dp72))
                }
            }

            EditorListContent.Nothing -> {}
        }
    }
}