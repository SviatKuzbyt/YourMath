package ua.sviatkuzbyt.yourmath.test.repositories

import ua.sviatkuzbyt.yourmath.domain.repositories.JsonRepository
import ua.sviatkuzbyt.yourmath.domain.structures.edit.export.ExportFormulaItem
import ua.sviatkuzbyt.yourmath.domain.structures.formula.FormulaInput

class FakeJsonRepository: JsonRepository {


    val inputsJson = "{\"input1\": \"10\""

    val inputMap = mapOf("output1" to "20")

    val formulasJson = "formulas"

    val formulasData = listOf(
        ExportFormulaItem(
            name = "Formula 1",
            description = "Description of Formula 1",
            inputData = listOf(),
            outputData = listOf(),
            code = "code",
            isNote = false,
            position = 1
        )
    )

    override fun formulaInputsToJson(inputList: List<FormulaInput>): String {
        return inputsJson
    }

    override fun jsonToResultMap(json: String): Map<String, String> {
        return inputMap
    }

    override fun fileFormulaItemsToJson(items: List<ExportFormulaItem>): String {
        return formulasJson
    }

    override fun jsonToFileFormulaItems(itemsJson: String): List<ExportFormulaItem> {
        return formulasData
    }
}