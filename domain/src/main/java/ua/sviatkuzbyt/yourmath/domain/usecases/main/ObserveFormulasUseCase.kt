package ua.sviatkuzbyt.yourmath.domain.usecases.main

import kotlinx.coroutines.flow.Flow
import ua.sviatkuzbyt.yourmath.domain.repositories.FormulasRepository
import ua.sviatkuzbyt.yourmath.domain.structures.main.FormulaItemWithPinned

class ObserveFormulasUseCase(
    private val repository: FormulasRepository
){
    fun execute(): Flow<List<FormulaItemWithPinned>>{
        return repository.observeFormulas()
    }
}