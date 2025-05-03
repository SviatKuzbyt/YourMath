package ua.sviatkuzbyt.yourmath.app.presenter.ui.elements.editformula.screen

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import ua.sviatkuzbyt.yourmath.app.R
import ua.sviatkuzbyt.yourmath.app.presenter.controllers.editformula.EditFormulaStateContent
import ua.sviatkuzbyt.yourmath.app.presenter.controllers.editformula.FormulaListText
import ua.sviatkuzbyt.yourmath.app.presenter.controllers.editformula.FormulaText
import ua.sviatkuzbyt.yourmath.app.presenter.other.basic.EmptyScreenInfo
import ua.sviatkuzbyt.yourmath.app.presenter.ui.elements.basic.list.EmptyScreenInListFullSize
import ua.sviatkuzbyt.yourmath.app.presenter.ui.elements.basic.list.emptySpaceOfButton
import ua.sviatkuzbyt.yourmath.app.presenter.ui.elements.basic.text.TittleText
import ua.sviatkuzbyt.yourmath.app.presenter.ui.elements.editformula.items.CodeItem
import ua.sviatkuzbyt.yourmath.app.presenter.ui.elements.editformula.items.InfoItems
import ua.sviatkuzbyt.yourmath.app.presenter.ui.elements.editformula.items.InputItem
import ua.sviatkuzbyt.yourmath.app.presenter.ui.elements.editformula.items.ResultItem
import ua.sviatkuzbyt.yourmath.app.presenter.ui.theme.AppSizes

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun EditFormulaList(
    listState: LazyListState,
    tabs: List<Int>,
    selectedTab: Int,
    content: EditFormulaStateContent,
    onSelectTab: (Int) -> Unit,
    onChangeText: (FormulaText, String) -> Unit,
    onChangeIsNote: (Boolean) -> Unit,
    onSaveText: (FormulaText) -> Unit,
    onListChangeText: (FormulaListText, Int, String) -> Unit,
    onListSaveText: (FormulaListText, Int) -> Unit,
    onListMove: (Int, Int) -> Unit,
    onListDelete: (Int) -> Unit
){
    LazyColumn(modifier = Modifier.fillMaxSize(), state = listState) {
        item { Tittle() }

        stickyHeader {
            ScreenTabs(
                tabs = tabs,
                selectedTab = selectedTab,
                onSelectTab = onSelectTab
            )
        }

        when(content){
            is EditFormulaStateContent.Info -> item("info") {
                InfoItems(
                    info = content,
                    onNameChange = { text -> onChangeText(FormulaText.Name, text) },
                    onDescriptionChange = { text -> onChangeText(FormulaText.Description, text) },
                    onNameSave = { onSaveText(FormulaText.Name) },
                    onDescriptionSave = { onSaveText(FormulaText.Description) },
                    onChangeIsNote = onChangeIsNote
                )
            }

            is EditFormulaStateContent.Inputs -> {
                if (content.list.isEmpty()){
                    item(key = "empty") {
                        EmptyScreenInListFullSize(EmptyScreenInfo.noEditRecords())
                    }
                } else {
                    itemsIndexed(
                        items = content.list,
                        key = { _, input -> "input${input.id}" }
                    ){ index, input ->
                        InputItem(
                            input = input,
                            onLabelChange = { text ->
                                onListChangeText(FormulaListText.InputLabel, index, text)
                            },
                            onCodeLabelChange = { text ->
                                onListChangeText(FormulaListText.InputCodeLabel, index, text)
                            },
                            onDefaultDataChange = { text ->
                                onListChangeText(FormulaListText.InputDefaultData, index, text)
                            },
                            onLabelSave = {
                                onListSaveText(FormulaListText.InputLabel, index)
                            },
                            onCodeLabelSave = {
                                onListSaveText(FormulaListText.InputCodeLabel, index)
                            },
                            onDefaultDataSave = {
                                onListSaveText(FormulaListText.InputDefaultData, index)
                            },
                            onDelete = { onListDelete(index) },
                            onMoveDown = { onListMove(index, index + 1) },
                            onMoveUp = { onListMove(index, index - 1) }
                        )
                    }

                    emptySpaceOfButton()
                }
            }

            is EditFormulaStateContent.Results -> {
                if (content.list.isEmpty()){
                    item(key = "empty") {
                        EmptyScreenInListFullSize(EmptyScreenInfo.noEditRecords())
                    }
                } else{
                    itemsIndexed(
                        items = content.list,
                        key = {_, result -> "result${result.id}"}
                    ){ index, result ->
                        ResultItem(
                            result = result,
                            onLabelChange = { text ->
                                onListChangeText(FormulaListText.ResultLabel, index, text)
                            },
                            onCodeLabelChange = { text ->
                                onListChangeText(FormulaListText.ResultCodeLabel, index, text)
                            },
                            onLabelSave = {
                                onListSaveText(FormulaListText.ResultLabel, index)
                            },
                            onCodeLabelSave = {
                                onListSaveText(FormulaListText.ResultCodeLabel, index)
                            },
                            onDelete = { onListDelete(index) },
                            onMoveDown = { onListMove(index, index + 1) },
                            onMoveUp = { onListMove(index, index - 1) }
                        )
                    }
                    emptySpaceOfButton()
                }
            }

            is EditFormulaStateContent.Code -> {
                item {
                    CodeItem(
                        text = content.text,
                        onTextChange = { text -> onChangeText(FormulaText.Code, text) },
                        onSaveText = { onSaveText(FormulaText.Code) }
                    )
                }
            }

            EditFormulaStateContent.Nothing -> Unit
        }
    }
}

@Composable
private fun Tittle(){
    TittleText(
        textRes = R.string.edit_formula,
        padding = PaddingValues(
            start = AppSizes.dp16,
            end = AppSizes.dp16,
            bottom = AppSizes.dp16
        )
    )
}