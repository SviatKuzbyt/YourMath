package ua.sviatkuzbyt.yourmath.data.structures.edit.editformula

data class EditFormulaInfoData(
    val formulaID: Long,
    val name: String,
    val description: String?,
    val isNote: Boolean,
    val code: String
)