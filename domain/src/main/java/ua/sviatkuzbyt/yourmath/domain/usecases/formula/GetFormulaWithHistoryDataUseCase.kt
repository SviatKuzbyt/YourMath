package ua.sviatkuzbyt.yourmath.domain.usecases.formula

import ua.sviatkuzbyt.yourmath.domain.repositories.FormulasRepository
import ua.sviatkuzbyt.yourmath.domain.repositories.HistoryRepository
import ua.sviatkuzbyt.yourmath.domain.structures.formula.FormulaContent

class GetFormulaWithHistoryDataUseCase(
    private val formulaRepository: FormulasRepository,
    private val historyRepository: HistoryRepository,
) {
    fun execute(
        formulaID: Long,
        historyID: Long
    ): FormulaContent {
        return FormulaContent(
            info = formulaRepository.getFormulaInfo(formulaID),
            inputData = historyRepository.getFormulaInput(historyID, formulaID),
            resultData = historyRepository.getFormulaResult(historyID, formulaID)
        )
    }
}