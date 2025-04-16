package ua.sviatkuzbyt.yourmath.app.presenter.screens.editformula

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import ua.sviatkuzbyt.yourmath.app.R
import ua.sviatkuzbyt.yourmath.app.presenter.ui.elements.basic.ScreenTopBar

@Composable
fun EditFormulaScreen(){
    EditFormulaContent()
}

@Composable
fun EditFormulaContent(){
    val listState = rememberLazyListState()
    Column(Modifier.fillMaxSize()) {
        ScreenTopBar(
            tittle = stringResource(R.string.edit_formula),
            listState = listState,
            onBack = {}
        )
    }
}