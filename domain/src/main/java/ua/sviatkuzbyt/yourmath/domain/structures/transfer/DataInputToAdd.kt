package ua.sviatkuzbyt.yourmath.domain.structures.transfer

data class DataInputToAdd(
    val label: String,
    val codeLabel: String,
    val defaultData: String?,
    val formulaID: Long,
    val position: Int
)
