package ua.sviatkuzbyt.yourmath.app.presenter.ui.elements.history.items

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.Role
import ua.sviatkuzbyt.yourmath.app.presenter.ui.elements.basic.Container
import ua.sviatkuzbyt.yourmath.app.presenter.ui.theme.AppSizes
import ua.sviatkuzbyt.yourmath.app.presenter.ui.theme.AppTheme

@Composable
fun HistoryContainer(
    formulaName: String,
    formulaData: String,
    onClick: () -> Unit
){
    Container(
        Modifier
            .clickable(
                onClick = onClick,
                role = Role.Button,
                interactionSource = null,
                indication = null
            )
    ) {
        Column(Modifier.padding(vertical = AppSizes.dp16, horizontal = AppSizes.dp20)) {
            Text(
                text = formulaName,
                style = AppTheme.types.basic,
                modifier = Modifier.padding(bottom = AppSizes.dp4)
            )

            Text(
                text = formulaData,
                style = AppTheme.types.bold
            )
        }
    }
}