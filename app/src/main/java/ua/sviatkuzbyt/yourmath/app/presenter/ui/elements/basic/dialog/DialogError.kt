package ua.sviatkuzbyt.yourmath.app.presenter.ui.elements.basic.dialog

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.BasicAlertDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import ua.sviatkuzbyt.yourmath.app.R
import ua.sviatkuzbyt.yourmath.app.presenter.other.ErrorData
import ua.sviatkuzbyt.yourmath.app.presenter.ui.theme.AppShapes
import ua.sviatkuzbyt.yourmath.app.presenter.ui.theme.AppSizes
import ua.sviatkuzbyt.yourmath.app.presenter.ui.theme.AppTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DialogError(
    data: ErrorData,
    onCloseClick: () -> Unit
) {
    BasicAlertDialog(onDismissRequest = onCloseClick) {
        Column(modifier = Modifier
            .fillMaxWidth()
            .background(
                color = AppTheme.colors.container,
                shape = AppShapes.main
            )
            .padding(horizontal = AppSizes.dp20)
            .padding(top = AppSizes.dp20)
            .padding(bottom = AppSizes.dp8)

        ) {
            Text(
                text = stringResource(data.tittleRes),
                style = AppTheme.types.dialogTittle
            )

            Spacer(Modifier.height(AppSizes.dp8))

            Text(
                text = stringResource(data.detailRes),
                style = AppTheme.types.basic
            )

            Spacer(Modifier.height(AppSizes.dp4))

            DialogButton(
                textRes = R.string.ok,
                modifier = Modifier.align(Alignment.End),
                onClick = onCloseClick
            )
        }
    }
}