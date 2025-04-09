package ua.sviatkuzbyt.yourmath.app.presenter.ui.elements.basic.dialog

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import ua.sviatkuzbyt.yourmath.app.R
import ua.sviatkuzbyt.yourmath.app.presenter.other.basic.ErrorData

@Composable
fun ShowDialogError(
    errorData: ErrorData?,
    onCloseClick: () -> Unit
){
    errorData?.let { error ->
        DialogError(
            data = error,
            onCloseClick = onCloseClick
        )
    }
}

@Composable
fun DialogError(
    data: ErrorData,
    onCloseClick: () -> Unit
) {
    val contentText = if (data.detailStr != null){
        "${stringResource(data.detailRes)}: ${data.detailStr}"
    } else {
        stringResource(data.detailRes)
    }

    DialogBasic(
        tittle = stringResource(data.tittleRes),
        content = contentText,
        onClose = onCloseClick,
        buttons = {
            DialogButton(
                textRes = R.string.ok,
                onClick = onCloseClick
            )
        }
    )
}