package ua.sviatkuzbyt.yourmath.domain.usecases.transfer

import ua.sviatkuzbyt.yourmath.domain.repositories.EditFormulaRepository
import ua.sviatkuzbyt.yourmath.domain.repositories.JsonRepository
import ua.sviatkuzbyt.yourmath.domain.structures.transfer.FileFormulaItem

class ExportUseCase(
    private val editFormulaRepository: EditFormulaRepository,
    private val jsonRepository: JsonRepository
) {
    fun execute(): String{
        val formulas = editFormulaRepository.getFormulasToExport()

        val itemsToExport = formulas.map { formula ->
            FileFormulaItem(
                name = formula.name,
                description = formula.description,
                inputData = editFormulaRepository.getInputDataToExport(formula.formulaID),
                outputData = editFormulaRepository.getOutputDataToExport(formula.formulaID),
                code = formula.code,
                position = formula.position
            )
        }
        return jsonRepository.fileFormulaItemsToJson(itemsToExport)
    }
}