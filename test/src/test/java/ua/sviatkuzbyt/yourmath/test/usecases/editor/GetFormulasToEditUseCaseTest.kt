package ua.sviatkuzbyt.yourmath.test.usecases.editor

import org.junit.Assert.assertEquals
import org.junit.Test
import ua.sviatkuzbyt.yourmath.domain.structures.main.FormulaItem
import ua.sviatkuzbyt.yourmath.domain.usecases.editor.GetFormulasToEditUseCase
import ua.sviatkuzbyt.yourmath.test.repositories.FakeEditFormulaRepository
import ua.sviatkuzbyt.yourmath.test.repositories.FakeFormulasRepository

class GetFormulasToEditUseCaseTest {

    @Test
    fun `test execute retrieves formulas to edit`() {
        val fakeRepository = FakeEditFormulaRepository()
        val useCase = GetFormulasToEditUseCase(fakeRepository)

        val result = useCase.execute()
        assertEquals(fakeRepository.fakeFormulas, result)
    }
}
