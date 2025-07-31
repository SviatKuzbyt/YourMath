package ua.sviatkuzbyt.yourmath.domain.usecases.editor

import kotlinx.coroutines.flow.Flow
import ua.sviatkuzbyt.yourmath.domain.repositories.EditFormulaRepository
import ua.sviatkuzbyt.yourmath.domain.structures.edit.FormulaNameItem

class ObserveFormulasToEditUseCase(private val repository: EditFormulaRepository){
    fun execute(): Flow<List<FormulaNameItem>>{
        return repository.observeFormulas()
    }
}