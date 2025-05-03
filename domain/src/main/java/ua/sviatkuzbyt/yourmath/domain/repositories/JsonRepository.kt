package ua.sviatkuzbyt.yourmath.domain.repositories

import ua.sviatkuzbyt.yourmath.domain.structures.formula.FormulaInput
import ua.sviatkuzbyt.yourmath.domain.structures.edit.export.ExportFormulaItem

interface JsonRepository {
    fun formulaInputsToJson(inputList: List<FormulaInput>): String
    fun jsonToResultMap(json: String): Map<String, String>
    fun fileFormulaItemsToJson(items: List<ExportFormulaItem>): String
    fun jsonToFileFormulaItems(itemsJson: String): List<ExportFormulaItem>
}