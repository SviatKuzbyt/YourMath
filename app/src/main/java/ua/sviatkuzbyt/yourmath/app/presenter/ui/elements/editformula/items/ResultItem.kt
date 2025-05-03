package ua.sviatkuzbyt.yourmath.app.presenter.ui.elements.editformula.items

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyItemScope
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardCapitalization
import ua.sviatkuzbyt.yourmath.app.R
import ua.sviatkuzbyt.yourmath.app.presenter.other.editformula.saveField
import ua.sviatkuzbyt.yourmath.app.presenter.other.editformula.setFieldChanged
import ua.sviatkuzbyt.yourmath.app.presenter.ui.elements.basic.Container
import ua.sviatkuzbyt.yourmath.app.presenter.ui.elements.basic.text.TextFieldWithLabel
import ua.sviatkuzbyt.yourmath.app.presenter.ui.elements.editformula.component.ItemHead
import ua.sviatkuzbyt.yourmath.app.presenter.ui.theme.AppSizes
import ua.sviatkuzbyt.yourmath.domain.structures.editformula.EditResult

@Composable
fun LazyItemScope.ResultItem(
    result: EditResult,
    onLabelChange: (String) -> Unit,
    onCodeLabelChange: (String) -> Unit,
    onDelete: () -> Unit,
    onMoveUp: () -> Unit,
    onMoveDown: () -> Unit,
    onLabelSave: () -> Unit,
    onCodeLabelSave: () -> Unit,
){
    val isTextLabelFocused = remember { mutableStateOf(false) }
    val isTextLabelChanged = remember { mutableStateOf(false) }
    val isCodeLabelFocused = remember { mutableStateOf(false) }
    val isCodeLabelChanged = remember { mutableStateOf(false) }

    Container(Modifier.padding(horizontal = AppSizes.dp16).animateItem()) {
        Column {
            ItemHead(onDelete, onMoveUp, onMoveDown)

            TextFieldWithLabel(
                label = stringResource(R.string.display_label),
                text = result.label,
                hint = stringResource(R.string.enter_name),
                onTextChange = { text ->
                    onLabelChange(text)
                    setFieldChanged(isTextLabelChanged)
                },
                keyboardOptions = KeyboardOptions(
                    capitalization = KeyboardCapitalization.Sentences
                ),
                modifier = Modifier
                    .padding(top = AppSizes.dp8, bottom = AppSizes.dp16)
                    .padding(horizontal = AppSizes.dp16)
                    .onFocusChanged { state ->
                        saveField(
                            focusState = state,
                            isFocused = isTextLabelFocused,
                            isChanged = isTextLabelChanged,
                            onSave = onLabelSave
                        )
                    }
            )

            TextFieldWithLabel(
                label = stringResource(R.string.code_label),
                text = result.codeLabel,
                hint = stringResource(R.string.enter_name),
                onTextChange = { text ->
                    onCodeLabelChange(text)
                    setFieldChanged(isCodeLabelChanged)
                },
                modifier = Modifier
                    .padding(bottom = AppSizes.dp16)
                    .padding(horizontal = AppSizes.dp16)
                    .onFocusChanged { state ->
                        saveField(
                            focusState = state,
                            isFocused = isCodeLabelFocused,
                            isChanged = isCodeLabelChanged,
                            onSave = onCodeLabelSave
                        )
                    }
            )
        }
    }
}