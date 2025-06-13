package ua.sviatkuzbyt.yourmath.test.usecases.main

import org.junit.Assert.assertTrue
import org.junit.Test
import ua.sviatkuzbyt.yourmath.domain.structures.main.FormulaItem
import ua.sviatkuzbyt.yourmath.domain.usecases.main.PinFormulaUseCase
import ua.sviatkuzbyt.yourmath.test.repositories.FakeFormulasRepository

class PinFormulaUseCaseTest {

    @Test
    fun `test execute pins formula`() {
        val fakeRepository = FakeFormulasRepository()
        val useCase = PinFormulaUseCase(fakeRepository)

        val formula = FormulaItem(1, "Formula 1", 0)
        useCase.execute(formula)

        assertTrue(fakeRepository.isChangedPin)
    }
}
