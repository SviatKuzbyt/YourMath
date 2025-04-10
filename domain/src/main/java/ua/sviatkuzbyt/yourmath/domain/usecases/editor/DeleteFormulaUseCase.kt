package ua.sviatkuzbyt.yourmath.domain.usecases.editor

import ua.sviatkuzbyt.yourmath.domain.repositories.EditFormulaRepository
import ua.sviatkuzbyt.yourmath.domain.structures.main.FormulaItem

class DeleteFormulaUseCase(private val repository: EditFormulaRepository) {
    fun execute(formulaID: Long){
        repository.deleteFormula(formulaID)
    }
}