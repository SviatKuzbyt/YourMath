package ua.sviatkuzbyt.yourmath.domain.usecases.main

import ua.sviatkuzbyt.yourmath.domain.repositories.FormulasRepository
import ua.sviatkuzbyt.yourmath.domain.structures.FormulaItem
import ua.sviatkuzbyt.yourmath.domain.structures.FormulaItemWithPinned
import ua.sviatkuzbyt.yourmath.domain.structures.PinUnpinFormulaItems

class GetFormulasUseCase(private val repository: FormulasRepository) {
    fun execute(): PinUnpinFormulaItems {
        val (pinList, noPinList) = repository.getFormulas()
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