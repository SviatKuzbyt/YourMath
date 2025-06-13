package ua.sviatkuzbyt.yourmath.test.repositories

import ua.sviatkuzbyt.yourmath.domain.repositories.HistoryRepository
import ua.sviatkuzbyt.yourmath.domain.structures.formula.FormulaInput
import ua.sviatkuzbyt.yourmath.domain.structures.formula.FormulaResult
import ua.sviatkuzbyt.yourmath.domain.structures.history.HistoryNoFormatItem

class FakeHistoryRepository: HistoryRepository {
    val fakeItems = listOf(
        HistoryNoFormatItem(1, 1, "1", "1", "1", 1),
        HistoryNoFormatItem(2, 2, "2", "2", "2", 2),
        HistoryNoFormatItem(3, 3, "3", "3", "3", 3)
    )

    val fakeFilterItems = listOf(
        HistoryNoFormatItem(1, 1, "1", "1", "1", 1)
    )

    val fakeInputs = listOf(
        FormulaInput(1, "input1", "value1", "1", "data 1 history"),
        FormulaInput(2, "input2", "value2", "2", "data 2 history")
    )

    val fakeResults = listOf(
        FormulaResult(1, "result1", "value1", "data 1 history"),
        FormulaResult(2, "result2", "value2", "data 2 history")
    )

    var cleanHistoryCalled = false
        private set

    var addedHistoryFormulaAndGetID = false
        private set

    var addedHistoryInputData = false
        private set

    var addedHistoryOutputData = false
        private set

    override fun getHistoryItems(offset: Int, limit: Int): List<HistoryNoFormatItem> {
        return fakeItems
    }

    override fun getHistoryByFormulaIDItems(
        formulaID: Long,
        offset: Int,
        limit: Int
    ): List<HistoryNoFormatItem> {
        return fakeFilterItems
    }

    override fun cleanHistory() {
        cleanHistoryCalled = true
    }

    override fun addHistoryFormulaAndGetID(date: Long, formulaID: Long): Long {
        addedHistoryFormulaAndGetID = true
        return 1
    }

    override fun addHistoryInputData(data: String, inputID: Long, historyID: Long) {
        addedHistoryInputData = true
    }

    override fun addHistoryOutputData(data: String, outputID: Long, historyID: Long) {
        addedHistoryOutputData = true
    }

    override fun getFormulaInput(historyID: Long, formulaID: Long): List<FormulaInput> {
        return fakeInputs
    }

    override fun getFormulaResult(historyID: Long, formulaID: Long): List<FormulaResult> {
        return fakeResults
    }
}