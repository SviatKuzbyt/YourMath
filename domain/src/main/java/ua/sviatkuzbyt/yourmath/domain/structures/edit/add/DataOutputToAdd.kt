package ua.sviatkuzbyt.yourmath.domain.structures.edit.add

data class DataOutputToAdd(
    val label: String,
    val codeLabel: String,
    val formulaID: Long,
    val position: Int
)
