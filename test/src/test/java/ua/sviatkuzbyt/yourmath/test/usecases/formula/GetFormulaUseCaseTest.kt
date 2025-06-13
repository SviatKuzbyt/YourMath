package ua.sviatkuzbyt.yourmath.test.usecases.formula

import org.junit.Assert.assertEquals
import org.junit.Test
import ua.sviatkuzbyt.yourmath.domain.structures.formula.FormulaContent
import ua.sviatkuzbyt.yourmath.domain.usecases.formula.GetFormulaUseCase
import ua.sviatkuzbyt.yourmath.test.repositories.FakeFormulasRepository

class GetFormulaUseCaseTest {

    @Test
    fun `test execute retrieves specific formula`() {
        val fakeRepository = FakeFormulasRepository()
        val useCase = GetFormulaUseCase(fakeRepository)

        val result = useCase.execute(1)

        val expected = FormulaContent(
            info = fakeRepository.formulaInfo,
            inputData = fakeRepository.formulaInputs,
            resultData = listOf()
        )
        assertEquals(expected, result)
    }
}
