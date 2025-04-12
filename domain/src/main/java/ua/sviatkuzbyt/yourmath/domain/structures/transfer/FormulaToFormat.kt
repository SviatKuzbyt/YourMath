package ua.sviatkuzbyt.yourmath.domain.structures.transfer

data class FormulaToFormat(
    val formulaID: Long,
    val name: String,
    val description: String?,
    val code: String,
    val position: Int
)

