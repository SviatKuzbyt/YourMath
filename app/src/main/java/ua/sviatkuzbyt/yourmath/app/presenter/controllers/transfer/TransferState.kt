package ua.sviatkuzbyt.yourmath.app.presenter.controllers.transfer

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import ua.sviatkuzbyt.yourmath.app.R

data class TransferState(
    @DrawableRes val icon: Int,
    @StringRes val tittle: Int,
    @StringRes val content: Int,
    val buttonPrimary: TransferButton?,
    val isCanselButton: Boolean
){
    companion object{
        fun getExport() = TransferState(
            icon = R.drawable.ic_export,
            tittle = R.string.export_formulas,
            content = R.string.export_content,
            buttonPrimary = TransferButton.ContinueExport,
            isCanselButton = true
        )

        fun getImport() = TransferState(
            icon = R.drawable.ic_import,
            tittle = R.string.import_formulas,
            content = R.string.import_content,
            buttonPrimary = TransferButton.ContinueImport,
            isCanselButton = true
        )

        fun getImportFromUri() = TransferState(
            icon = R.drawable.ic_import,
            tittle = R.string.import_formulas,
            content = R.string.import_uri_content,
            buttonPrimary = TransferButton.ContinueImportFromUri,
            isCanselButton = true
        )

        fun getError() = TransferState(
            icon = R.drawable.ic_no_results,
            tittle = R.string.unknown_error,
            content = R.string.unknown_error_detail,
            buttonPrimary = null,
            isCanselButton = true
        )
    }
}

sealed class TransferButton(@StringRes val text: Int){
    data object ContinueExport: TransferButton(R.string.start)
    data object ContinueImport: TransferButton(R.string.start)
    data object ContinueImportFromUri: TransferButton(R.string.start)
    data object Close: TransferButton(R.string.close)
    data object ToMain: TransferButton(R.string.to_main)
}

