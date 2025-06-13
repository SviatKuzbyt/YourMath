package ua.sviatkuzbyt.yourmath.test.usecases.history

import org.junit.Assert.assertEquals
import org.junit.Test
import ua.sviatkuzbyt.yourmath.domain.structures.history.FormulaFilterItem
import ua.sviatkuzbyt.yourmath.domain.usecases.history.GetFiltersUseCase
import ua.sviatkuzbyt.yourmath.test.repositories.FakeFormulasRepository

class GetFiltersUseCaseTest {

    @Test
    fun `test execute retrieves filters`() {
        val fakeRepository = FakeFormulasRepository()
        val useCase = GetFiltersUseCase(fakeRepository)

        val result = useCase.execute()

        val expected = listOf(FormulaFilterItem(0, "", true)) +
                fakeRepository.formulaFilters
        assertEquals(expected, result)
    }
}
