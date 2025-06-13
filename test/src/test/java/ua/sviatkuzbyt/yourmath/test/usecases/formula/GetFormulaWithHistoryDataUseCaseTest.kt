package ua.sviatkuzbyt.yourmath.test.usecases.formula

import org.junit.Assert.assertEquals
import org.junit.Test
import ua.sviatkuzbyt.yourmath.domain.structures.formula.FormulaContent
import ua.sviatkuzbyt.yourmath.domain.usecases.formula.GetFormulaWithHistoryDataUseCase
import ua.sviatkuzbyt.yourmath.test.repositories.FakeFormulasRepository
import ua.sviatkuzbyt.yourmath.test.repositories.FakeHistoryRepository

class GetFormulaWithHistoryDataUseCaseTest {

    @Test
    fun `test execute retrieves formula with history`() {
        val formulaRepository = FakeFormulasRepository()
        val historyRepository = FakeHistoryRepository()
        val useCase = GetFormulaWithHistoryDataUseCase(formulaRepository, historyRepository)

        val result = useCase.execute(1, 1)

        val expected = FormulaContent(
            info = formulaRepository.formulaInfo,
            inputData = historyRepository.fakeInputs,
            resultData = historyRepository.fakeResults
        )
        assertEquals(expected, result)
    }
}
