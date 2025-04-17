package ua.sviatkuzbyt.yourmath.domain.usecases.editformula

import ua.sviatkuzbyt.yourmath.domain.repositories.EditFormulaRepository
import ua.sviatkuzbyt.yourmath.domain.structures.editformula.EditFormula
import ua.sviatkuzbyt.yourmath.domain.structures.editformula.EditFormulaInfo

class GetEditFormulaDataUseCase(
    private val repository: EditFormulaRepository
) {
    fun execute(formulaID: Long): EditFormula {
        return if (formulaID == NEW_FORMULA){
            getEmptyData()
        } else {
            val info = repository.getEditFormulaInfo(formulaID)
            val inputList = repository.getEditInputs(formulaID)
            val resultList = repository.getEditResults(formulaID)
            EditFormula(info, inputList, resultList)
        }
    }

    private fun getEmptyData() = EditFormula(
        info = EditFormulaInfo(
            id = 0L,
            name = "",
            description = null,
            code = ""
        ),
        inputList = listOf(),
        resultList = listOf()
    )

    companion object{
        const val NEW_FORMULA = 0L
    }
}