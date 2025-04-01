package ua.sviatkuzbyt.yourmath.domain.usecases.main

import ua.sviatkuzbyt.yourmath.domain.repositories.FormulasRepository

class UnpinFormulaUseCase(private val repository: FormulasRepository) {
    fun execute(id: Long) {
        repository.changePinFormula(id, false)
    }
}