package ua.sviatkuzbyt.yourmath.data.repositories

import ua.sviatkuzbyt.yourmath.data.database.EditFormulaDao
import ua.sviatkuzbyt.yourmath.data.structures.editor.FormulaItemData
import ua.sviatkuzbyt.yourmath.domain.repositories.EditFormulaRepository
import ua.sviatkuzbyt.yourmath.domain.structures.main.FormulaItem
import javax.inject.Inject

class EditFormulaRepositoryImpl @Inject constructor(
    private val editFormulaDao: EditFormulaDao
) : EditFormulaRepository {

    override fun getFormulas(): List<FormulaItem> {
        return editFormulaDao.getFormulas().map {
            mapToFormulaItemDomain(it)
        }
    }

    private fun mapToFormulaItemDomain(item: FormulaItemData): FormulaItem{
        return FormulaItem(item.formulaID, item.name, item.position)
    }
}