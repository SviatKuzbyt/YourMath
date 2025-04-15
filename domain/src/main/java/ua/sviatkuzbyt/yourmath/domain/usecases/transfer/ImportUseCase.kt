package ua.sviatkuzbyt.yourmath.domain.usecases.transfer

import ua.sviatkuzbyt.yourmath.domain.repositories.EditFormulaRepository
import ua.sviatkuzbyt.yourmath.domain.repositories.FileRepository
import ua.sviatkuzbyt.yourmath.domain.repositories.JsonRepository
import ua.sviatkuzbyt.yourmath.domain.structures.transfer.ExportDataInput
import ua.sviatkuzbyt.yourmath.domain.structures.transfer.ExportDataOutput
import ua.sviatkuzbyt.yourmath.domain.structures.transfer.ExportFormulaItem
import ua.sviatkuzbyt.yourmath.domain.structures.transfer.ImportedDataInput
import ua.sviatkuzbyt.yourmath.domain.structures.transfer.ImportedDataOutput
import ua.sviatkuzbyt.yourmath.domain.structures.transfer.ImportedFormula

class ImportUseCase(
    private val editFormulaRepository: EditFormulaRepository,
    private val jsonRepository: JsonRepository,
    private val fileRepository: FileRepository
) {
    fun execute(fileUri: String){
        val jsonFormulas = fileRepository.read(fileUri)
        val listFormulas = jsonRepository.jsonToFileFormulaItems(jsonFormulas)
        val tableSize = editFormulaRepository.getTableSize()

        listFormulas.forEach { formula ->
            val formulaID = addFormulaAndGetID(formula, tableSize)
            formula.inputData.forEach { data ->
                addInputData(data, formulaID)
            }
            formula.outputData.forEach { data ->
                addOutputData(data, formulaID)
            }
        }
    }

    private fun addFormulaAndGetID(formula: ExportFormulaItem, tableSize: Int): Long{
        return editFormulaRepository.addImportedFormulaAndGetID(
            ImportedFormula(
                name = formula.name,
                description = formula.description,
                code = formula.code,
                position = formula.position + tableSize
            )
        )
    }

    private fun addInputData(data: ExportDataInput, formulaID: Long){
        editFormulaRepository.addImportedInputData(
            ImportedDataInput(
                label = data.label,
                codeLabel = data.codeLabel,
                defaultData = data.defaultData,
                formulaID = formulaID,
                position = data.position
            )
        )
    }

    private fun addOutputData(data: ExportDataOutput, formulaID: Long){
        editFormulaRepository.addImportedOutputData(
            ImportedDataOutput(
                label = data.label,
                codeLabel = data.codeLabel,
                formulaID = formulaID,
                position = data.position
            )
        )
    }
}