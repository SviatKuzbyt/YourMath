package ua.sviatkuzbyt.yourmath.app.presenter.ui.elements.formula

import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.res.stringResource
import ua.sviatkuzbyt.yourmath.app.R
import ua.sviatkuzbyt.yourmath.app.presenter.ui.elements.basic.AnimateListItem
import ua.sviatkuzbyt.yourmath.app.presenter.ui.elements.basic.text.SubTittleText
import ua.sviatkuzbyt.yourmath.app.presenter.ui.elements.basic.text.TittleText
import ua.sviatkuzbyt.yourmath.app.presenter.ui.theme.AppSizes
import ua.sviatkuzbyt.yourmath.app.presenter.ui.theme.AppTheme
import ua.sviatkuzbyt.yourmath.domain.structures.formula.FormulaInput
import ua.sviatkuzbyt.yourmath.domain.structures.formula.FormulaResult

@Composable
fun ColumnScope.FormulaContentList(
    label: String,
    description: String?,
    inputList: List<FormulaInput>,
    resultList: List<FormulaResult>,
    onInputDone: () -> Unit,
    onInputDataChange: (Int, String) -> Unit,
    onCopyResultText: (String) -> Unit,
    listState: LazyListState,
    focusManager: FocusManager
){
    LazyColumn(
        state = listState,
        modifier = Modifier.fillMaxWidth().weight(1f),
        contentPadding = PaddingValues(horizontal = AppSizes.dp16)
    ) {
        //Name and description
        item {
            TittleText(
                text = label ,
                padding = PaddingValues(bottom = AppSizes.dp8)
            )
        }

        if (!description.isNullOrBlank()){
            item {
                Text(
                    text = description,
                    style = AppTheme.types.secondary,
                    modifier = Modifier
                        .fillMaxWidth()
                )
            }
        }

        //Input data
        item {
            SubTittleText(
                textRes = R.string.input_data,
                modifier = Modifier.padding(top = AppSizes.dp16)
            )
        }

        itemsIndexed(
            items = inputList,
            key = { _, inputData ->
                inputData.id
            },
            itemContent = { position, inputData ->
                InputDataContainer(
                    label = inputData.label,
                    data = inputData.data ?: stringResource(R.string.no_found),
                    hint = inputData.defaultData,
                    isDoneButton = inputList.lastIndex == position,
                    focusManager = focusManager,
                    onDone = onInputDone,
                    onDataChange = {
                        onInputDataChange(position, it)
                    }
                )
            }
        )

        //Result data
        if(resultList.isNotEmpty()){
            item {
                AnimateListItem {
                    SubTittleText(R.string.results)
                }
            }

            items(resultList) { result ->
                AnimateListItem {
                    val data = result.data ?: stringResource(R.string.no_found)
                    ResultDataContainer(
                        title = result.label,
                        content = data,
                        onTextClick = {
                            onCopyResultText(data)
                        }
                    )
                }
            }
        }
    }
}