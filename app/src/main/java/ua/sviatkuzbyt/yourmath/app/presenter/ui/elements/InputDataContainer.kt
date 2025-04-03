package ua.sviatkuzbyt.yourmath.app.presenter.ui.elements

import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import ua.sviatkuzbyt.yourmath.app.R
import ua.sviatkuzbyt.yourmath.app.presenter.ui.elements.basic.Container
import ua.sviatkuzbyt.yourmath.app.presenter.ui.elements.basic.TextFieldWithLabel
import ua.sviatkuzbyt.yourmath.app.presenter.ui.theme.AppSizes

@Composable
fun InputDataContainer(
    label: String,
    data: String,
    hint: String?,
    onDataChange: (String) -> Unit
){
    Container {
        TextFieldWithLabel(
            label = label,
            hint = hint ?: stringResource(R.string.enter_data),
            data = data,
            onDataChange = onDataChange,
            modifier = Modifier.padding(vertical = AppSizes.dp16)
        )
    }
}