package ua.sviatkuzbyt.yourmath.app.presenter.ui.elements.editformula

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyItemScope
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import ua.sviatkuzbyt.yourmath.app.R
import ua.sviatkuzbyt.yourmath.app.presenter.controllers.editformula.EditFormulaStateContent
import ua.sviatkuzbyt.yourmath.app.presenter.ui.elements.basic.Container
import ua.sviatkuzbyt.yourmath.app.presenter.ui.elements.basic.text.TextFieldWithLabel
import ua.sviatkuzbyt.yourmath.app.presenter.ui.theme.AppSizes

@Composable
fun LazyItemScope.InfoItems(
    info: EditFormulaStateContent.Info,
    onNameChange: (String) -> Unit,
    onDescriptionChange: (String) -> Unit
){
    Column(modifier = Modifier.padding(horizontal = AppSizes.dp16).animateItem()) {
        Container {
            TextFieldWithLabel(
                label = stringResource(R.string.name),
                text = info.name,
                hint = stringResource(R.string.enter_name),
                onTextChange = onNameChange,
                modifier = Modifier.padding(AppSizes.dp16)
            )
        }

        Container {
            TextFieldWithLabel(
                label = stringResource(R.string.description),
                text = info.description.orEmpty(),
                hint = stringResource(R.string.optional),
                onTextChange = onDescriptionChange,
                singleLine = false,
                modifier = Modifier.padding(AppSizes.dp16)
            )
        }
    }
}