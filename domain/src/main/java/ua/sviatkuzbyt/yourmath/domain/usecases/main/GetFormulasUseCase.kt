package ua.sviatkuzbyt.yourmath.domain.usecases.main

import ua.sviatkuzbyt.yourmath.domain.repositories.FormulasRepository
import ua.sviatkuzbyt.yourmath.domain.structures.FormulaListItem

class GetFormulasUseCase(private val repository: FormulasRepository) {
    fun execute(): List<FormulaListItem>{
        return repository.getFormulas()
    }
}