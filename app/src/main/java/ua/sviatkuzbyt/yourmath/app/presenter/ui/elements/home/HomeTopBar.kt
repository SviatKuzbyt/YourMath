package ua.sviatkuzbyt.yourmath.app.presenter.ui.elements.home

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import ua.sviatkuzbyt.yourmath.app.R
import ua.sviatkuzbyt.yourmath.app.presenter.ui.elements.basic.ButtonTopBar
import ua.sviatkuzbyt.yourmath.app.presenter.ui.theme.AppSizes
import ua.sviatkuzbyt.yourmath.app.presenter.ui.theme.AppTheme

@Composable
fun HomeTopBar(
    historyOnClick: () -> Unit,
    editOnClick: () -> Unit
){
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = AppSizes.dp16)
            .padding(bottom = AppSizes.dp8)
    ) {
        Text(
            text = stringResource(R.string.app_name),
            style = AppTheme.types.tittle,
            modifier = Modifier.weight(1f)
        )

        ButtonTopBar(
            imageRes = R.drawable.btn_history,
            contentDescriptionRes = R.string.history,
            onClick = historyOnClick
        )

        ButtonTopBar(
            imageRes = R.drawable.btn_edit,
            contentDescriptionRes = R.string.editor,
            onClick = editOnClick
        )
    }
}