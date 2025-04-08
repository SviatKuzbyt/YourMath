package ua.sviatkuzbyt.yourmath.domain.structures.history

data class HistoryListItem(
    val historyId: Long,
    val formulaId: Long,
    val name: String,
    val valueInput: String,
    val valueOutput: String,
    val date: Long
)
