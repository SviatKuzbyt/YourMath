package ua.sviatkuzbyt.yourmath.domain.usecases.editformula

import ua.sviatkuzbyt.yourmath.domain.repositories.EditFormulaRepository

class UpdateResultDataUseCase(private val repository: EditFormulaRepository) {
    fun updateTextLabel(text: String, outputID: Long){
        repository.updateResultTextLabel(text, outputID)
    }

    fun updateCodeLabel(text: String, outputID: Long){
        repository.updateResultCodeLabel(text, outputID)
    }
}