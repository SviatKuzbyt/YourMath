package ua.sviatkuzbyt.yourmath.test

import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import ua.sviatkuzbyt.yourmath.data.repositories.EditFormulaRepositoryImpl
import ua.sviatkuzbyt.yourmath.domain.structures.edit.editformula.EditFormula
import ua.sviatkuzbyt.yourmath.domain.structures.edit.editformula.EditFormulaInfo
import ua.sviatkuzbyt.yourmath.domain.usecases.editformula.CreateFormulaUseCase
import ua.sviatkuzbyt.yourmath.domain.usecases.editformula.GetEditFormulaDataUseCase
import ua.sviatkuzbyt.yourmath.other.createDatabase

class CreateFormulaTest {
    private lateinit var createUseCase: CreateFormulaUseCase
    private lateinit var getUseCase: GetEditFormulaDataUseCase

    private val expectedFormula = EditFormula(
        info = EditFormulaInfo(
            id = 0,
            name = "",
            description = null,
            isNote = true,
            code = ""
        ),
        inputList = listOf(),
        resultList = listOf()
    )

    @Before
    fun setup() {
        val db = createDatabase()
        val editFormulaDao = db.editFormulaDao()
        val editFoRepository = EditFormulaRepositoryImpl(editFormulaDao)

        createUseCase = CreateFormulaUseCase(editFoRepository)
        getUseCase = GetEditFormulaDataUseCase(editFoRepository)
    }

    @Test
    fun testCreateFormula() = runBlocking {
        val createdFormula = createUseCase.execute()
        val createdId = createdFormula.info.id
        val result = getUseCase.execute(createdId)

        val expectedFormulaWithId = expectedFormula.copy(
            info = expectedFormula.info.copy(id = createdId)
        )

        Assert.assertEquals(expectedFormulaWithId, result)
    }
}