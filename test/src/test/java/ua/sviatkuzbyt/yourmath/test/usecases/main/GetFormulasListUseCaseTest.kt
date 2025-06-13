package ua.sviatkuzbyt.yourmath.test.usecases.main

import org.junit.Assert.assertEquals
import org.junit.Test
import ua.sviatkuzbyt.yourmath.domain.structures.main.FormulaItem
import ua.sviatkuzbyt.yourmath.domain.structures.main.SplitFormulaItems
import ua.sviatkuzbyt.yourmath.domain.usecases.main.GetFormulasListUseCase
import ua.sviatkuzbyt.yourmath.domain.usecases.main.SplitFormulaItemsUseCase
import ua.sviatkuzbyt.yourmath.test.repositories.FakeFormulasRepository

class GetFormulasListUseCaseTest {

    @Test
    fun `test execute returns split formulas`() {
        val fakeRepository = FakeFormulasRepository()
        val splitFormulaItemsUseCase = SplitFormulaItemsUseCase()
        val useCase = GetFormulasListUseCase(fakeRepository, splitFormulaItemsUseCase)

        val result = useCase.execute()

        val expected = SplitFormulaItems(
            pins = fakeRepository.formulas
                .filter { it.isPinned }
                .map { FormulaItem(it.id, it.name, it.position) },
            unpins = fakeRepository.formulas
                .filter { !it.isPinned }
                .map { FormulaItem(it.id, it.name, it.position) }
        )

        assertEquals(expected, result)
    }
}
