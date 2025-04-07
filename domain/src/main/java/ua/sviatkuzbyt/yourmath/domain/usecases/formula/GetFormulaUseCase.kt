package ua.sviatkuzbyt.yourmath.domain.usecases.formula

import ua.sviatkuzbyt.yourmath.domain.repositories.FormulasRepository
import ua.sviatkuzbyt.yourmath.domain.structures.formula.FormulaContent

class GetFormulaUseCase(private val repository: FormulasRepository) {
    fun execute(formulaID: Long): FormulaContent{
        return FormulaContent(
            info = repository.getFormulaInfo(formulaID),
            inputData = repository.getFormulaInput(formulaID),
            resultData = listOf()
        )
    }
}