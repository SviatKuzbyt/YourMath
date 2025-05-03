package ua.sviatkuzbyt.yourmath.app.presenter.ui.elements.history.above

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import ua.sviatkuzbyt.yourmath.app.R
import ua.sviatkuzbyt.yourmath.app.presenter.ui.elements.basic.dialog.DialogConfirm

@Composable
fun ClearDialog(
    onClose: () -> Unit,
    onClear: () -> Unit
){
    DialogConfirm(
        tittle = stringResource(R.string.clear_history),
        text = stringResource(R.string.clear_history_content),
        onNo = onClose,
        onYes = onClear
    )
}