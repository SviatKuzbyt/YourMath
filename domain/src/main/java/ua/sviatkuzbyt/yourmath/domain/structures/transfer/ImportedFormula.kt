package ua.sviatkuzbyt.yourmath.domain.structures.transfer

data class ImportedFormula(
    val name: String,
    val description: String?,
    val code: String,
    val position: Int
)

