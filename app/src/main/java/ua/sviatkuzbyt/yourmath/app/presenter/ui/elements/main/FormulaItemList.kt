package ua.sviatkuzbyt.yourmath.app.presenter.ui.elements.main

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.Role
import ua.sviatkuzbyt.yourmath.app.R
import ua.sviatkuzbyt.yourmath.app.presenter.ui.elements.basic.button.ButtonIconContainer
import ua.sviatkuzbyt.yourmath.app.presenter.ui.elements.basic.Container
import ua.sviatkuzbyt.yourmath.app.presenter.ui.theme.AppSizes
import ua.sviatkuzbyt.yourmath.app.presenter.ui.theme.AppTheme

@Composable
private fun FormulaItemList(
    text: String,
    pinButton: @Composable () -> Unit,
    onClick: () -> Unit,
){
    Container {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .padding(vertical = AppSizes.dp8)
                .padding(start = AppSizes.dp20, end = AppSizes.dp4)
                .clickable(
                    onClick = onClick,
                    role = Role.Button,
                    interactionSource = null,
                    indication = null
                )
        ) {
            Text(
                text = text,
                style = AppTheme.types.basic,
                modifier = Modifier.weight(1f)
            )
            pinButton()
        }
    }
}

@Composable
fun FormulaNoPinItemList(
    text: String,
    onClick: () -> Unit,
    pinOnClick: () -> Unit
){
    FormulaItemList(
        text = text,
        onClick = onClick,
        pinButton = {
            ButtonIconContainer(
                imageRes = R.drawable.btn_no_pin,
                contentDescriptionRes = R.string.to_pin,
                onClick = pinOnClick
            )
        }
    )
}

@Composable
fun FormulaPinnedItemList(
    text: String,
    onClick: () -> Unit,
    unpinOnClick: () -> Unit,
){
    FormulaItemList(
        text = text,
        onClick = onClick,
        pinButton = {
            ButtonIconContainer(
                imageRes = R.drawable.btn_pinned,
                contentDescriptionRes = R.string.to_unpin,
                onClick = unpinOnClick
            )
        }
    )
}