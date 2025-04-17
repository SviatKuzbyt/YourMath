package ua.sviatkuzbyt.yourmath.app.presenter.ui.elements.basic

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.ui.Modifier
import ua.sviatkuzbyt.yourmath.app.presenter.ui.theme.AppSizes

fun LazyListScope.emptySpaceOfButton(){
    item {
        Spacer(Modifier.height(AppSizes.dp72))
    }
}