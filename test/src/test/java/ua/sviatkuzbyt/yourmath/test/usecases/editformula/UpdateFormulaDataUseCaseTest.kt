package ua.sviatkuzbyt.yourmath.test.usecases.editformula

import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import ua.sviatkuzbyt.yourmath.domain.usecases.editformula.UpdateFormulaDataUseCase
import ua.sviatkuzbyt.yourmath.test.repositories.FakeEditFormulaRepository

class UpdateFormulaDataUseCaseTest {

    private lateinit var repository: FakeEditFormulaRepository
    private lateinit var useCase: UpdateFormulaDataUseCase

    @Before
    fun setup() {
        repository = FakeEditFormulaRepository()
        useCase = UpdateFormulaDataUseCase(repository)
    }

    @Test
    fun `test update label`() {
        useCase.updateLabel("New Label", 1L)
        assertTrue(repository.isUpdatedFormulaLabel)
    }

    @Test
    fun `test update description`() {
        useCase.updateDescription("New Description", 1L)
        assertTrue(repository.isUpdatedFormulaDescription)
    }

    @Test
    fun `test update code`() {
        useCase.updateCode("New Code", 1L)
        assertTrue(repository.isUpdatedCodeFormula)
    }

    @Test
    fun `test change is note`() {
        useCase.changeIsNote(true, 1L)
        assertTrue(repository.updatedIsNote)
    }
}
