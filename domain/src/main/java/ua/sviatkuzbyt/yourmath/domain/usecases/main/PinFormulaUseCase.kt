package ua.sviatkuzbyt.yourmath.domain.usecases.main

import ua.sviatkuzbyt.yourmath.domain.repositories.FormulasRepository
import ua.sviatkuzbyt.yourmath.domain.structures.FormulaItem
import ua.sviatkuzbyt.yourmath.domain.structures.FormulaItemWithPinned
import ua.sviatkuzbyt.yourmath.domain.structures.PinUnpinFormulaItems

class PinFormulaUseCase(private val repository: FormulasRepository) {
    fun execute(id: Long) {
        repository.pinFormula(id)
    }
}