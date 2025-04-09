package ua.sviatkuzbyt.yourmath.app.presenter.other.history

sealed class HistoryItem {
    data class Date(
        val dateStr: String,
        val dateLong: Long
    ): HistoryItem()

    data class Formula(
        val name: String,
        val inputOutputData: String,
        val formulaID: Long,
        val historyID: Long
    ): HistoryItem()
}