package ua.sviatkuzbyt.yourmath.app.presenter.ui.elements.basic.text

import android.annotation.SuppressLint
import androidx.annotation.StringRes
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import ua.sviatkuzbyt.yourmath.app.presenter.ui.theme.AppSizes
import ua.sviatkuzbyt.yourmath.app.presenter.ui.theme.AppTheme

@Composable
fun TittleText(
    text: String,
    padding: PaddingValues = PaddingValues(bottom = AppSizes.dp16),
    @SuppressLint("ModifierParameter") modifier: Modifier = Modifier
){
    Text(
        text = text,
        style = AppTheme.types.tittle,
        modifier = modifier
            .fillMaxWidth()
            .padding(padding)
    )
}

@Composable
fun TittleText(
    @StringRes textRes: Int,
    padding: PaddingValues = PaddingValues(bottom = AppSizes.dp16),
    @SuppressLint("ModifierParameter") modifier: Modifier = Modifier
){
    TittleText(
        text = stringResource(textRes),
        padding = padding,
        modifier = modifier
    )
}