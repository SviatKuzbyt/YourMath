package ua.sviatkuzbyt.yourmath.domain.structures.edit.add

data class FormulaToAdd(
    val name: String,
    val description: String?,
    val code: String,
    val position: Int,
    val isNote: Boolean
)

