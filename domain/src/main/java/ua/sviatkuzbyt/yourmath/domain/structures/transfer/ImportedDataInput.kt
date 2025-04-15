package ua.sviatkuzbyt.yourmath.domain.structures.transfer

data class ImportedDataInput(
    val label: String,
    val codeLabel: String,
    val defaultData: String?,
    val formulaID: Long,
    val position: Int
)
