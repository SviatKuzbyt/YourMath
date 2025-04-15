package ua.sviatkuzbyt.yourmath.domain.usecases.editor

import ua.sviatkuzbyt.yourmath.domain.repositories.EditFormulaRepository
import ua.sviatkuzbyt.yourmath.domain.structures.editor.FormulaNameItem

class GetNewFormulasUseCase(private val repository: EditFormulaRepository) {
    fun execute(offset: Int): List<FormulaNameItem>{
        return repository.getMoreFormulas(offset)
    }
}