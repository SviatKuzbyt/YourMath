package ua.sviatkuzbyt.yourmath.app.presenter.ui.elements.editformula

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyItemScope
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardCapitalization
import ua.sviatkuzbyt.yourmath.app.R
import ua.sviatkuzbyt.yourmath.app.presenter.controllers.editformula.EditFormulaStateContent
import ua.sviatkuzbyt.yourmath.app.presenter.other.editformula.saveField
import ua.sviatkuzbyt.yourmath.app.presenter.other.editformula.setFieldChanged
import ua.sviatkuzbyt.yourmath.app.presenter.ui.elements.basic.Container
import ua.sviatkuzbyt.yourmath.app.presenter.ui.elements.basic.text.TextFieldWithLabel
import ua.sviatkuzbyt.yourmath.app.presenter.ui.theme.AppSizes
import ua.sviatkuzbyt.yourmath.app.presenter.ui.theme.AppTheme

@Composable
fun LazyItemScope.InfoItems(
    info: EditFormulaStateContent.Info,
    onNameChange: (String) -> Unit,
    onDescriptionChange: (String) -> Unit,
    onNameSave: () -> Unit,
    onDescriptionSave: () -> Unit,
    onChangeIsNote: (Boolean) -> Unit
){
    val isLabelFocused = remember { mutableStateOf(false) }
    val isDescriptionFocused = remember { mutableStateOf(false) }
    val isLabelChanged = remember { mutableStateOf(false) }
    val isDescriptionChanged = remember { mutableStateOf(false) }

    Column(modifier = Modifier.padding(horizontal = AppSizes.dp16).animateItem()) {
        Container {
            TextFieldWithLabel(
                label = stringResource(R.string.name),
                text = info.name,
                hint = stringResource(R.string.enter_name),
                keyboardOptions = KeyboardOptions(
                    capitalization = KeyboardCapitalization.Sentences
                ),
                onTextChange = { text ->
                    onNameChange(text)
                    setFieldChanged(isLabelChanged)
                },
                modifier = Modifier
                    .padding(AppSizes.dp16)
                    .onFocusChanged { state ->
                        saveField(
                            focusState = state,
                            isFocused = isLabelFocused,
                            isChanged = isLabelChanged,
                            onSave = onNameSave
                        )
                    }
            )
        }

        Container {
            TextFieldWithLabel(
                label = stringResource(R.string.description),
                text = info.description.orEmpty(),
                hint = stringResource(R.string.optional),
                onTextChange = { text ->
                    onDescriptionChange(text)
                    setFieldChanged(isDescriptionChanged)
                },
                keyboardOptions = KeyboardOptions(
                    capitalization = KeyboardCapitalization.Sentences
                ),
                singleLine = false,
                modifier = Modifier
                    .padding(AppSizes.dp16)
                    .onFocusChanged { state ->
                        saveField(
                            focusState = state,
                            isFocused = isDescriptionFocused,
                            isChanged = isDescriptionChanged,
                            onSave = onDescriptionSave
                        )
                    }
            )
        }

        Container{
            Row(
                modifier = Modifier
                    .padding(vertical = AppSizes.dp16)
                    .padding(start = AppSizes.dp8, end = AppSizes.dp16),
                verticalAlignment = Alignment.CenterVertically
            )  {
                Checkbox(
                    checked = info.isNote,
                    onCheckedChange = onChangeIsNote,
                    colors = CheckboxDefaults.colors(
                        checkmarkColor = AppTheme.colors.white,
                        uncheckedColor = AppTheme.colors.textPrimary,
                        checkedColor = AppTheme.colors.primary
                    )
                )

                Spacer(Modifier.size(AppSizes.dp8))

                Text(
                    text = stringResource(R.string.mark_as_note),
                    style = AppTheme.types.basic
                )
            }
        }
    }
}