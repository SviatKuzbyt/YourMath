package ua.sviatkuzbyt.yourmath.domain.usecases.formula

import ua.sviatkuzbyt.yourmath.domain.repositories.FormulasRepository
import ua.sviatkuzbyt.yourmath.domain.repositories.PythonRepository
import ua.sviatkuzbyt.yourmath.domain.structures.formula.InputDataFormula
import ua.sviatkuzbyt.yourmath.domain.structures.formula.ResultDataFormula

class MathFormulaUseCase(
    private val formulasRepository: FormulasRepository,
    private val pythonRepository: PythonRepository
) {
    fun execute(
        formulaID: Long,
        inputData: List<InputDataFormula>
    ): List<ResultDataFormula> {
        val inputJSON = pythonRepository.convertInputDataToJSON(inputData)
        val code = formulasRepository.getFormulaCode(formulaID)
        val codeResultJSON = pythonRepository.runCode(code, inputJSON)
        val emptyResultData = formulasRepository.getEmptyResultDataFormula(formulaID)
        return pythonRepository.putJSONToResultData(codeResultJSON, emptyResultData)
    }
}