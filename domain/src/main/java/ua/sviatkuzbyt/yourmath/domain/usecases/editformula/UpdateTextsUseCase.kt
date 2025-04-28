package ua.sviatkuzbyt.yourmath.domain.usecases.editformula

import ua.sviatkuzbyt.yourmath.domain.repositories.EditFormulaRepository

class UpdateTextsUseCase(private val repository: EditFormulaRepository) {
    fun updateFormulaLabel(text: String, formulaID: Long){
        repository.updateFormulaLabel(text, formulaID)
    }
    fun updateFormulaDescription(text: String, formulaID: Long){
        repository.updateFormulaDescription(text, formulaID)
    }
    fun updateInputTextLabel(text: String, inputID: Long){
        repository.updateInputTextLabel(text, inputID)
    }
    fun updateInputCodeLabel(text: String, inputID: Long){
        repository.updateInputCodeLabel(text, inputID)
    }
    fun updateInputDefaultData(text: String, inputID: Long){
        repository.updateInputDefaultData(text, inputID)
    }
    fun updateResultTextLabel(text: String, outputID: Long){
        repository.updateResultTextLabel(text, outputID)
    }
    fun updateResultCodeLabel(text: String, outputID: Long){
        repository.updateResultCodeLabel(text, outputID)
    }
    fun updateCodeFormula(text: String, formulaID: Long){
        repository.updateCodeFormula(text, formulaID)
    }
}