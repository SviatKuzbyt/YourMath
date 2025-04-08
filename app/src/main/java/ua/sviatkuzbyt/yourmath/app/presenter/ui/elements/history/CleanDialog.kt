package ua.sviatkuzbyt.yourmath.app.presenter.ui.elements.history

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import ua.sviatkuzbyt.yourmath.app.R
import ua.sviatkuzbyt.yourmath.app.presenter.ui.elements.basic.dialog.DialogBasic
import ua.sviatkuzbyt.yourmath.app.presenter.ui.elements.basic.dialog.DialogButton

@Composable
fun ClearDialog(
    onClose: () -> Unit,
    onClear: () -> Unit
){
    DialogBasic(
        tittle = stringResource(R.string.clear_history),
        content = stringResource(R.string.clear_history_content),
        onClose = onClose,
        buttons = {
            DialogButton(
                R.string.no,
                onClick = onClose
            )
            DialogButton(
                R.string.yes,
                onClick = onClear
            )
        }
    )
}