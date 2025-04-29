package ua.sviatkuzbyt.yourmath.domain.usecases.editformula

import ua.sviatkuzbyt.yourmath.domain.repositories.EditFormulaRepository
import ua.sviatkuzbyt.yourmath.domain.structures.editformula.EditFormula
import ua.sviatkuzbyt.yourmath.domain.structures.editformula.EditFormulaInfo
import ua.sviatkuzbyt.yourmath.domain.structures.transfer.FormulaToAdd

class CreateFormulaUseCase(private val repository: EditFormulaRepository) {
    fun execute(): EditFormula{
        val position = repository.getTableSize()
        val formula = FormulaToAdd(
            name = "",
            description = null,
            code = "",
            position = position
        )
        val id = repository.addFormula(formula)

        return EditFormula(
            info = EditFormulaInfo(
                id = id,
                name = formula.name,
                description = formula.description,
                code = formula.code
            ),
            inputList = listOf(),
            resultList = listOf()
        )
    }

    companion object{
        const val NEW_FORMULA = 0L
    }
}