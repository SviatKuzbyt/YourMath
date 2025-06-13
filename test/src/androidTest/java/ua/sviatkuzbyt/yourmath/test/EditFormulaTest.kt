package ua.sviatkuzbyt.yourmath.test

import org.junit.Assert
import org.junit.Before
import org.junit.Test
import ua.sviatkuzbyt.yourmath.data.repositories.EditFormulaRepositoryImpl
import ua.sviatkuzbyt.yourmath.domain.repositories.EditFormulaRepository
import ua.sviatkuzbyt.yourmath.domain.structures.edit.add.FormulaToAdd
import ua.sviatkuzbyt.yourmath.domain.usecases.editformula.UpdateFormulaDataUseCase
import ua.sviatkuzbyt.yourmath.other.createDatabase

class EditFormulaTest {
    private lateinit var editFormulaRepository: EditFormulaRepository
    private lateinit var useCase: UpdateFormulaDataUseCase
    private var formulaId = 0L

    private val formula = FormulaToAdd(
        name = "Test Formula",
        description = "This is a test formula",
        isNote = false,
        position = 0,
        code = "x^2 + y^2 = z^2",
    )

    private val newName = "Updated Test Formula"
    private val newDescription = "This is an updated test formula"
    private val newCode = "a^2 + b^2 = c^2"

    @Before
    fun setup() {
        val db = createDatabase()
        val dao = db.editFormulaDao()

        editFormulaRepository = EditFormulaRepositoryImpl(dao)
        useCase = UpdateFormulaDataUseCase(editFormulaRepository)

        formulaId = editFormulaRepository.addFormula(formula)
    }

    @Test
    fun testChangeName(){
        useCase.updateLabel(newName, formulaId)
        val updatedFormula = editFormulaRepository.getEditFormulaInfo(formulaId)
        Assert.assertEquals(newName, updatedFormula.name)
    }

    @Test
    fun testChangeDescription(){
        useCase.updateDescription(newDescription, formulaId)
        val updatedFormula = editFormulaRepository.getEditFormulaInfo(formulaId)
        Assert.assertEquals(newDescription, updatedFormula.description)
    }

    @Test
    fun testChangeCode(){
        useCase.updateCode(newCode, formulaId)
        val updatedFormula = editFormulaRepository.getEditFormulaInfo(formulaId)
        Assert.assertEquals(newCode, updatedFormula.code)
    }
}