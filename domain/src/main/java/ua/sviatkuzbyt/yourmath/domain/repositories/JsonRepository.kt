package ua.sviatkuzbyt.yourmath.domain.repositories

import ua.sviatkuzbyt.yourmath.domain.structures.formula.FormulaInput
import ua.sviatkuzbyt.yourmath.domain.structures.transfer.FileFormulaItem

interface JsonRepository {
    fun formulaInputsToJson(inputList: List<FormulaInput>): String
    fun jsonToResultMap(json: String): Map<String, String>
    fun fileFormulaItemsToJson(items: List<FileFormulaItem>): String
}