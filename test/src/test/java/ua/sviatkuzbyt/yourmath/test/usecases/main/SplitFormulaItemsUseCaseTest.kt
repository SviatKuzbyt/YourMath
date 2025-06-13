package ua.sviatkuzbyt.yourmath.test.usecases.main

import org.junit.Assert.assertEquals
import org.junit.Test
import ua.sviatkuzbyt.yourmath.domain.structures.main.FormulaItem
import ua.sviatkuzbyt.yourmath.domain.structures.main.FormulaItemWithPinned
import ua.sviatkuzbyt.yourmath.domain.structures.main.SplitFormulaItems
import ua.sviatkuzbyt.yourmath.domain.usecases.main.SplitFormulaItemsUseCase

class SplitFormulaItemsUseCaseTest {

    @Test
    fun `test execute splits formulas into pinned and unpinned`() {
        val formulas = listOf(
            FormulaItemWithPinned(1, "Formula 1", true, 0),
            FormulaItemWithPinned(2, "Formula 2", false, 1)
        )
        val useCase = SplitFormulaItemsUseCase()

        val result = useCase.execute(formulas)

        val expected = SplitFormulaItems(
            pins = listOf(
                FormulaItem(1, "Formula 1", 0)
            ),
            unpins = listOf(
                FormulaItem(2, "Formula 2", 1)
            )
        )

        assertEquals(expected, result)
    }
}
