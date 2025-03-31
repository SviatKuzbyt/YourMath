package ua.sviatkuzbyt.yourmath.app.ui.screens.home

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
fun MainScreen(viewModel: MainViewModel = hiltViewModel()){
    val screenState by viewModel.screenState.collectAsState()
    MainContent(screenState)
}
