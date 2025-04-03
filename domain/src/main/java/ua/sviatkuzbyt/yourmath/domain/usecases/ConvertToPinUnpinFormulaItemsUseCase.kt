package ua.sviatkuzbyt.yourmath.domain.usecases

import ua.sviatkuzbyt.yourmath.domain.structures.main.FormulaItem
import ua.sviatkuzbyt.yourmath.domain.structures.main.FormulaItemWithPinned
import ua.sviatkuzbyt.yourmath.domain.structures.main.PinUnpinFormulaItems

class ConvertToPinUnpinFormulaItemsUseCase {
    fun execute(formulas: List<FormulaItemWithPinned>): PinUnpinFormulaItems {
        val (pinList, noPinList) = formulas
            .partition { formula ->
                formula.isPinned
            }

        return PinUnpinFormulaItems(
            pins = pinList.map { mapToFormulaItem(it) },
            unpins = noPinList.map { mapToFormulaItem(it) }
        )
    }

    private fun mapToFormulaItem(formula: FormulaItemWithPinned): FormulaItem {
        return FormulaItem(formula.id, formula.name, formula.position)
    }
}