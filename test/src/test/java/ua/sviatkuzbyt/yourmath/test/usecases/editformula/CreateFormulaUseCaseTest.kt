package ua.sviatkuzbyt.yourmath.test.usecases.editformula

import org.junit.Assert.assertTrue
import org.junit.Test
import ua.sviatkuzbyt.yourmath.domain.usecases.editformula.CreateFormulaUseCase
import ua.sviatkuzbyt.yourmath.test.repositories.FakeEditFormulaRepository

class CreateFormulaUseCaseTest {

    @Test
    fun `test execute creates formula`() {
        val fakeRepository = FakeEditFormulaRepository()
        val useCase = CreateFormulaUseCase(fakeRepository)

        useCase.execute()
        assertTrue(fakeRepository.isFormulaAdded)
    }
}
