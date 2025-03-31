package ua.sviatkuzbyt.yourmath.app.ui.screens.home

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import ua.sviatkuzbyt.yourmath.app.R
import ua.sviatkuzbyt.yourmath.app.ui.elements.basic.SubTittleText
import ua.sviatkuzbyt.yourmath.app.ui.elements.home.FieldSearch
import ua.sviatkuzbyt.yourmath.app.ui.elements.home.FormulaNoPinItemList
import ua.sviatkuzbyt.yourmath.app.ui.elements.home.FormulaPinnedItemList
import ua.sviatkuzbyt.yourmath.app.ui.elements.home.HomeTopBar
import ua.sviatkuzbyt.yourmath.app.ui.intents.MainIntent
import ua.sviatkuzbyt.yourmath.app.ui.states.MainState

@Composable
fun MainContent(
    screenState: MainState,
    onIntent: (MainIntent) -> Unit
){
    Column(modifier = Modifier.fillMaxSize().padding()){
        HomeTopBar(
            historyOnClick = {},
            editOnClick = {}
        )

        FieldSearch(screenState.searchText){
            //TODO
        }

        LazyColumn(Modifier.fillMaxSize()) {
            if (screenState.formulas.pins.isNotEmpty()){
                item {
                    SubTittleText(R.string.pinned)
                }

                items(
                    items = screenState.formulas.pins,
                    key = { formula -> formula.id }
                ){ formula ->
                    FormulaPinnedItemList(
                        text = formula.name,
                        onClick = {  },
                        unpinOnClick = { onIntent(MainIntent.UnPinFormula(formula)) }
                    )
                }
            }

            if (screenState.formulas.unpins.isNotEmpty()){
                item {
                    SubTittleText(R.string.other)
                }

                items(
                    items = screenState.formulas.unpins,
                    key = { formula -> formula.id }
                ){ formula ->
                    FormulaNoPinItemList(
                        text = formula.name,
                        onClick = {  },
                        pinOnClick = { onIntent(MainIntent.PinFormula(formula)) }
                    )
                }
            }
        }
    }
}