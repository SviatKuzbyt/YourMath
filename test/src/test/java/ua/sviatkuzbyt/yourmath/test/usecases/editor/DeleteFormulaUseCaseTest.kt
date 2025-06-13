package ua.sviatkuzbyt.yourmath.test.usecases.editor

import org.junit.Assert.assertTrue
import org.junit.Test
import ua.sviatkuzbyt.yourmath.domain.usecases.editor.DeleteFormulaUseCase
import ua.sviatkuzbyt.yourmath.test.repositories.FakeEditFormulaRepository

class DeleteFormulaUseCaseTest {

    @Test
    fun `test execute deletes specific formula`() {
        val fakeRepository = FakeEditFormulaRepository()
        val useCase = DeleteFormulaUseCase(fakeRepository)

        useCase.execute(1)
        assertTrue(fakeRepository.isDeletedFormula)
        assertTrue(fakeRepository.isUpdatedPosition)
    }
}
