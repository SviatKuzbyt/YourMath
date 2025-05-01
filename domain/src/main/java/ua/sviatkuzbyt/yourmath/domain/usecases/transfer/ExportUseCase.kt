package ua.sviatkuzbyt.yourmath.domain.usecases.transfer

import ua.sviatkuzbyt.yourmath.domain.repositories.EditFormulaRepository
import ua.sviatkuzbyt.yourmath.domain.repositories.FileRepository
import ua.sviatkuzbyt.yourmath.domain.repositories.JsonRepository
import ua.sviatkuzbyt.yourmath.domain.structures.transfer.ExportFormulaItem

class ExportUseCase(
    private val editFormulaRepository: EditFormulaRepository,
    private val jsonRepository: JsonRepository,
    private val fileRepository: FileRepository
) {
    fun execute(fileUri: String, isExportNotes: Boolean){
        val formulas = if (isExportNotes){
            editFormulaRepository.getFormulasWithNotesToExport()
        } else {
            editFormulaRepository.getFormulasToExport()
        }

        val itemsToExport = formulas.mapIndexed { index, formula ->
            ExportFormulaItem(
                name = formula.name,
                description = formula.description,
                inputData = editFormulaRepository.getInputDataToExport(formula.formulaID),
                outputData = editFormulaRepository.getOutputDataToExport(formula.formulaID),
                code = formula.code,
                isNote = formula.isNote,
                position = index
            )
        }

        val textImportItems = jsonRepository.fileFormulaItemsToJson(itemsToExport)
        fileRepository.write(fileUri, textImportItems)
    }
}