package ua.sviatkuzbyt.yourmath.data.fake

import ua.sviatkuzbyt.yourmath.data.structures.FormulaItemData
import ua.sviatkuzbyt.yourmath.domain.repositories.FormulasRepository
import ua.sviatkuzbyt.yourmath.domain.structures.FormulaItemWithPinned
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class FormulasRepositoryFakeImpl @Inject constructor(): FormulasRepository {
    private val formulas = mutableListOf(
        FormulaItemData(1, "One formula", true, 1),
        FormulaItemData(2, "Two formula", false, 2),
        FormulaItemData(3, "Three formula", true, 3),
        FormulaItemData(4, "Four formula", false, 4),
        FormulaItemData(5, "Five formula", false, 5),
        FormulaItemData(6, "Six formula", false, 6)
    )

    override fun getFormulas(): List<FormulaItemWithPinned> {
        return formulas.map { item ->
            mapFormulaToDomain(item)
        }
    }

    private fun mapFormulaToDomain(item: FormulaItemData): FormulaItemWithPinned {
        return FormulaItemWithPinned(item.id, item.name, item.isPinned, item.position)
    }
}