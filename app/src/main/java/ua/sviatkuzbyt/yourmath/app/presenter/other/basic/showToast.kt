package ua.sviatkuzbyt.yourmath.app.presenter.other.basic

import android.content.Context
import android.widget.Toast
import androidx.annotation.StringRes
import ua.sviatkuzbyt.yourmath.app.R

fun showToast(
    @StringRes text: Int,
    context: Context
){
    Toast.makeText(context, text, Toast.LENGTH_SHORT).show()
}