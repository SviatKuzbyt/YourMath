package ua.sviatkuzbyt.yourmath.domain.structures.editformula

data class EditInput(
    val id: Long,
    val label: String,
    val codeLabel: String,
    val defaultData: String?
)
