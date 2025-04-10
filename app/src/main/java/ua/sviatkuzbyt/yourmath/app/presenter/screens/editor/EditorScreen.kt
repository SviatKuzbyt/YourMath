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
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
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

    Box(modifier = Modifier.fillMaxSize()){
        Column(modifier = Modifier.fillMaxSize()) {
            ScreenTopBar(
                tittle = stringResource(R.string.editor),
                listState = listState,
                onBack = { onNavigate(NavigateIntent.NavigateBack) }
            )

            EditorContent(
                listContent = screenState.listContent,
                listState = listState,
                onImportFormulas = { onIntent(EditorIntent.ImportFormulas) },
                onExportClick = { onIntent(EditorIntent.ExportFormulas) },
                onClearClick = {
                    onIntent(EditorIntent.OpenDialog(EditorDialogContent.DeleteAll))
                },
                onOpenFormula = {
                    onNavigate(NavigateIntent.OpenFormulaEdit(it))
                },
                onDeleteFormula = {onIntent(EditorIntent.OpenDialog(it))}
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
fun EditorContent(
    listContent: EditorListContent,
    listState: LazyListState,
    onImportFormulas: () -> Unit,
    onExportClick: () -> Unit,
    onClearClick: () -> Unit,
    onOpenFormula: (Long) -> Unit,
    onDeleteFormula: (EditorDialogContent) -> Unit
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
                items(
                    items = listContent.formulas,
                    key = {formula -> formula.id}
                ){ formula ->
                    AnimateListItem {
                        EditFormulaItem(
                            name = formula.name,
                            onClick = {onOpenFormula(formula.id)},
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

            EditorListContent.Nothing -> {}
        }
    }
}