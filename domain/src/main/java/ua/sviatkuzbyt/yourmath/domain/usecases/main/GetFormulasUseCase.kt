package ua.sviatkuzbyt.yourmath.domain.usecases.main

import ua.sviatkuzbyt.yourmath.domain.usecases.ConvertToPinUnpinFormulaItemsUseCase
import ua.sviatkuzbyt.yourmath.domain.repositories.FormulasRepository
import ua.sviatkuzbyt.yourmath.domain.structures.main.PinUnpinFormulaItems

class GetFormulasUseCase(
    private val repository: FormulasRepository,
    private val convert: ConvertToPinUnpinFormulaItemsUseCase
) {
    fun execute(): PinUnpinFormulaItems {
        return convert.execute(repository.getFormulas())
    }
}