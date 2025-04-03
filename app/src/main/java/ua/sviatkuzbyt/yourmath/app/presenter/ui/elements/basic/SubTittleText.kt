package ua.sviatkuzbyt.yourmath.app.presenter.ui.elements.basic

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import ua.sviatkuzbyt.yourmath.app.presenter.ui.theme.AppSizes
import ua.sviatkuzbyt.yourmath.app.presenter.ui.theme.AppTheme

@Composable
fun SubTittleText(
    text: String,
    modifier: Modifier = Modifier
){
    Text(
        text = text,
        style = AppTheme.types.bold,
        modifier = modifier
            .padding(bottom = AppSizes.dp8)
            .padding(horizontal = AppSizes.dp16)
    )
}

@Composable
fun SubTittleText(
    @StringRes textRes: Int,
    modifier: Modifier = Modifier
) {
    SubTittleText(
        text = stringResource(textRes),
        modifier = modifier
    )
}