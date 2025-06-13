package ua.sviatkuzbyt.yourmath.test

import org.junit.Before
import org.junit.Test
import ua.sviatkuzbyt.yourmath.data.repositories.EditFormulaRepositoryImpl
import ua.sviatkuzbyt.yourmath.domain.repositories.EditFormulaRepository
import ua.sviatkuzbyt.yourmath.domain.structures.edit.add.FormulaToAdd
import ua.sviatkuzbyt.yourmath.domain.usecases.editor.DeleteFormulaUseCase
import ua.sviatkuzbyt.yourmath.other.createDatabase

class DeleteFormulaTest {
    private lateinit var editFormulaRepository: EditFormulaRepository
    private lateinit var useCase: DeleteFormulaUseCase

    private val formula1 = FormulaToAdd(
        name = "Test Formula",
        description = "This is a test formula",
        isNote = false,
        position = 0,
        code = "x^2 + y^2 = z^2",
    )

    private val formula2 = FormulaToAdd(
        name = "Another Formula",
        description = "This is another test formula",
        isNote = false,
        position = 1,
        code = "a + b = c",
    )

    @Before
    fun setup() {
        val db = createDatabase()
        val dao = db.editFormulaDao()

        editFormulaRepository = EditFormulaRepositoryImpl(dao)
        useCase = DeleteFormulaUseCase(editFormulaRepository)

        editFormulaRepository.addFormula(formula1)
        editFormulaRepository.addFormula(formula2)
    }

    @Test
    fun testDeletingFormula() {
        val formulasBefore = editFormulaRepository.getFormulas()
        val formulaIdToDelete = formulasBefore.first().id

        useCase.execute(formulaIdToDelete)

        val formulasAfter = editFormulaRepository.getFormulas()

        assert(!formulasAfter.any { it.id == formulaIdToDelete })
        assert(formulasAfter.size == formulasBefore.size - 1)
    }
}