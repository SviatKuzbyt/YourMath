package ua.sviatkuzbyt.yourmath.domain.structures.transfer

data class FormulaToAdd(
    val name: String,
    val description: String?,
    val code: String,
    val position: Int,
    val isNote: Boolean
)

