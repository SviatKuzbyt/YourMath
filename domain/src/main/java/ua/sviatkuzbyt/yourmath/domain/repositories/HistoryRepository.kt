package ua.sviatkuzbyt.yourmath.domain.repositories

import ua.sviatkuzbyt.yourmath.domain.structures.formula.FormulaInput
import ua.sviatkuzbyt.yourmath.domain.structures.formula.FormulaResult
import ua.sviatkuzbyt.yourmath.domain.structures.history.HistoryNoFormatItem

interface HistoryRepository {
    fun addHistoryFormulaAndGetID(date: Long, formulaID: Long): Long
    fun addHistoryInputData(data: String, inputID: Long, historyID: Long)
    fun addHistoryOutputData(data: String, outputID: Long, historyID: Long)
    fun getHistoryItems(offset: Int, limit: Int): List<HistoryNoFormatItem>
    fun getFormulaInput(historyID: Long): List<FormulaInput>
    fun getFormulaResult(historyID: Long): List<FormulaResult>
    fun cleanHistory()
}