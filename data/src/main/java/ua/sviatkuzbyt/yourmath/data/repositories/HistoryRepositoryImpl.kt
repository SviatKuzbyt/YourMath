package ua.sviatkuzbyt.yourmath.data.repositories

import ua.sviatkuzbyt.yourmath.data.database.HistoryDao
import ua.sviatkuzbyt.yourmath.data.database.entity.HistoryFormulaEntity
import ua.sviatkuzbyt.yourmath.data.database.entity.HistoryInputDataEntity
import ua.sviatkuzbyt.yourmath.data.database.entity.HistoryOutputDataEntity
import ua.sviatkuzbyt.yourmath.data.structures.history.FormulaInputWithValueData
import ua.sviatkuzbyt.yourmath.data.structures.history.FormulaResultWithValueData
import ua.sviatkuzbyt.yourmath.data.structures.history.HistoryListItemData
import ua.sviatkuzbyt.yourmath.domain.repositories.HistoryRepository
import ua.sviatkuzbyt.yourmath.domain.structures.formula.FormulaInput
import ua.sviatkuzbyt.yourmath.domain.structures.formula.FormulaResult
import ua.sviatkuzbyt.yourmath.domain.structures.history.HistoryDataItem
import ua.sviatkuzbyt.yourmath.domain.structures.history.HistoryListItem
import javax.inject.Inject

class HistoryRepositoryImpl @Inject constructor(
    private val historyDao: HistoryDao
) : HistoryRepository {
    override fun addHistoryFormulaAndGetID(date: Long, formulaID: Long): Long {
        val entity = HistoryFormulaEntity(0, date, formulaID)
        return historyDao.insertHistoryFormula(entity)
    }

    override fun addHistoryInputData(data: HistoryDataItem, historyID: Long) {
        val entity = HistoryInputDataEntity(0, data.data, data.placeID, historyID)
        historyDao.insertHistoryInputData(entity)
    }

    override fun addHistoryOutputData(data: HistoryDataItem, historyID: Long) {
        val entity = HistoryOutputDataEntity(0, data.data, data.placeID, historyID)
        historyDao.insertHistoryOutputData(entity)
    }

    override fun getHistoryItems(offset: Int, limit: Int): List<HistoryListItem> {
        return historyDao.getHistoryItems(offset, limit).map { item ->
            mapToHistoryListItem(item)
        }
    }

    override fun getFormulaInput(historyID: Long): List<FormulaInput> {
        return historyDao.getInputData(historyID).map{ data ->
            mapInputDataFormulaToDomain(data)
        }
    }

    override fun getFormulaResult(historyID: Long): List<FormulaResult> {
        return historyDao.getOutputData(historyID).map{ data ->
            mapResultDataFormulaToDomain(data)
        }
    }

    private fun mapToHistoryListItem(items: HistoryListItemData): HistoryListItem{
        return HistoryListItem(
            historyId = items.historyId,
            formulaId = items.formulaId,
            name = items.name,
            valueInput = items.valueInput,
            valueOutput = items.valueOutput,
            date = items.date
        )
    }

    private fun mapInputDataFormulaToDomain(data: FormulaInputWithValueData): FormulaInput {
        return FormulaInput(
            id = data.inputDataID,
            label = data.label,
            codeLabel = data.codeLabel,
            defaultData = data.defaultData,
            data = data.value
        )
    }

    private fun mapResultDataFormulaToDomain(data: FormulaResultWithValueData): FormulaResult {
        return FormulaResult(
            id = data.outputDataID,
            label = data.label,
            codeLabel = data.codeLabel,
            data = data.value
        )
    }
}