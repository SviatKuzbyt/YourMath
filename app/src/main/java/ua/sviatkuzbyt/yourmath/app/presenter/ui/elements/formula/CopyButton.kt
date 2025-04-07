package ua.sviatkuzbyt.yourmath.app.presenter.ui.elements.formula

import androidx.compose.runtime.Composable
import ua.sviatkuzbyt.yourmath.app.R
import ua.sviatkuzbyt.yourmath.app.presenter.ui.elements.basic.button.ButtonIconTopBar

@Composable
fun CopyButton(
    onClick: () -> Unit
) {
    ButtonIconTopBar(
        imageRes = R.drawable.btn_copy,
        contentDescriptionRes = R.string.copy,
        onClick = onClick
    )
}