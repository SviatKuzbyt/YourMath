package ua.sviatkuzbyt.yourmath.data.structures.main

data class FormulaItemWithPinnedData(
    val formulaID: Long,
    val name: String,
    val isPin: Boolean,
    val position: Int
)