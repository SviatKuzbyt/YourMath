package ua.sviatkuzbyt.yourmath.domain.repositories

import ua.sviatkuzbyt.yourmath.domain.structures.formula.FormulaInput

interface JsonRepository {
    fun formulaInputsToJson(inputList: List<FormulaInput>): String
    fun jsonToResultMap(json: String): Map<String, String>
}