package ua.sviatkuzbyt.yourmath.domain.structures.edit.export

data class FormulaToFormat(
    val formulaID: Long,
    val name: String,
    val description: String?,
    val code: String,
    val isNote: Boolean,
)

