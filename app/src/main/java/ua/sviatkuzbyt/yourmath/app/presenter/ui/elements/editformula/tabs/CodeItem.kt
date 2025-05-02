package ua.sviatkuzbyt.yourmath.app.presenter.ui.elements.editformula.tabs

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyItemScope
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.res.stringResource
import ua.sviatkuzbyt.yourmath.app.R
import ua.sviatkuzbyt.yourmath.app.presenter.other.editformula.saveField
import ua.sviatkuzbyt.yourmath.app.presenter.other.editformula.setFieldChanged
import ua.sviatkuzbyt.yourmath.app.presenter.ui.elements.basic.Container
import ua.sviatkuzbyt.yourmath.app.presenter.ui.elements.basic.text.TextFieldWithLabel
import ua.sviatkuzbyt.yourmath.app.presenter.ui.theme.AppSizes

@Composable
fun LazyItemScope.CodeItem(
    text: String,
    onTextChange: (String) -> Unit,
    onSaveText: () -> Unit
){
    val isTextFocused = remember { mutableStateOf(false) }
    val isTextChanged = remember { mutableStateOf(false) }

    Column(modifier = Modifier.padding(horizontal = AppSizes.dp16).animateItem()) {
        Container {
            TextFieldWithLabel(
                label = stringResource(R.string.python),
                text = text,
                maxLines = 16,
                singleLine = false,
                hint = stringResource(R.string.enter_code),
                onTextChange = { text ->
                    onTextChange(text)
                    setFieldChanged(isTextChanged)
                },
                modifier = Modifier
                    .padding(AppSizes.dp16)
                    .onFocusChanged { state ->
                        saveField(
                            focusState = state,
                            isFocused = isTextFocused,
                            isChanged = isTextChanged,
                            onSave = onSaveText
                        )
                    }
            )
        }
    }
}

