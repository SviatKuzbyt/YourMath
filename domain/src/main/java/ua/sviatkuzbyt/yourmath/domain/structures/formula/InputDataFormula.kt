package ua.sviatkuzbyt.yourmath.domain.structures.formula

data class InputDataFormula(
    val id: Long,
    val label: String,
    val defaultData: String?,
    val data: String
)
