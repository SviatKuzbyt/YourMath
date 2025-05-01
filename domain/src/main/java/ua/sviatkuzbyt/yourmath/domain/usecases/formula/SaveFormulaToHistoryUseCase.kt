package ua.sviatkuzbyt.yourmath.domain.usecases.formula

import ua.sviatkuzbyt.yourmath.domain.repositories.HistoryRepository
import ua.sviatkuzbyt.yourmath.domain.structures.formula.FormulaContent

class SaveFormulaToHistoryUseCase(
    private val historyRepository: HistoryRepository
) {
    fun execute(
        formulaContent: FormulaContent,
        formulaID: Long,
        date: Long
    ){
        val historyID = historyRepository.addHistoryFormulaAndGetID(date, formulaID)

        formulaContent.inputData.forEach { input ->
            historyRepository.addHistoryInputData(
                data =
                    if (input.data.isNullOrBlank()) input.defaultData ?: "0"
                    else input.data,
                inputID = input.id,
                historyID = historyID
            )
        }

        formulaContent.resultData.forEach { result ->
            historyRepository.addHistoryOutputData(
                data = result.data?: "0",
                outputID = result.id,
                historyID = historyID
            )
        }
    }
}