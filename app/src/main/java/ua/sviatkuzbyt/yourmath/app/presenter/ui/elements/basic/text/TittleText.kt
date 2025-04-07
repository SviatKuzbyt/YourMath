package ua.sviatkuzbyt.yourmath.app.presenter.ui.elements.basic.text

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import ua.sviatkuzbyt.yourmath.app.presenter.ui.theme.AppSizes
import ua.sviatkuzbyt.yourmath.app.presenter.ui.theme.AppTheme

@Composable
fun TittleText(
    text: String,
    modifier: Modifier = Modifier
){
    Text(
        text = text,
        style = AppTheme.types.tittle,
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = AppSizes.dp16)
            .padding(bottom = AppSizes.dp8)
    )
}