package ua.sviatkuzbyt.yourmath.app.presenter.ui.elements.editor

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import ua.sviatkuzbyt.yourmath.app.R
import ua.sviatkuzbyt.yourmath.app.presenter.ui.elements.basic.dialog.DialogConfirm

@Composable
fun DialogDeleteAll(
    onClose: () -> Unit,
    onDelete: () -> Unit
){
    DialogConfirm(
        tittle = stringResource(R.string.delete_all_formulas),
        text = stringResource(R.string.delete_all_formulas_text),
        onNo = onClose,
        onYes = onDelete
    )
}