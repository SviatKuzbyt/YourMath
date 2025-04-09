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
                data = input.data.ifBlank { input.defaultData ?: "0" },
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