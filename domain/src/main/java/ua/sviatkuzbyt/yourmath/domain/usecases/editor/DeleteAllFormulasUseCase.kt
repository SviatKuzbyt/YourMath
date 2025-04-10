package ua.sviatkuzbyt.yourmath.domain.usecases.editor

import ua.sviatkuzbyt.yourmath.domain.repositories.EditFormulaRepository

class DeleteAllFormulasUseCase(private val repository: EditFormulaRepository) {
    fun execute(){
        repository.deleteAllFormulas()
    }
}