package ua.sviatkuzbyt.yourmath.test.usecases.editformula

import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import ua.sviatkuzbyt.yourmath.domain.usecases.editformula.UpdateInputDataUseCase
import ua.sviatkuzbyt.yourmath.test.repositories.FakeEditFormulaRepository

class UpdateInputDataUseCaseTest {

    private lateinit var repository: FakeEditFormulaRepository
    private lateinit var useCase: UpdateInputDataUseCase

    @Before
    fun setup() {
        repository = FakeEditFormulaRepository()
        useCase = UpdateInputDataUseCase(repository)
    }

    @Test
    fun `update label of input item`() {
        useCase.updateTextLabel("New label", 1)
        assertTrue(repository.isUpdatedInputTextLabel)
    }

    @Test
    fun `update code label of input item`() {
        useCase.updateCodeLabel("New Code", 1)
        assertTrue(repository.isUpdatedInputCodeLabel)
    }

    @Test
    fun `update default data of input item`() {
        useCase.updateDefaultData("New Default Data", 1)
        assertTrue(repository.isUpdatedInputDefaultData)
    }

    @Test
    fun `delete input item`() {
        useCase.deleteItem(1, 1, 0)
        assertTrue(repository.isDeletedInputData)
        assertTrue(repository.isUpdatedInputDataPositionsAfterDeleting)
    }

    @Test
    fun `move input item`() {
        useCase.moveItem(1, 0, 2, 1)
        assertTrue(repository.isSetInputDataPosition)
    }

    @Test
    fun `add new input item`() {
        useCase.add(1)
        assertTrue(repository.isInputDataAdded)
    }
}
