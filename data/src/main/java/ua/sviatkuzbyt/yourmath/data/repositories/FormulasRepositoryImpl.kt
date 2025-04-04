package ua.sviatkuzbyt.yourmath.data.repositories

import ua.sviatkuzbyt.yourmath.data.database.FormulaDao
import ua.sviatkuzbyt.yourmath.data.structures.formula.FormulaInfoData
import ua.sviatkuzbyt.yourmath.data.structures.formula.InputDataFormulaData
import ua.sviatkuzbyt.yourmath.data.structures.formula.ResultDataFormulaData
import ua.sviatkuzbyt.yourmath.data.structures.main.FormulaItemData
import ua.sviatkuzbyt.yourmath.domain.repositories.FormulasRepository
import ua.sviatkuzbyt.yourmath.domain.structures.formula.FormulaInfo
import ua.sviatkuzbyt.yourmath.domain.structures.formula.InputDataFormula
import ua.sviatkuzbyt.yourmath.domain.structures.formula.ResultDataFormula
import ua.sviatkuzbyt.yourmath.domain.structures.main.FormulaItemWithPinned
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class FormulasRepositoryImpl @Inject constructor(
    private val formulaDao: FormulaDao
): FormulasRepository {
    override fun getFormulas(): List<FormulaItemWithPinned> {
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

    override fun getInputDataFormula(formulaID: Long): List<InputDataFormula> {
        return formulaDao.getFormulaInputData(formulaID).map{ inputData ->
            mapInputDataFormulaToDomain(inputData)
        }
    }

    override fun getFormulaCode(formulaID: Long): String {
        return formulaDao.getFormulaCode(formulaID)
    }

    override fun getEmptyResultDataFormula(formulaID: Long): List<ResultDataFormula> {
        return formulaDao.getOutputData(formulaID).map {
            mapResultDataFormulaToDomain(it)
        }
    }

    private fun mapFormulaItemToDomain(item: FormulaItemData): FormulaItemWithPinned {
        return FormulaItemWithPinned(item.formulaID, item.name, item.isPin, item.position)
    }

    private fun mapFormulaInfoToDomain(info: FormulaInfoData): FormulaInfo {
        return FormulaInfo(info.name, info.description)
    }

    private fun mapInputDataFormulaToDomain(data: InputDataFormulaData): InputDataFormula {
        return InputDataFormula(
            id = data.inputDataID,
            label = data.label,
            codeLabel = data.codeLabel,
            defaultData = data.defaultData,
            data = ""
        )
    }

    private fun mapResultDataFormulaToDomain(data: ResultDataFormulaData): ResultDataFormula {
        return ResultDataFormula(
            id = data.outputDataID,
            label = data.label,
            codeLabel = data.codeLabel,
            data = ""
        )
    }
}