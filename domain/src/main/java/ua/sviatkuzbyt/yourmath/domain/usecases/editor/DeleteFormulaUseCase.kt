package ua.sviatkuzbyt.yourmath.domain.usecases.editor

import ua.sviatkuzbyt.yourmath.domain.repositories.EditFormulaRepository

class DeleteFormulaUseCase(private val repository: EditFormulaRepository) {
    fun execute(formulaID: Long){
        repository.deleteFormula(formulaID)
    }
}