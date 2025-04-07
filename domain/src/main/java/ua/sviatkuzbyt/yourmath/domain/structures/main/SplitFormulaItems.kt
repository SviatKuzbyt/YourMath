package ua.sviatkuzbyt.yourmath.domain.structures.main

data class SplitFormulaItems(
    val pins: List<FormulaItem>,
    val unpins: List<FormulaItem>
) {
    fun isEmpty() : Boolean{
        return pins.isEmpty() && unpins.isEmpty()
    }
}
