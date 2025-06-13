package ua.sviatkuzbyt.yourmath.test.usecases.editformula

import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import ua.sviatkuzbyt.yourmath.domain.usecases.editformula.UpdateResultDataUseCase
import ua.sviatkuzbyt.yourmath.test.repositories.FakeEditFormulaRepository

class UpdateResultDataUseCaseTest {

    private lateinit var repository: FakeEditFormulaRepository
    private lateinit var useCase: UpdateResultDataUseCase

    @Before
    fun setup() {
        repository = FakeEditFormulaRepository()
        useCase = UpdateResultDataUseCase(repository)
    }

    @Test
    fun `update label of input item`() {
        useCase.updateTextLabel("New label", 1)
        assertTrue(repository.isUpdatedResultTextLabel)
    }

    @Test
    fun `update code label of input item`() {
        useCase.updateCodeLabel("New Code", 1)
        assertTrue(repository.isUpdatedResultCodeLabel)
    }

    @Test
    fun `delete result item`() {
        useCase.deleteItem(1, 1, 0)
        assertTrue(repository.isDeletedResultData)
        assertTrue(repository.isUpdatedResultDataPositionsAfterDeleting)
    }

    @Test
    fun `move result item`() {
        useCase.moveItem(1, 0, 2, 1)
        assertTrue(repository.isSetResultDataPosition)
    }

    @Test
    fun `add new result item`() {
        useCase.add(1)
        assertTrue(repository.isOutputDataAdded)
    }
}
