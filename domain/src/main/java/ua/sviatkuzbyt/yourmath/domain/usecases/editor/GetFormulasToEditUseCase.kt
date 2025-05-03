package ua.sviatkuzbyt.yourmath.domain.usecases.editor

import ua.sviatkuzbyt.yourmath.domain.repositories.EditFormulaRepository
import ua.sviatkuzbyt.yourmath.domain.structures.edit.FormulaNameItem

class GetFormulasToEditUseCase(private val repository: EditFormulaRepository) {
    fun execute(): List<FormulaNameItem>{
        return repository.getFormulas()
    }
}