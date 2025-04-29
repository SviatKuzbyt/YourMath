package ua.sviatkuzbyt.yourmath.domain.usecases.editformula

import ua.sviatkuzbyt.yourmath.domain.repositories.EditFormulaRepository

class UpdateFormulaDataUseCase(private val repository: EditFormulaRepository) {
    fun updateLabel(text: String, formulaID: Long){
        repository.updateFormulaLabel(text, formulaID)
    }

    fun updateDescription(text: String, formulaID: Long){
        repository.updateFormulaDescription(text, formulaID)
    }

    fun updateCode(text: String, formulaID: Long){
        repository.updateCodeFormula(text, formulaID)
    }
}