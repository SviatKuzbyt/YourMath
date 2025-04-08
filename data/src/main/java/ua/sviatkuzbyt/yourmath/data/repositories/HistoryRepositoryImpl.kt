package ua.sviatkuzbyt.yourmath.data.repositories

import ua.sviatkuzbyt.yourmath.data.database.HistoryDao
import ua.sviatkuzbyt.yourmath.data.database.entity.HistoryFormulaEntity
import ua.sviatkuzbyt.yourmath.data.database.entity.HistoryInputDataEntity
import ua.sviatkuzbyt.yourmath.data.database.entity.HistoryOutputDataEntity
import ua.sviatkuzbyt.yourmath.data.structures.history.HistoryListItemData
import ua.sviatkuzbyt.yourmath.domain.repositories.HistoryRepository
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

    override fun addHistoryInputData(data: HistoryDataItem, formulaID: Long) {
        val entity = HistoryInputDataEntity(0, data.data, data.placeID, formulaID)
        historyDao.insertHistoryInputData(entity)
    }

    override fun addHistoryOutputData(data: HistoryDataItem, formulaID: Long) {
        val entity = HistoryOutputDataEntity(0, data.data, data.placeID, formulaID)
        historyDao.insertHistoryOutputData(entity)
    }

    override fun getHistoryItems(offset: Int, limit: Int): List<HistoryListItem> {
        return historyDao.getHistoryItems(offset, limit).map { item ->
            mapToHistoryListItem(item)
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
}