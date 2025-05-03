package ua.sviatkuzbyt.yourmath.app.presenter.ui.elements.main

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import ua.sviatkuzbyt.yourmath.app.R
import ua.sviatkuzbyt.yourmath.app.presenter.controllers.main.MainIntent
import ua.sviatkuzbyt.yourmath.app.presenter.controllers.main.MainListContent
import ua.sviatkuzbyt.yourmath.app.presenter.navigation.NavigateIntent
import ua.sviatkuzbyt.yourmath.app.presenter.ui.elements.basic.list.AnimateListItem
import ua.sviatkuzbyt.yourmath.app.presenter.ui.elements.basic.list.EmptyScreenInListFullSize
import ua.sviatkuzbyt.yourmath.app.presenter.ui.elements.basic.text.SubTittleText
import ua.sviatkuzbyt.yourmath.app.presenter.ui.theme.AppSizes

@Composable
fun MainListContent(
    listContent: MainListContent,
    onIntent: (MainIntent) -> Unit,
    onNavigate: (NavigateIntent) -> Unit
) {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(horizontal = AppSizes.dp16)
    ) {
        when(listContent){
            is MainListContent.FormulaList -> {
                if (listContent.formulas.pins.isNotEmpty()) {
                    item (key = "s1") {
                        AnimateListItem { SubTittleText(R.string.pinned) }
                    }
                    items(listContent.formulas.pins, key = { it.id }) { formula ->
                        AnimateListItem {
                            FormulaPinnedItemList(
                                text = formula.name,
                                onClick = {
                                    onNavigate(NavigateIntent.OpenFormulaScreen(formula.id))
                                },
                                unpinOnClick = {
                                    onIntent(MainIntent.UnPinFormula(formula))
                                }
                            )
                        }
                    }
                }

                //Other formulas
                if (listContent.formulas.unpins.isNotEmpty()) {
                    if (listContent.formulas.pins.isNotEmpty()) {
                        item(key = "s2") { AnimateListItem { SubTittleText(R.string.other) } }
                    }
                    items(listContent.formulas.unpins, key = { it.id }) { formula ->
                        AnimateListItem {
                            FormulaNoPinItemList(
                                text = formula.name,
                                onClick = {
                                    onNavigate(NavigateIntent.OpenFormulaScreen(formula.id))
                                },
                                pinOnClick = {
                                    onIntent(MainIntent.PinFormula(formula))
                                }
                            )
                        }
                    }
                }
            }
            is MainListContent.EmptyScreen -> item(key = "e0"){
                AnimateListItem {
                    EmptyScreenInListFullSize(listContent.info)
                }
            }
            MainListContent.Nothing -> {}
        }
    }
}