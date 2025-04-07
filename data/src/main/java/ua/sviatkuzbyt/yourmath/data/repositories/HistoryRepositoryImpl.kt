package ua.sviatkuzbyt.yourmath.data.repositories

import ua.sviatkuzbyt.yourmath.data.database.HistoryDao
import ua.sviatkuzbyt.yourmath.data.database.entity.HistoryFormulaEntity
import ua.sviatkuzbyt.yourmath.data.database.entity.HistoryInputDataEntity
import ua.sviatkuzbyt.yourmath.data.database.entity.HistoryOutputDataEntity
import ua.sviatkuzbyt.yourmath.domain.repositories.HistoryRepository
import ua.sviatkuzbyt.yourmath.domain.structures.history.HistoryDataItem
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
}