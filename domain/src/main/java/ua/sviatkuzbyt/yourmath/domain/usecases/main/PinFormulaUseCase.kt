package ua.sviatkuzbyt.yourmath.domain.usecases.main

import ua.sviatkuzbyt.yourmath.domain.repositories.FormulasRepository
import ua.sviatkuzbyt.yourmath.domain.structures.main.FormulaItem

class PinFormulaUseCase(private val repository: FormulasRepository) {
    fun execute(formula: FormulaItem) {
        repository.changePinFormula(formula.id, true)
    }
}