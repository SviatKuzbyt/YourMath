package ua.sviatkuzbyt.yourmath.domain.repositories

import ua.sviatkuzbyt.yourmath.domain.structures.formula.InputDataFormula
import ua.sviatkuzbyt.yourmath.domain.structures.formula.ResultDataFormula

interface PythonRepository {
    fun convertInputDataToJSON(input: List<InputDataFormula>): String
    fun runCode(code: String, inputData: String): String
    fun putJSONToResultData(
        resultJSON: String,
        resultList: List<ResultDataFormula>
    ): List<ResultDataFormula>
}