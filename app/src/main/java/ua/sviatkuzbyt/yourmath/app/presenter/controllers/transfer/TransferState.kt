package ua.sviatkuzbyt.yourmath.app.presenter.controllers.transfer

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import ua.sviatkuzbyt.yourmath.app.R

data class TransferState(
    @DrawableRes val icon: Int,
    @StringRes val tittle: Int,
    @StringRes val content: Int,
    val isExportNotes: Boolean?,
    val isContinueButton: Boolean,
    val isCanselButton: Boolean,
    val isExitButton: Boolean,
){
    companion object{
        fun getExport() = TransferState(
            icon = R.drawable.ic_export,
            tittle = R.string.export_formulas,
            content = R.string.export_content,
            isExportNotes = false,
            isContinueButton = true,
            isCanselButton = true,
            isExitButton = false
        )

        fun getImport() = TransferState(
            icon = R.drawable.ic_import,
            tittle = R.string.import_formulas,
            content = R.string.import_content,
            isExportNotes = null,
            isContinueButton = true,
            isCanselButton = true,
            isExitButton = false
        )

        fun getError() = TransferState(
            icon = R.drawable.ic_no_results,
            tittle = R.string.unknown_error,
            content = R.string.unknown_error_detail,
            isExportNotes = null,
            isContinueButton = false,
            isCanselButton = false,
            isExitButton = true
        )
    }
}