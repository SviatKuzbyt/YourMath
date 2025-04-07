package ua.sviatkuzbyt.yourmath.app.presenter.ui.elements.history

import androidx.compose.runtime.Composable
import ua.sviatkuzbyt.yourmath.app.R
import ua.sviatkuzbyt.yourmath.app.presenter.ui.elements.basic.button.ButtonIconTopBar

@Composable
fun FilterButton(
    onClick: () -> Unit
) {
    ButtonIconTopBar(
        imageRes = R.drawable.btn_filter,
        contentDescriptionRes = R.string.open_filters,
        onClick = onClick
    )
}