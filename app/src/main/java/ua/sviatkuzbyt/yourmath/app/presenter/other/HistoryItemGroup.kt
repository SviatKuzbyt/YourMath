package ua.sviatkuzbyt.yourmath.app.presenter.other

sealed class HistoryItem{
    data class Date(
        val id: Int,
        val dateStr: String
    ): HistoryItem()
    data class Formula(
        val name: String,
        val inputOutputData: String,
        val formulaID: Long,
        val historyID: Long
    ): HistoryItem()
}