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

    // Rest of codes

    var cleanHistoryCalled = false
        private set

    override fun cleanHistory() {
        cleanHistoryCalled = true
    }

    override fun addHistoryFormulaAndGetID(date: Long, formulaID: Long): Long {
        TODO("Not yet implemented")
    }

    override fun addHistoryInputData(data: String, inputID: Long, historyID: Long) {
        TODO("Not yet implemented")
    }

    override fun addHistoryOutputData(data: String, outputID: Long, historyID: Long) {
        TODO("Not yet implemented")
    }



    override fun getFormulaInput(historyID: Long, formulaID: Long): List<FormulaInput> {
        TODO("Not yet implemented")
    }

    override fun getFormulaResult(historyID: Long, formulaID: Long): List<FormulaResult> {
        TODO("Not yet implemented")
    }



}