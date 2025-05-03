package ua.sviatkuzbyt.yourmath.domain.structures.edit.editformula

data class EditFormulaInfo(
    val id: Long,
    val name: String,
    val description: String?,
    val isNote: Boolean,
    val code: String,
)