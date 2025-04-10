package ua.sviatkuzbyt.yourmath.app.presenter.ui.elements.basic.dialog

import androidx.compose.runtime.Composable
import ua.sviatkuzbyt.yourmath.app.R

@Composable
fun DialogConfirm(
    tittle: String,
    text: String,
    onNo: () -> Unit,
    onYes: () -> Unit
){
    DialogBasic(
        tittle = tittle,
        content = text,
        onClose = onNo,
        buttons = {
            DialogButton(
                textRes = R.string.no,
                onClick = onNo
            )
            DialogButton(
                textRes = R.string.yes,
                onClick = onYes
            )
        }
    )
}