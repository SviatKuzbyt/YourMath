package ua.sviatkuzbyt.yourmath.domain.usecases.formula

import ua.sviatkuzbyt.yourmath.domain.repositories.FormulasRepository
import ua.sviatkuzbyt.yourmath.domain.repositories.JsonRepository
import ua.sviatkuzbyt.yourmath.domain.repositories.PythonRepository
import ua.sviatkuzbyt.yourmath.domain.structures.formula.FormulaInput
import ua.sviatkuzbyt.yourmath.domain.structures.formula.FormulaResult

class MathFormulaUseCase(
    private val formulasRepository: FormulasRepository,
    private val pythonRepository: PythonRepository,
    private val jsonRepository: JsonRepository
) {
    fun execute(
        formulaID: Long,
        formulaInputList: List<FormulaInput>
    ): List<FormulaResult> {
        //get data for calculation
        val inputJson = jsonRepository.formulaInputsToJson(formulaInputList)
        val codeText = formulasRepository.getFormulaCode(formulaID)

        //calculation
        val codeResultJson = pythonRepository.runCode(codeText, inputJson)

        //get result data
        val mapResult = jsonRepository.jsonToResultMap(codeResultJson)
        val formulaResultList = formulasRepository.getFormulaResult(formulaID)

        //map result data
        return formulaResultList.map { result ->
            result.copy(
                data = mapResult[result.codeLabel]
            )
        }
    }
}