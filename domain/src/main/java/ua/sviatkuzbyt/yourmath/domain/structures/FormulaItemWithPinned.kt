package ua.sviatkuzbyt.yourmath.domain.structures

data class FormulaItemWithPinned(
    val id: Long,
    val name: String,
    val isPinned: Boolean,
    val position: Int
)