package ua.sviatkuzbyt.yourmath.data.structures.transfer

data class FormulaToFormatData(
    val formulaID: Long,
    val name: String,
    val description: String?,
    val code: String,
    val isNote: Boolean,
    val position: Int
)
