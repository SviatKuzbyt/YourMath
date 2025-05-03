package ua.sviatkuzbyt.yourmath.app.presenter.ui.elements.basic.dialog

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.BasicAlertDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import ua.sviatkuzbyt.yourmath.app.presenter.ui.theme.AppShapes
import ua.sviatkuzbyt.yourmath.app.presenter.ui.theme.AppSizes
import ua.sviatkuzbyt.yourmath.app.presenter.ui.theme.AppTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DialogBasic(
    modifier: Modifier = Modifier,
    tittle: String,
    content: String,
    onClose: () -> Unit = {},
    buttons: @Composable RowScope.() -> Unit
) {
    BasicAlertDialog(onDismissRequest = onClose) {
        //Background
        Column(modifier = modifier
            .fillMaxWidth()
            .background(
                color = AppTheme.colors.container,
                shape = AppShapes.main
            )
            .padding(horizontal = AppSizes.dp20)
            .padding(top = AppSizes.dp20)
            .padding(bottom = AppSizes.dp8)

        ) {
            //Tittle
            Text(
                text = tittle,
                style = AppTheme.types.dialogTittle
            )

            Spacer(Modifier.height(AppSizes.dp8))

            //Content
            Text(
                text = content,
                style = AppTheme.types.basic
            )

            Spacer(Modifier.height(AppSizes.dp4))

            //Buttons
            Row(
                horizontalArrangement = Arrangement.End,
                modifier = Modifier.fillMaxWidth()
            ) {
                buttons()
            }
        }
    }
}