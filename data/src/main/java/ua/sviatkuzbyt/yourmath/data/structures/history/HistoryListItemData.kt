package ua.sviatkuzbyt.yourmath.data.structures.history

data class HistoryListItemData(
    val historyId: Long,
    val formulaId: Long,
    val name: String,
    val valueInput: String,
    val valueOutput: String,
    val date: Long
)
