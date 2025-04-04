package ua.sviatkuzbyt.yourmath.app.presenter.screens.formula

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import ua.sviatkuzbyt.yourmath.app.R
import ua.sviatkuzbyt.yourmath.app.presenter.controllers.formula.FormulaIntent
import ua.sviatkuzbyt.yourmath.app.presenter.controllers.formula.FormulaState
import ua.sviatkuzbyt.yourmath.app.presenter.controllers.main.MainIntent
import ua.sviatkuzbyt.yourmath.app.presenter.navigation.LocalNavController
import ua.sviatkuzbyt.yourmath.app.presenter.navigation.NavigateIntent
import ua.sviatkuzbyt.yourmath.app.presenter.navigation.onNavigateIntent
import ua.sviatkuzbyt.yourmath.app.presenter.ui.elements.basic.ButtonTextPrimary
import ua.sviatkuzbyt.yourmath.app.presenter.ui.elements.formula.InputDataContainer
import ua.sviatkuzbyt.yourmath.app.presenter.ui.elements.basic.ScreenTopBar
import ua.sviatkuzbyt.yourmath.app.presenter.ui.elements.basic.SubTittleText
import ua.sviatkuzbyt.yourmath.app.presenter.ui.elements.basic.dialog.DialogError
import ua.sviatkuzbyt.yourmath.app.presenter.ui.elements.formula.ResultDataContainer
import ua.sviatkuzbyt.yourmath.app.presenter.ui.theme.AppSizes
import ua.sviatkuzbyt.yourmath.app.presenter.ui.theme.AppTheme

@Composable
fun FormulaScreen(viewModel: FormulaViewModel) {
    val screenState by viewModel.screenState.collectAsState()
    val navController = LocalNavController.current

    FormulaContent(
        screenState = screenState,
        onIntent = viewModel::onIntent,
        onNavigate = { onNavigateIntent(navController, it) })
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
        ScreenTopBar(
            tittle = screenState.content.info.label,
            listState = listState,
            onBack = { onNavigate(NavigateIntent.NavigateUp) }
        )

        LazyColumn(
            state = listState,
            modifier = Modifier.weight(1f)
        ) {
            item {
                Text(
                    text = screenState.content.info.label,
                    style = AppTheme.types.tittle,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = AppSizes.dp16)
                )
            }

            screenState.content.info.description?.let { description ->
                item {
                    Text(
                        text = description,
                        style = AppTheme.types.secondary,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = AppSizes.dp8)
                            .padding(horizontal = AppSizes.dp16)
                    )
                }
            }

            item {
                SubTittleText(
                    textRes = R.string.input_data,
                    modifier = Modifier.padding(top = AppSizes.dp16)
                )
            }

            itemsIndexed(
                items = screenState.content.inputData,
                key = { _, inputData ->
                    inputData.id
                }
            ){ position, inputData ->
                InputDataContainer(
                    label = inputData.label,
                    data = inputData.data,
                    hint = inputData.defaultData,
                    onDataChange = { onIntent(FormulaIntent.ChangeInputData(position, it)) },
                    isDoneButton = screenState.content.inputData.lastIndex == position,
                    focusManager = focusManager
                )
            }

            if (screenState.content.resultData.isNotEmpty()){
                item {
                    SubTittleText(R.string.result)
                }

                items(screenState.content.resultData) { result ->
                    ResultDataContainer(result.label, result.data ?: stringResource(R.string.no_found))
                }
            }
        }

        ButtonTextPrimary(
            textRes = R.string.math,
            onClick = { onIntent(FormulaIntent.MathFormula) }
        )
    }

    screenState.errorMessage?.let { error ->
        DialogError(error) {
            onIntent(FormulaIntent.CloseDialog)
        }
    }
}
