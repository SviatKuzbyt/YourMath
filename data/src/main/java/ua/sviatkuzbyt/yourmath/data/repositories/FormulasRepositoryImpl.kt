package ua.sviatkuzbyt.yourmath.data.repositories

import ua.sviatkuzbyt.yourmath.data.database.FormulaDao
import ua.sviatkuzbyt.yourmath.data.structures.FormulaItemData
import ua.sviatkuzbyt.yourmath.domain.repositories.FormulasRepository
import ua.sviatkuzbyt.yourmath.domain.structures.FormulaItemWithPinned
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class FormulasRepositoryImpl @Inject constructor(
    private val formulaDao: FormulaDao
): FormulasRepository {
    override fun getFormulas(): List<FormulaItemWithPinned> {
        return formulaDao.getFormulas().map { formulaFromDB ->
            mapFormulaToDomain(formulaFromDB)
        }
    }

    override fun changePinFormula(id: Long, isPin: Boolean) {
        formulaDao.changePinFormula(id, isPin)
    }

    override fun searchFormulas(searchText: String): List<FormulaItemWithPinned> {
        return formulaDao.searchFormulas(searchText).map { formula ->
            mapFormulaToDomain(formula)
        }
    }


    private fun mapFormulaToDomain(item: FormulaItemData): FormulaItemWithPinned {
        return FormulaItemWithPinned(item.formulaID, item.name, item.isPin, item.position)
    }
}