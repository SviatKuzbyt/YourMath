package ua.sviatkuzbyt.yourmath.app.presenter.ui.elements.basic

import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import ua.sviatkuzbyt.yourmath.app.R
import ua.sviatkuzbyt.yourmath.app.presenter.ui.elements.basic.button.ButtonIconTopBar
import ua.sviatkuzbyt.yourmath.app.presenter.ui.theme.AppSizes
import ua.sviatkuzbyt.yourmath.app.presenter.ui.theme.AppTheme

@Composable
fun ScreenTopBar(
    tittle: String,
    listState: LazyListState,
    onBack: () -> Unit,
    modifier: Modifier = Modifier,
    toolButtons: @Composable RowScope.() -> Unit = {},

){
    val isFirstItemHide by remember {
        derivedStateOf {
            listState.firstVisibleItemIndex > 0
        }
    }
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(start = AppSizes.dp4, end = AppSizes.dp16),
        verticalAlignment = Alignment.CenterVertically
    ) {
        ButtonIconTopBar(
            imageRes = R.drawable.btn_back,
            contentDescriptionRes = R.string.back,
            onClick = onBack
        )

        Box(modifier = Modifier.weight(1f)){
            androidx.compose.animation.AnimatedVisibility(
                visible = isFirstItemHide,
                enter = fadeIn(),
                exit = fadeOut()
            ) {
                Text(
                    text = tittle,
                    style = AppTheme.types.bold,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }
        }

        toolButtons()
    }
}