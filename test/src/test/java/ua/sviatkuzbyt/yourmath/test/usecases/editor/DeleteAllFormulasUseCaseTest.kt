package ua.sviatkuzbyt.yourmath.test.usecases.editor

import org.junit.Assert.assertTrue
import org.junit.Test
import ua.sviatkuzbyt.yourmath.domain.usecases.editor.DeleteAllFormulasUseCase
import ua.sviatkuzbyt.yourmath.test.repositories.FakeEditFormulaRepository

class DeleteAllFormulasUseCaseTest {

    @Test
    fun `test execute deletes all formulas`() {
        val fakeRepository = FakeEditFormulaRepository()
        val useCase = DeleteAllFormulasUseCase(fakeRepository)

        useCase.execute()
        assertTrue(fakeRepository.isDeletedAllFormulas)
    }
}
