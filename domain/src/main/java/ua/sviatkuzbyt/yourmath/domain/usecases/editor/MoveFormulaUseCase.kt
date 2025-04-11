package ua.sviatkuzbyt.yourmath.domain.usecases.editor

import ua.sviatkuzbyt.yourmath.domain.repositories.EditFormulaRepository

class MoveFormulaUseCase(private val repository: EditFormulaRepository) {
    fun execute(fromID: Long, fromIndex: Int, toID: Long, toIndex: Int){
        repository.setFormulaPosition(fromID, toIndex)
        repository.setFormulaPosition(toID, fromIndex)
    }
}