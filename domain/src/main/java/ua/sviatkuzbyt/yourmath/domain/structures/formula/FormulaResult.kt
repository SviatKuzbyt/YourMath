package ua.sviatkuzbyt.yourmath.domain.structures.formula

data class FormulaResult(
    val id: Long,
    val label: String,
    val codeLabel: String,
    val data: String? = null
)