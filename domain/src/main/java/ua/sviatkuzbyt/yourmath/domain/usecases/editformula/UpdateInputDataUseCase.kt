package ua.sviatkuzbyt.yourmath.domain.usecases.editformula

import ua.sviatkuzbyt.yourmath.domain.repositories.EditFormulaRepository

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
}