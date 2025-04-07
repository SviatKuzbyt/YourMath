package ua.sviatkuzbyt.yourmath.domain.usecases.main

import ua.sviatkuzbyt.yourmath.domain.structures.main.FormulaItem
import ua.sviatkuzbyt.yourmath.domain.structures.main.FormulaItemWithPinned
import ua.sviatkuzbyt.yourmath.domain.structures.main.SplitFormulaItems

class SplitFormulaItemsUseCase {
    fun execute(formulas: List<FormulaItemWithPinned>): SplitFormulaItems {
        val (pinList, noPinList) = formulas
            .partition { formula ->
                formula.isPinned
            }

        return SplitFormulaItems(
            pins = pinList.map { mapToFormulaItem(it) },
            unpins = noPinList.map { mapToFormulaItem(it) }
        )
    }

    private fun mapToFormulaItem(formula: FormulaItemWithPinned): FormulaItem {
        return FormulaItem(formula.id, formula.name, formula.position)
    }
}