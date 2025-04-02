package ua.sviatkuzbyt.yourmath.app.presenter.ui.elements.basic

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.lazy.LazyItemScope
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier


@Composable
fun LazyItemScope.AnimateListItem(
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit
){
    Box(modifier = modifier.animateItem()){
        content()
    }
}