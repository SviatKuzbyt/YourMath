package ua.sviatkuzbyt.yourmath.data.structures.history

data class FormulaInputWithValueData(
    val inputDataID: Long,
    val label: String,
    val codeLabel: String,
    val defaultData: String?,
    val value: String
)
