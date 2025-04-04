package ua.sviatkuzbyt.yourmath.domain.structures.formula

data class ResultDataFormula(
    val id: Long,
    val label: String,
    val codeLabel: String,
    val data: String? = null
)