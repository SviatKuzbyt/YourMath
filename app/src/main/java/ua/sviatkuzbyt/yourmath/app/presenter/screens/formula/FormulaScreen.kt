package ua.sviatkuzbyt.yourmath.app.presenter.screens.formula

import androidx.compose.animation.Crossfade
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import ua.sviatkuzbyt.yourmath.app.R
import ua.sviatkuzbyt.yourmath.app.presenter.controllers.formula.FormulaIntent
import ua.sviatkuzbyt.yourmath.app.presenter.controllers.formula.FormulaState
import ua.sviatkuzbyt.yourmath.app.presenter.navigation.LocalNavController
import ua.sviatkuzbyt.yourmath.app.presenter.navigation.NavigateIntent
import ua.sviatkuzbyt.yourmath.app.presenter.navigation.onNavigateIntent
import ua.sviatkuzbyt.yourmath.app.presenter.ui.elements.basic.AnimateListItem
import ua.sviatkuzbyt.yourmath.app.presenter.ui.elements.basic.button.ButtonLarge
import ua.sviatkuzbyt.yourmath.app.presenter.ui.elements.basic.button.ButtonLargeLoad
import ua.sviatkuzbyt.yourmath.app.presenter.ui.elements.formula.InputDataContainer
import ua.sviatkuzbyt.yourmath.app.presenter.ui.elements.basic.ScreenTopBar
import ua.sviatkuzbyt.yourmath.app.presenter.ui.elements.basic.text.SubTittleText
import ua.sviatkuzbyt.yourmath.app.presenter.ui.elements.basic.text.TittleText
import ua.sviatkuzbyt.yourmath.app.presenter.ui.elements.basic.dialog.ShowDialogError
import ua.sviatkuzbyt.yourmath.app.presenter.ui.elements.formula.CopyButton
import ua.sviatkuzbyt.yourmath.app.presenter.ui.elements.formula.ResultDataContainer
import ua.sviatkuzbyt.yourmath.app.presenter.ui.theme.AppSizes
import ua.sviatkuzbyt.yourmath.app.presenter.ui.theme.AppTheme
import ua.sviatkuzbyt.yourmath.domain.structures.formula.FormulaInput
import ua.sviatkuzbyt.yourmath.domain.structures.formula.FormulaResult

@Composable
fun FormulaScreen(viewModel: FormulaViewModel) {
    val screenState by viewModel.screenState.collectAsState()
    val navController = LocalNavController.current

    FormulaContent(
        screenState = screenState,
        onIntent = viewModel::onIntent,
        onNavigate = { onNavigateIntent(navController, it) }
    )
}

@Composable
fun FormulaContent(
    screenState: FormulaState,
    onIntent: (FormulaIntent) -> Unit,
    onNavigate: (NavigateIntent) -> Unit
){
    val listState = rememberLazyListState()
    val focusManager = LocalFocusManager.current

    Column(modifier = Modifier.fillMaxSize()) {
        TopBar(
            tittle = screenState.content.info.label,
            listState = listState,
            onBack = {
                onNavigate(NavigateIntent.NavigateBack)
            },
            onCopy = {
                onIntent(FormulaIntent.CopyFormulaToClipboard)
            }
        )

        ContentList(
            label = screenState.content.info.label,
            description = screenState.content.info.description,
            inputList = screenState.content.inputData,
            resultList = screenState.content.resultData,
            listState = listState,
            focusManager = focusManager,
            onInputDone = {
                onIntent(FormulaIntent.MathFormula)
            },
            onInputDataChange = { position, text ->
                onIntent(FormulaIntent.ChangeInputData(position, text))
            },
            onCopyResultText = { text ->
                onIntent(FormulaIntent.CopyTextToClipboard(text))
            }
        )

        MathButton(
            isLoading = screenState.isLoading,
            onClick = {
                onIntent(FormulaIntent.MathFormula)
            }
        )
    }

    ShowDialogError(
        errorData = screenState.errorMessage,
        onCloseClick = {
            onIntent(FormulaIntent.CloseDialog)
        }
    )
}

@Composable
private fun TopBar(
    tittle: String,
    onBack: () -> Unit,
    onCopy: () -> Unit,
    listState: LazyListState,
){
    ScreenTopBar(
        tittle = tittle,
        listState = listState,
        onBack = onBack,
        toolButtons = {
            CopyButton(onCopy)
        }
    )
}

@Composable
private fun ColumnScope.ContentList(
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
                    data = inputData.data,
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

@Composable
private fun MathButton(
    isLoading: Boolean,
    onClick: () -> Unit
){
    Crossfade(isLoading){
        if (it){
            ButtonLargeLoad()
        } else {
            ButtonLarge(
                textRes = R.string.math,
                onClick = onClick,
                modifier = Modifier.padding(AppSizes.dp16)
            )
        }
    }
}