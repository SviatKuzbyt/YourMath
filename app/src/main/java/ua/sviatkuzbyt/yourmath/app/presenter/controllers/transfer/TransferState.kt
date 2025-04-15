package ua.sviatkuzbyt.yourmath.app.presenter.controllers.transfer

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import ua.sviatkuzbyt.yourmath.app.R

data class TransferState(
    @DrawableRes val icon: Int,
    @StringRes val tittle: Int,
    @StringRes val content: Int,
    val isContinueButton: Boolean,
    val isCanselButton: Boolean,
    val isExitButton: Boolean,
){
    companion object{
        fun getExport() = TransferState(
            icon = R.drawable.ic_export,
            tittle = R.string.export_formulas,
            content = R.string.export_content,
            isContinueButton = true,
            isCanselButton = true,
            isExitButton = false
        )

        fun getError() = TransferState(
            icon = R.drawable.ic_no_results,
            tittle = R.string.unknown_error,
            content = R.string.unknown_error_detail,
            isContinueButton = false,
            isCanselButton = true,
            isExitButton = false
        )
    }
}