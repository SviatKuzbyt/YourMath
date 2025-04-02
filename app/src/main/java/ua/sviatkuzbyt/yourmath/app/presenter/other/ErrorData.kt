package ua.sviatkuzbyt.yourmath.app.presenter.other

import androidx.annotation.StringRes
import ua.sviatkuzbyt.yourmath.app.R

data class ErrorData(
    @StringRes val tittleRes: Int = R.string.unknown_error,
    @StringRes val detailRes: Int = R.string.unknown_error_detail,
    val detailStr: String? = null
)
