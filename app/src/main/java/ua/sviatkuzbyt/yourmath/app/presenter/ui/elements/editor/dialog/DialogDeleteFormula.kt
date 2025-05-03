package ua.sviatkuzbyt.yourmath.app.presenter.ui.elements.editor.dialog

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import ua.sviatkuzbyt.yourmath.app.R
import ua.sviatkuzbyt.yourmath.app.presenter.ui.elements.basic.dialog.DialogConfirm

@Composable
fun DialogDeleteFormula(
    formulaName: String,
    onClose: () -> Unit,
    onDelete: () -> Unit
){
    val text =
        "${stringResource(R.string.delete_formula_text_1)} $formulaName? " +
        stringResource(R.string.delete_formula_text_2)

    DialogConfirm(
        tittle = stringResource(R.string.delete_item),
        text = text,
        onNo = onClose,
        onYes = onDelete
    )
}