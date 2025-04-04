package ua.sviatkuzbyt.yourmath.app.presenter.ui.elements.formula

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import ua.sviatkuzbyt.yourmath.app.presenter.ui.elements.basic.Container
import ua.sviatkuzbyt.yourmath.app.presenter.ui.theme.AppSizes
import ua.sviatkuzbyt.yourmath.app.presenter.ui.theme.AppTheme

@Composable
fun ResultDataContainer(
    title: String,
    content: String
) {
    Container {
        Column(
            modifier = Modifier.padding(AppSizes.dp16)
        ) {
            Text(
                text = title,
                style = AppTheme.types.basic,
                modifier = Modifier.padding(bottom = AppSizes.dp4)
            )

            Text(
                text = content,
                style = AppTheme.types.bold
            )
        }
    }
}