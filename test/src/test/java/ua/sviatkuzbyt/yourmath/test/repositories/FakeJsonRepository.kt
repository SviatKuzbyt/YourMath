package ua.sviatkuzbyt.yourmath.test.repositories

import ua.sviatkuzbyt.yourmath.domain.repositories.JsonRepository
import ua.sviatkuzbyt.yourmath.domain.structures.edit.export.ExportDataInput
import ua.sviatkuzbyt.yourmath.domain.structures.edit.export.ExportDataOutput
import ua.sviatkuzbyt.yourmath.domain.structures.edit.export.ExportFormulaItem
import ua.sviatkuzbyt.yourmath.domain.structures.formula.FormulaInput

class FakeJsonRepository: JsonRepository {
    private val inputsJson = "{\"input1\": \"10\""

    val inputMap = mapOf("output1" to "20")

    private val formulasJson = "formulas"

    private val formulasData = listOf(
        ExportFormulaItem(
            name = "Formula 1",
            description = "Description of Formula 1",
            inputData = listOf(
                ExportDataInput("Input 1", "input1", "10", 1)
            ),
            outputData = listOf(
                ExportDataOutput("Output 1", "output1", 1)
            ),
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