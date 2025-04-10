package ua.sviatkuzbyt.yourmath.data.repositories

import ua.sviatkuzbyt.yourmath.data.database.FormulaDao
import ua.sviatkuzbyt.yourmath.data.structures.formula.FormulaInfoData
import ua.sviatkuzbyt.yourmath.data.structures.formula.FormulaInputData
import ua.sviatkuzbyt.yourmath.data.structures.formula.FormulaResultData
import ua.sviatkuzbyt.yourmath.data.structures.history.FormulaFilterItemData
import ua.sviatkuzbyt.yourmath.data.structures.main.FormulaItemWithPinnedData
import ua.sviatkuzbyt.yourmath.domain.repositories.FormulasRepository
import ua.sviatkuzbyt.yourmath.domain.structures.formula.FormulaInfo
import ua.sviatkuzbyt.yourmath.domain.structures.formula.FormulaInput
import ua.sviatkuzbyt.yourmath.domain.structures.formula.FormulaResult
import ua.sviatkuzbyt.yourmath.domain.structures.history.FormulaFilterItem
import ua.sviatkuzbyt.yourmath.domain.structures.main.FormulaItemWithPinned
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class FormulasRepositoryImpl @Inject constructor(
    private val formulaDao: FormulaDao
): FormulasRepository {

    override fun getFormulaWithPinnedList(): List<FormulaItemWithPinned> {
        return formulaDao.getFormulas().map { formulaFromDB ->
            mapFormulaItemToDomain(formulaFromDB)
        }
    }

    override fun changePinFormula(id: Long, isPin: Boolean) {
        formulaDao.changePinFormula(id, isPin)
    }

    override fun searchFormulas(searchText: String): List<FormulaItemWithPinned> {
        return formulaDao.searchFormulas(searchText).map { formula ->
            mapFormulaItemToDomain(formula)
        }
    }

    override fun getFormulaInfo(formulaID: Long): FormulaInfo {
        return mapFormulaInfoToDomain(formulaDao.getFormulaInfo(formulaID))
    }

    override fun getFormulaInput(formulaID: Long): List<FormulaInput> {
        return formulaDao.getFormulaInputData(formulaID).map{ inputData ->
            mapInputDataFormulaToDomain(inputData)
        }
    }

    override fun getFormulaCode(formulaID: Long): String {
        return formulaDao.getFormulaCode(formulaID)
    }

    override fun getFormulaResult(formulaID: Long): List<FormulaResult> {
        return formulaDao.getOutputData(formulaID).map {
            mapResultDataFormulaToDomain(it)
        }
    }

    override fun getFormulaFilterList(): List<FormulaFilterItem> {
        return formulaDao.getFormulaFilterItemList().map {
            mapFormulaFilterItemToDomain(it)
        }
    }

    private fun mapFormulaItemToDomain(item: FormulaItemWithPinnedData): FormulaItemWithPinned {
        return FormulaItemWithPinned(item.formulaID, item.name, item.isPin, item.position)
    }

    private fun mapFormulaInfoToDomain(info: FormulaInfoData): FormulaInfo {
        return FormulaInfo(info.name, info.description)
    }

    private fun mapInputDataFormulaToDomain(data: FormulaInputData): FormulaInput {
        return FormulaInput(
            id = data.inputDataID,
            label = data.label,
            codeLabel = data.codeLabel,
            defaultData = data.defaultData,
            data = ""
        )
    }

    private fun mapResultDataFormulaToDomain(data: FormulaResultData): FormulaResult {
        return FormulaResult(
            id = data.outputDataID,
            label = data.label,
            codeLabel = data.codeLabel,
            data = null
        )
    }

    private fun mapFormulaFilterItemToDomain(data: FormulaFilterItemData): FormulaFilterItem {
        return FormulaFilterItem(data.formulaID, data.name)
    }
}