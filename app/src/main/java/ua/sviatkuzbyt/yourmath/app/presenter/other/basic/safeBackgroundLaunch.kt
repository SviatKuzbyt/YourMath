package ua.sviatkuzbyt.yourmath.app.presenter.other.basic

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

fun ViewModel.safeBackgroundLaunch(
    code: suspend () -> Unit,
    errorHandling: (Exception) -> Unit
){
    viewModelScope.launch(Dispatchers.IO){
        try {
            code()
        } catch (e: Exception){
            errorHandling(e)
            Log.e("SKLT", "Caught error", e)
        }
    }
}