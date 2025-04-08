package ua.sviatkuzbyt.yourmath.domain.repositories

import ua.sviatkuzbyt.yourmath.domain.structures.formula.FormulaInput
import ua.sviatkuzbyt.yourmath.domain.structures.formula.FormulaResult
import ua.sviatkuzbyt.yourmath.domain.structures.history.HistoryDataItem
import ua.sviatkuzbyt.yourmath.domain.structures.history.HistoryListItem

interface HistoryRepository {
    fun addHistoryFormulaAndGetID(date: Long, formulaID: Long): Long
    fun addHistoryInputData(data: HistoryDataItem, historyID: Long)
    fun addHistoryOutputData(data: HistoryDataItem, historyID: Long)
    fun getHistoryItems(offset: Int, limit: Int): List<HistoryListItem>
    fun getFormulaInput(historyID: Long): List<FormulaInput>
    fun getFormulaResult(historyID: Long): List<FormulaResult>
}