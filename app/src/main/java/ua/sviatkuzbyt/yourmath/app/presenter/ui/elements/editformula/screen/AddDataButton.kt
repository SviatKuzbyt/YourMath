package ua.sviatkuzbyt.yourmath.app.presenter.ui.elements.editformula.screen

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import ua.sviatkuzbyt.yourmath.app.R
import ua.sviatkuzbyt.yourmath.app.presenter.ui.elements.basic.button.AddButton

@Composable
fun BoxScope.AddDataButton(
    isShow: Boolean,
    onClick: () -> Unit
){
    AnimatedVisibility(
        visible = isShow,
        enter = fadeIn(),
        exit = fadeOut(),
        modifier = Modifier.align(Alignment.BottomEnd)
    ) {
        AddButton(
            descriptionRes = R.string.add_item,
            onClick = onClick
        )
    }
}