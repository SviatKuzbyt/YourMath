package ua.sviatkuzbyt.yourmath.test.usecases.main

import org.junit.Assert.assertTrue
import org.junit.Test
import ua.sviatkuzbyt.yourmath.domain.structures.main.FormulaItem
import ua.sviatkuzbyt.yourmath.domain.usecases.main.UnpinFormulaUseCase
import ua.sviatkuzbyt.yourmath.test.repositories.FakeFormulasRepository

class UnpinFormulaUseCaseTest {

    @Test
    fun `test execute unpins formula`() {
        val fakeRepository = FakeFormulasRepository()
        val useCase = UnpinFormulaUseCase(fakeRepository)

        val formula = FormulaItem(1, "Formula 1", 0)
        useCase.execute(formula)

        assertTrue(fakeRepository.isChangedPin)
    }
}
