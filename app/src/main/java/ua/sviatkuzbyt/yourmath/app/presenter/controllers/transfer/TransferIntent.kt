package ua.sviatkuzbyt.yourmath.app.presenter.controllers.transfer

sealed class TransferIntent {
    data object Continue: TransferIntent()
    data class SetExportNotes(val isExport: Boolean): TransferIntent()
    data object Exit: TransferIntent()
}