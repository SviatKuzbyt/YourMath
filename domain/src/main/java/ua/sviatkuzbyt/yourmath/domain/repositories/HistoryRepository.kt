package ua.sviatkuzbyt.yourmath.domain.repositories

import ua.sviatkuzbyt.yourmath.domain.structures.history.HistoryDataItem

interface HistoryRepository {
    fun addHistoryFormulaAndGetID(date: Long, formulaID: Long): Long
    fun addHistoryInputData(data: HistoryDataItem, formulaID: Long)
    fun addHistoryOutputData(data: HistoryDataItem, formulaID: Long)
}