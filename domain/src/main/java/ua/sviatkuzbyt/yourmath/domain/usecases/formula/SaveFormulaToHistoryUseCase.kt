package ua.sviatkuzbyt.yourmath.domain.usecases.formula

import ua.sviatkuzbyt.yourmath.domain.repositories.HistoryRepository
import ua.sviatkuzbyt.yourmath.domain.structures.formula.FormulaContent
import ua.sviatkuzbyt.yourmath.domain.structures.history.HistoryDataItem

class SaveFormulaToHistoryUseCase(
    private val historyRepository: HistoryRepository
) {
    fun execute(
        formulaContent: FormulaContent,
        formulaID: Long
    ){
        val date = System.currentTimeMillis()
        val historyID = historyRepository.addHistoryFormulaAndGetID(date, formulaID)

        formulaContent.inputData.forEach { input ->
            val data = HistoryDataItem(input.data, input.id)
            historyRepository.addHistoryInputData(data, historyID)
        }

        formulaContent.resultData.forEach { result ->
            val data = HistoryDataItem(result.data.orEmpty(), result.id)
            historyRepository.addHistoryOutputData(data, historyID)
        }
    }
}