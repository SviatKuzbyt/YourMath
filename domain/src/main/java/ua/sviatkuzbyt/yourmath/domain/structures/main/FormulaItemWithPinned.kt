package ua.sviatkuzbyt.yourmath.domain.structures.main

data class FormulaItemWithPinned(
    val id: Long,
    val name: String,
    val isPinned: Boolean,
    val position: Int
)