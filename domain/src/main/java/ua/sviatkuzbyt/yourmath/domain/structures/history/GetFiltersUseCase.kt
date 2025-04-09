package ua.sviatkuzbyt.yourmath.domain.structures.history

import ua.sviatkuzbyt.yourmath.domain.repositories.FormulasRepository

class GetFiltersUseCase(private val formulasRepository: FormulasRepository) {
    fun execute(): List<FormulaFilterItem>{
        return formulasRepository.getFormulaFilterList()
    }
}