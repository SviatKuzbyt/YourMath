package ua.sviatkuzbyt.yourmath.domain.usecases.editformula

import ua.sviatkuzbyt.yourmath.domain.repositories.EditFormulaRepository
import ua.sviatkuzbyt.yourmath.domain.structures.editformula.EditResult
import ua.sviatkuzbyt.yourmath.domain.structures.transfer.DataOutputToAdd

class UpdateResultDataUseCase(private val repository: EditFormulaRepository) {
    fun updateTextLabel(text: String, outputID: Long){
        repository.updateResultTextLabel(text, outputID)
    }

    fun updateCodeLabel(text: String, outputID: Long){
        repository.updateResultCodeLabel(text, outputID)
    }

    fun deleteItem(formulaID: Long, resultID: Long, position: Int){
        repository.deleteResultData(resultID)
        repository.updateResultDataPositionsAfterDeleting(position, formulaID)
    }

    fun moveItem(fromID: Long, fromIndex: Int, toID: Long, toIndex: Int){
        repository.setResultDataPosition(fromID, toIndex)
        repository.setResultDataPosition(toID, fromIndex)
    }

    fun add(formulaID: Long): EditResult {
        val position = repository.getResultTableSize(formulaID)
        val item = DataOutputToAdd(
            label = "",
            codeLabel = "",
            formulaID = formulaID,
            position = position
        )
        val id = repository.addOutputData(item)
        return EditResult(id, item.label, item.codeLabel)
    }
}