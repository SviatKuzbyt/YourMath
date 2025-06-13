package ua.sviatkuzbyt.yourmath.test.usecases.editor

import org.junit.Assert.assertEquals
import org.junit.Test
import ua.sviatkuzbyt.yourmath.domain.usecases.editor.GetNewFormulasUseCase
import ua.sviatkuzbyt.yourmath.test.repositories.FakeEditFormulaRepository

class GetNewFormulasUseCaseTest {

    @Test
    fun `test execute retrieves new formulas`() {
        val fakeRepository = FakeEditFormulaRepository()
        val useCase = GetNewFormulasUseCase(fakeRepository)

        val result = useCase.execute(1)
        assertEquals(fakeRepository.fakeMoreFormulas, result)
    }
}
