package ua.sviatkuzbyt.yourmath.app.presenter.ui.elements.formula.screen

import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.runtime.Composable
import ua.sviatkuzbyt.yourmath.app.R
import ua.sviatkuzbyt.yourmath.app.presenter.ui.elements.basic.ScreenTopBar
import ua.sviatkuzbyt.yourmath.app.presenter.ui.elements.basic.button.ButtonIconTopBar

@Composable
fun FormulaTopBar(
    tittle: String,
    onBack: () -> Unit,
    onCopy: () -> Unit,
    listState: LazyListState,
){
    ScreenTopBar(
        tittle = tittle,
        listState = listState,
        onBack = onBack,
        toolButtons = {
            ButtonIconTopBar(
                imageRes = R.drawable.btn_copy,
                contentDescriptionRes = R.string.copy,
                onClick = onCopy
            )
        }
    )
}