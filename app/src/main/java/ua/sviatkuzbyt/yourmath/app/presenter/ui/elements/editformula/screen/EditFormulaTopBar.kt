package ua.sviatkuzbyt.yourmath.app.presenter.ui.elements.editformula.screen

import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import ua.sviatkuzbyt.yourmath.app.R
import ua.sviatkuzbyt.yourmath.app.presenter.other.basic.showToast
import ua.sviatkuzbyt.yourmath.app.presenter.ui.elements.basic.ScreenTopBar
import ua.sviatkuzbyt.yourmath.app.presenter.ui.elements.basic.button.ButtonIconTopBar

@Composable
fun EditFormulaTopBar(
    listState: LazyListState,
    onBack: () -> Unit
){
    val context = LocalContext.current
    ScreenTopBar(
        tittle = stringResource(R.string.edit_formula),
        listState = listState,
        onBack = onBack,
        toolButtons = {
            ButtonIconTopBar(
                imageRes = R.drawable.ic_auto_save,
                contentDescriptionRes = R.string.change_auto_save,
                onClick = { showToast(R.string.change_auto_save, context) }
            )
        }
    )
}