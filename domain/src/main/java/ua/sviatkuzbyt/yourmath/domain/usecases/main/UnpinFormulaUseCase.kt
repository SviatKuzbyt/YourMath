package ua.sviatkuzbyt.yourmath.domain.usecases.main

import ua.sviatkuzbyt.yourmath.domain.repositories.FormulasRepository
import ua.sviatkuzbyt.yourmath.domain.structures.FormulaItem

class UnpinFormulaUseCase(private val repository: FormulasRepository) {
    fun execute(formula: FormulaItem) {
        repository.changePinFormula(formula.id, false)
    }
}