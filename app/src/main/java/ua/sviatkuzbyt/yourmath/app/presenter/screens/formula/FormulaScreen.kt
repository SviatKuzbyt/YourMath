package ua.sviatkuzbyt.yourmath.app.presenter.screens.formula

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import ua.sviatkuzbyt.yourmath.app.presenter.controllers.formula.FormulaIntent
import ua.sviatkuzbyt.yourmath.app.presenter.controllers.formula.FormulaState
import ua.sviatkuzbyt.yourmath.app.presenter.navigation.LocalNavController
import ua.sviatkuzbyt.yourmath.app.presenter.navigation.NavigateIntent
import ua.sviatkuzbyt.yourmath.app.presenter.navigation.onNavigateIntent
import ua.sviatkuzbyt.yourmath.app.presenter.ui.elements.basic.dialog.ShowDialogError
import ua.sviatkuzbyt.yourmath.app.presenter.ui.elements.formula.screen.FormulaContentList
import ua.sviatkuzbyt.yourmath.app.presenter.ui.elements.formula.screen.FormulaTopBar
import ua.sviatkuzbyt.yourmath.app.presenter.ui.elements.formula.screen.MathButton

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
        FormulaTopBar(
            tittle = screenState.content.info.label,
            listState = listState,
            onBack = { onNavigate(NavigateIntent.NavigateBack) },
            onCopy = { onIntent(FormulaIntent.CopyFormulaToClipboard) }
        )

        FormulaContentList(
            label = screenState.content.info.label,
            description = screenState.content.info.description,
            inputList = screenState.content.inputData,
            resultList = screenState.content.resultData,
            listState = listState,
            focusManager = focusManager,
            onInputDone = { onIntent(FormulaIntent.MathFormula) },
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