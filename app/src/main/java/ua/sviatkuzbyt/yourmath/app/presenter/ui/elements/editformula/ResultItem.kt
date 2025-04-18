package ua.sviatkuzbyt.yourmath.app.presenter.ui.elements.editformula

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyItemScope
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import ua.sviatkuzbyt.yourmath.app.R
import ua.sviatkuzbyt.yourmath.app.presenter.ui.elements.basic.Container
import ua.sviatkuzbyt.yourmath.app.presenter.ui.elements.basic.text.TextFieldWithLabel
import ua.sviatkuzbyt.yourmath.app.presenter.ui.theme.AppSizes
import ua.sviatkuzbyt.yourmath.domain.structures.editformula.EditInput
import ua.sviatkuzbyt.yourmath.domain.structures.editformula.EditResult

@Composable
fun LazyItemScope.ResultItem(
    result: EditResult,
    onLabelChange: (String) -> Unit,
    onCodeLabelChange: (String) -> Unit,
    onDelete: () -> Unit,
    onMoveUp: () -> Unit,
    onMoveDown: () -> Unit
){
    Container(Modifier.padding(horizontal = AppSizes.dp16).animateItem()) {
        Column {
            ItemHead(onDelete, onMoveUp, onMoveDown)

            TextFieldWithLabel(
                label = stringResource(R.string.display_label),
                text = result.label,
                hint = stringResource(R.string.enter_name),
                onTextChange = onLabelChange,
                keyboardOptions = KeyboardOptions(
                    capitalization = KeyboardCapitalization.Sentences
                ),
                modifier = Modifier
                    .padding(top = AppSizes.dp8, bottom = AppSizes.dp16)
                    .padding(horizontal = AppSizes.dp16)
            )

            TextFieldWithLabel(
                label = stringResource(R.string.code_label),
                text = result.codeLabel,
                hint = stringResource(R.string.enter_name),
                onTextChange = onCodeLabelChange,
                modifier = Modifier
                    .padding(bottom = AppSizes.dp16)
                    .padding(horizontal = AppSizes.dp16)
            )
        }
    }
}