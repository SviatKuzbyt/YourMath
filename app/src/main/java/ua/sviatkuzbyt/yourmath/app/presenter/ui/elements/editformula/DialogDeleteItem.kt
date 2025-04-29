package ua.sviatkuzbyt.yourmath.app.presenter.ui.elements.editformula

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import ua.sviatkuzbyt.yourmath.app.R
import ua.sviatkuzbyt.yourmath.app.presenter.ui.elements.basic.dialog.DialogConfirm

@Composable
fun DialogDeleteItem(
    onClose: () -> Unit,
    onDelete: () -> Unit
){
    DialogConfirm(
        tittle = stringResource(R.string.delete_item),
        text = stringResource(R.string.delete_item_content),
        onNo = onClose,
        onYes = onDelete
    )
}