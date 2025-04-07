package ua.sviatkuzbyt.yourmath.domain.usecases.formula

import ua.sviatkuzbyt.yourmath.domain.repositories.FormulasRepository
import ua.sviatkuzbyt.yourmath.domain.structures.formula.Formula

class GetFormulaUseCase(private val repository: FormulasRepository) {
    fun execute(formulaID: Long): Formula{
        return Formula(
            info = repository.getFormulaInfo(formulaID),
            inputData = repository.getFormulaInput(formulaID),
            resultData = listOf()
        )
    }
}