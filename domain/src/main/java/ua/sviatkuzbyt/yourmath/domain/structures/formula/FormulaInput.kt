package ua.sviatkuzbyt.yourmath.domain.structures.formula

data class FormulaInput(
    val id: Long,
    val label: String,
    val codeLabel: String,
    val defaultData: String?,
    val data: String
)
