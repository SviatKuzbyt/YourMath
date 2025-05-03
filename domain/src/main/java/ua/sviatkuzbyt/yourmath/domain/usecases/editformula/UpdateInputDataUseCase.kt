package ua.sviatkuzbyt.yourmath.domain.usecases.editformula

import ua.sviatkuzbyt.yourmath.domain.repositories.EditFormulaRepository
import ua.sviatkuzbyt.yourmath.domain.structures.edit.editformula.EditInput
import ua.sviatkuzbyt.yourmath.domain.structures.edit.add.DataInputToAdd

class UpdateInputDataUseCase(private val repository: EditFormulaRepository) {
    fun updateTextLabel(text: String, inputID: Long){
        repository.updateInputTextLabel(text, inputID)
    }

    fun updateCodeLabel(text: String, inputID: Long){
        repository.updateInputCodeLabel(text, inputID)
    }

    fun updateDefaultData(text: String, inputID: Long){
        repository.updateInputDefaultData(text, inputID)
    }

    fun deleteItem(formulaID: Long, inputID: Long, position: Int){
        repository.deleteInputData(inputID)
        repository.updateInputDataPositionsAfterDeleting(position, formulaID)
    }

    fun moveItem(fromID: Long, fromIndex: Int, toID: Long, toIndex: Int){
        repository.setInputDataPosition(fromID, toIndex)
        repository.setInputDataPosition(toID, fromIndex)
    }

    fun add(formulaID: Long): EditInput {
        val position = repository.getInputTableSize(formulaID)
        val item = DataInputToAdd(
            label = "",
            codeLabel = "",
            defaultData = null,
            formulaID = formulaID,
            position = position
        )
        val id = repository.addInputData(item)
        return EditInput(id, item.label, item.codeLabel, item.defaultData)
    }
}