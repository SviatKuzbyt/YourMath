package ua.sviatkuzbyt.yourmath.test.usecases.editor

import org.junit.Assert.assertTrue
import org.junit.Test
import ua.sviatkuzbyt.yourmath.domain.usecases.editor.MoveFormulaUseCase
import ua.sviatkuzbyt.yourmath.test.repositories.FakeEditFormulaRepository
import ua.sviatkuzbyt.yourmath.test.repositories.FakeFormulasRepository

class MoveFormulaUseCaseTest {

    @Test
    fun `test execute moves formula to new position`() {
        val fakeRepository = FakeEditFormulaRepository()
        val useCase = MoveFormulaUseCase(fakeRepository)

        useCase.execute(1, 1, 2, 2)
        assertTrue(fakeRepository.isSetFormulaPosition)
    }
}
