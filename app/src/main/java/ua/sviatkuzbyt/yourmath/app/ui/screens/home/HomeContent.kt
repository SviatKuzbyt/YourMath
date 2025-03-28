package ua.sviatkuzbyt.yourmath.app.ui.screens.home

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import ua.sviatkuzbyt.yourmath.app.ui.elements.home.FieldSearch
import ua.sviatkuzbyt.yourmath.app.ui.elements.home.HomeTopBar

@Composable
fun HomeContent(){
    var textTemp by remember { mutableStateOf("") }
    Column(modifier = Modifier.fillMaxSize().padding()){
        HomeTopBar(
            historyOnClick = {},
            editOnClick = {}
        )

        FieldSearch(textTemp){
            textTemp = it
        }

        LazyColumn(Modifier.fillMaxSize()) {
            items(5){
                FormulaPinnedItemList(
                    text = "Item ${it+1}",
                    onClick = {},
                    unpinOnClick = {}
                )
            }
            items(10){
                FormulaNoPinItemList(
                    text = "Item ${it+6}",
                    onClick = {},
                    pinOnClick = {}
                )
            }
        }
    }
}