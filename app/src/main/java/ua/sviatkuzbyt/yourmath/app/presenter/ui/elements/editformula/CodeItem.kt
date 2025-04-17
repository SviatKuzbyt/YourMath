package ua.sviatkuzbyt.yourmath.app.presenter.ui.elements.editformula

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyItemScope
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import ua.sviatkuzbyt.yourmath.app.R
import ua.sviatkuzbyt.yourmath.app.presenter.ui.elements.basic.Container
import ua.sviatkuzbyt.yourmath.app.presenter.ui.elements.basic.text.TextFieldWithLabel
import ua.sviatkuzbyt.yourmath.app.presenter.ui.theme.AppSizes

@Composable
fun LazyItemScope.CodeItem(
    text: String,
    onTextChange: (String) -> Unit,
){
    Column(modifier = Modifier.padding(horizontal = AppSizes.dp16).animateItem()) {
        Container {
            TextFieldWithLabel(
                label = stringResource(R.string.python),
                text = text,
                singleLine = false,
                hint = stringResource(R.string.enter_name),
                onTextChange = onTextChange,
                modifier = Modifier.padding(AppSizes.dp16),
            )
        }
    }
}

