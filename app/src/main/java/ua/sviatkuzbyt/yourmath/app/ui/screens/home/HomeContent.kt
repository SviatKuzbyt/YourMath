package ua.sviatkuzbyt.yourmath.app.ui.screens.home

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import ua.sviatkuzbyt.yourmath.app.ui.elements.home.HomeTopBar

@Composable
fun HomeContent(){
    Column(modifier = Modifier.fillMaxSize()){
        HomeTopBar(
            historyOnClick = {},
            editOnClick = {}
        )
    }
}