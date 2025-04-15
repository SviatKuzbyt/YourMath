package ua.sviatkuzbyt.yourmath.app.presenter.screens.transfer.export

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import ua.sviatkuzbyt.yourmath.app.presenter.controllers.transfer.TransferIntent
import ua.sviatkuzbyt.yourmath.app.presenter.navigation.LocalNavController
import ua.sviatkuzbyt.yourmath.app.presenter.screens.transfer.TransferContent

@Composable
fun ExportScreen(viewModel: ExportViewModel = hiltViewModel()){
    val screenState by viewModel.screenState.collectAsState()
    val navController = LocalNavController.current

    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.CreateDocument("application/json")
    ) { uri ->
        uri?.let {
            viewModel.exportToFile(uri.toString())
        }
    }

    TransferContent(screenState) { intent ->
        when(intent){
            TransferIntent.Exit -> navController.navigateUp()
            TransferIntent.Continue -> launcher.launch("YourmathFormulas")
        }
    }
}