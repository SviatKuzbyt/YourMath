package ua.sviatkuzbyt.yourmath.app.presenter.controllers.transfer

sealed class TransferIntent {
    data object CreateFileToExport: TransferIntent()
}