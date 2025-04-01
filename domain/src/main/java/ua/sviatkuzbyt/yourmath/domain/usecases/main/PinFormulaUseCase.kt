package ua.sviatkuzbyt.yourmath.domain.usecases.main

import ua.sviatkuzbyt.yourmath.domain.repositories.FormulasRepository
import ua.sviatkuzbyt.yourmath.domain.structures.FormulaItem
import ua.sviatkuzbyt.yourmath.domain.structures.PinUnpinFormulaItems

class PinFormulaUseCase(private val repository: FormulasRepository) {
    fun execute(
        formula: FormulaItem,
        pinUnpinFormulaItems: PinUnpinFormulaItems
    ): PinUnpinFormulaItems {
        repository.changePinFormula(formula.id, true)

        return pinUnpinFormulaItems.copy(
            pins = (pinUnpinFormulaItems.pins + formula).sortedBy { it.position },
            unpins = pinUnpinFormulaItems.unpins - formula
        )
    }
}