package ua.sviatkuzbyt.yourmath.domain.usecases.main

import ua.sviatkuzbyt.yourmath.domain.repositories.FormulasRepository
import ua.sviatkuzbyt.yourmath.domain.structures.main.SplitFormulaItems

class GetFormulasListUseCase(
    private val repository: FormulasRepository,
    private val splitFormulas: SplitFormulaItemsUseCase
) {
    fun execute(): SplitFormulaItems {
        return splitFormulas.execute(repository.getFormulaWithPinnedList())
    }
}