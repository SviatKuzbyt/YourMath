package ua.sviatkuzbyt.yourmath.domain.structures.editformula

data class EditFormulaInfo(
    val id: Long,
    val name: String,
    val description: String?,
    val code: String
)