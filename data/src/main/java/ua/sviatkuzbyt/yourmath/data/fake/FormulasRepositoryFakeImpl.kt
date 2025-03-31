package ua.sviatkuzbyt.yourmath.data.fake

import ua.sviatkuzbyt.yourmath.data.structures.FormulaListItemData
import ua.sviatkuzbyt.yourmath.domain.repositories.FormulasRepository
import ua.sviatkuzbyt.yourmath.domain.structures.FormulaListItem
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class FormulasRepositoryFakeImpl @Inject constructor(): FormulasRepository {
    private val formulas = mutableListOf(
        FormulaListItemData(1, "One formula", true),
        FormulaListItemData(2, "Two formula", false),
        FormulaListItemData(3, "Three formula", true),
        FormulaListItemData(4, "Four formula", false),
        FormulaListItemData(5, "Five formula", false),
        FormulaListItemData(6, "Six formula", false),
    )

    override fun getFormulas(): List<FormulaListItem> {
        return formulas.map { item ->
            mapFormulaListToDomain(item)
        }
    }

    private fun mapFormulaListToDomain(item: FormulaListItemData): FormulaListItem {
        return FormulaListItem(item.id, item.name, item.isPinned)
    }


}