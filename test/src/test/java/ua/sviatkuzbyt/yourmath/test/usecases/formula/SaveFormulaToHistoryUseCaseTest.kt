package ua.sviatkuzbyt.yourmath.test.usecases.formula

import org.junit.Assert.assertTrue
import org.junit.Test
import ua.sviatkuzbyt.yourmath.domain.structures.formula.FormulaContent
import ua.sviatkuzbyt.yourmath.domain.usecases.formula.SaveFormulaToHistoryUseCase
import ua.sviatkuzbyt.yourmath.test.repositories.FakeFormulasRepository
import ua.sviatkuzbyt.yourmath.test.repositories.FakeHistoryRepository

class SaveFormulaToHistoryUseCaseTest {

    @Test
    fun `test execute saves formula to history`() {
        val formulasRepository = FakeFormulasRepository()
        val historyRepository = FakeHistoryRepository()
        val useCase = SaveFormulaToHistoryUseCase(historyRepository)

        val formula = FormulaContent(
            info = formulasRepository.formulaInfo,
            inputData = formulasRepository.formulaInputs,
            resultData = formulasRepository.formulaResults
        )

        useCase.execute(formula, 1, 1)

        assertTrue(historyRepository.addedHistoryFormulaAndGetID)
        assertTrue(historyRepository.addedHistoryInputData)
        assertTrue(historyRepository.addedHistoryOutputData)
    }
}
