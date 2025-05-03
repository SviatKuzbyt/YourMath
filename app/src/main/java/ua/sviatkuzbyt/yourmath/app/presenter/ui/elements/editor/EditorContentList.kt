package ua.sviatkuzbyt.yourmath.app.presenter.ui.elements.editor

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import ua.sviatkuzbyt.yourmath.app.R
import ua.sviatkuzbyt.yourmath.app.presenter.controllers.editor.EditorDialogContent
import ua.sviatkuzbyt.yourmath.app.presenter.controllers.editor.EditorListContent
import ua.sviatkuzbyt.yourmath.app.presenter.ui.elements.basic.list.AnimateListItem
import ua.sviatkuzbyt.yourmath.app.presenter.ui.elements.basic.list.EmptyScreenInListInSize
import ua.sviatkuzbyt.yourmath.app.presenter.ui.elements.basic.list.emptySpaceOfButton
import ua.sviatkuzbyt.yourmath.app.presenter.ui.elements.basic.text.SubTittleText
import ua.sviatkuzbyt.yourmath.app.presenter.ui.elements.basic.text.TittleText
import ua.sviatkuzbyt.yourmath.app.presenter.ui.elements.editor.items.EditFormulaItem
import ua.sviatkuzbyt.yourmath.app.presenter.ui.elements.editor.items.ActionsItems
import ua.sviatkuzbyt.yourmath.app.presenter.ui.theme.AppSizes
import ua.sviatkuzbyt.yourmath.domain.structures.edit.FormulaNameItem

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
                            name = getFormulaName(formula, stringResource(R.string.note)),
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

                emptySpaceOfButton()

            }

            EditorListContent.Nothing -> Unit
        }
    }
}

private fun getFormulaName(formula: FormulaNameItem, noteLabel: String): String{
    val name = formula.name
    val note = if (formula.isNote) " ($noteLabel)" else ""
    return "$name$note"
}