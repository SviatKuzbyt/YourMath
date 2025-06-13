package ua.sviatkuzbyt.yourmath.test.usecases.editformula

import org.junit.Assert.assertEquals
import org.junit.Test
import ua.sviatkuzbyt.yourmath.domain.structures.edit.editformula.EditFormula
import ua.sviatkuzbyt.yourmath.domain.usecases.editformula.GetEditFormulaDataUseCase
import ua.sviatkuzbyt.yourmath.test.repositories.FakeEditFormulaRepository

class GetEditFormulaDataUseCaseTest {

    @Test
    fun `test execute retrieves formula data`() {
        val fakeRepository = FakeEditFormulaRepository()
        val useCase = GetEditFormulaDataUseCase(fakeRepository)

        val result = useCase.execute(fakeRepository.newId)
        val expected = EditFormula(
            info = fakeRepository.editFormulaInfo,
            inputList = fakeRepository.editInputs,
            resultList = fakeRepository.editResults
        )

        assertEquals(expected, result)
    }
}
