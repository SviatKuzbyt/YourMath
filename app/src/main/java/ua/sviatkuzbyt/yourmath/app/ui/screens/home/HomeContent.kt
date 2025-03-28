package ua.sviatkuzbyt.yourmath.app.ui.screens.home

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
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
    Column(modifier = Modifier.fillMaxSize()){
        HomeTopBar(
            historyOnClick = {},
            editOnClick = {}
        )

        FieldSearch(textTemp){
            textTemp = it
        }
    }
}