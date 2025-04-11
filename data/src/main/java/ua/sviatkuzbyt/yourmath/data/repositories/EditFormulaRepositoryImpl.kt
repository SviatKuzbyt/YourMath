package ua.sviatkuzbyt.yourmath.data.repositories

import ua.sviatkuzbyt.yourmath.data.database.EditFormulaDao
import ua.sviatkuzbyt.yourmath.data.structures.editor.FormulaNameItemData
import ua.sviatkuzbyt.yourmath.domain.repositories.EditFormulaRepository
import ua.sviatkuzbyt.yourmath.domain.structures.editor.FormulaNameItem
import ua.sviatkuzbyt.yourmath.domain.structures.main.FormulaItem
import javax.inject.Inject

class EditFormulaRepositoryImpl @Inject constructor(
    private val editFormulaDao: EditFormulaDao
) : EditFormulaRepository {

    override fun getFormulas(): List<FormulaNameItem> {
        return editFormulaDao.getFormulas().map {
            mapToFormulaNameItemDomain(it)
        }
    }

    override fun deleteFormula(formulaID: Long) {
        editFormulaDao.deleteFormula(formulaID)
    }

    override fun deleteAllFormulas() {
        editFormulaDao.deleteAll()
    }

    override fun setFormulaPosition(formulaID: Long, position: Int) {
        editFormulaDao.updateFormulaPosition(formulaID, position)
    }

    private fun mapToFormulaNameItemDomain(item: FormulaNameItemData): FormulaNameItem{
        return FormulaNameItem(item.formulaID, item.name)
    }
}