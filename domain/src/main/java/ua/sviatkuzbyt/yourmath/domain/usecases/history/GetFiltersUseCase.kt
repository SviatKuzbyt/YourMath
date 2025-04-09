package ua.sviatkuzbyt.yourmath.domain.usecases.history

import ua.sviatkuzbyt.yourmath.domain.repositories.FormulasRepository
import ua.sviatkuzbyt.yourmath.domain.structures.history.FormulaFilterItem

class GetFiltersUseCase(private val formulasRepository: FormulasRepository) {
    fun execute(): List<FormulaFilterItem>{
        return listOf(FormulaFilterItem(0, "", true)) +
                formulasRepository.getFormulaFilterList()
    }
}