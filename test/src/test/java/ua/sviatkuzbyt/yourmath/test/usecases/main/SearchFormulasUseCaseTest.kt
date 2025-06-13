package ua.sviatkuzbyt.yourmath.test.usecases.main

import org.junit.Assert.assertEquals
import org.junit.Test
import ua.sviatkuzbyt.yourmath.domain.structures.main.FormulaItem
import ua.sviatkuzbyt.yourmath.domain.structures.main.FormulaItemWithPinned
import ua.sviatkuzbyt.yourmath.domain.structures.main.SplitFormulaItems
import ua.sviatkuzbyt.yourmath.domain.usecases.main.SearchFormulasUseCase
import ua.sviatkuzbyt.yourmath.domain.usecases.main.SplitFormulaItemsUseCase
import ua.sviatkuzbyt.yourmath.test.repositories.FakeFormulasRepository

class SearchFormulasUseCaseTest {

    @Test
    fun `test execute returns filtered and split formulas`() {
        val fakeRepository = FakeFormulasRepository()
        val splitFormulaItemsUseCase = SplitFormulaItemsUseCase()
        val useCase = SearchFormulasUseCase(fakeRepository, splitFormulaItemsUseCase)

        val result = useCase.execute("Math")

        val expected = SplitFormulaItems(
            pins = fakeRepository.searchFormulas
                .filter { it.isPinned }
                .map { FormulaItem(it.id, it.name, it.position) },
            unpins = fakeRepository.searchFormulas
                .filter { !it.isPinned }
                .map { FormulaItem(it.id, it.name, it.position) }
        )

        assertEquals(expected, result)
    }
}
