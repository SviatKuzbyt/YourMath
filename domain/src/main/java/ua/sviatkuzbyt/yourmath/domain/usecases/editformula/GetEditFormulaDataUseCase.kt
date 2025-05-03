package ua.sviatkuzbyt.yourmath.domain.usecases.editformula

import ua.sviatkuzbyt.yourmath.domain.repositories.EditFormulaRepository
import ua.sviatkuzbyt.yourmath.domain.structures.edit.editformula.EditFormula

class GetEditFormulaDataUseCase(private val repository: EditFormulaRepository) {
    fun execute(formulaID: Long): EditFormula {
        val info = repository.getEditFormulaInfo(formulaID)
        val inputList = repository.getEditInputs(formulaID)
        val resultList = repository.getEditResults(formulaID)
        return EditFormula(info, inputList, resultList)
    }
}