package ua.sviatkuzbyt.yourmath.domain.structures.history

data class FormulaFilterItem(
    val formulaID: Long,
    val name: String,
    val isSelected: Boolean = false
)