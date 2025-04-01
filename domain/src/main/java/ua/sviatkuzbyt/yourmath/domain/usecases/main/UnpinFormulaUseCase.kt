package ua.sviatkuzbyt.yourmath.domain.usecases.main

import ua.sviatkuzbyt.yourmath.domain.repositories.FormulasRepository
import ua.sviatkuzbyt.yourmath.domain.structures.FormulaItem
import ua.sviatkuzbyt.yourmath.domain.structures.PinUnpinFormulaItems

class UnpinFormulaUseCase(private val repository: FormulasRepository) {
    fun execute(
        formula: FormulaItem,
        pinUnpinFormulaItems: PinUnpinFormulaItems
    ): PinUnpinFormulaItems {

        repository.changePinFormula(formula.id, false)

        return pinUnpinFormulaItems.copy(
            pins = pinUnpinFormulaItems.pins - formula,
            unpins = (pinUnpinFormulaItems.unpins + formula).sortedBy { it.position }
        )
    }
}