package ua.sviatkuzbyt.yourmath.test

import org.junit.Assert
import org.junit.Before
import org.junit.Test
import ua.sviatkuzbyt.yourmath.data.repositories.EditFormulaRepositoryImpl
import ua.sviatkuzbyt.yourmath.data.repositories.FormulasRepositoryImpl
import ua.sviatkuzbyt.yourmath.domain.structures.edit.add.DataInputToAdd
import ua.sviatkuzbyt.yourmath.domain.structures.edit.add.FormulaToAdd
import ua.sviatkuzbyt.yourmath.domain.structures.formula.FormulaContent
import ua.sviatkuzbyt.yourmath.domain.structures.formula.FormulaInfo
import ua.sviatkuzbyt.yourmath.domain.structures.formula.FormulaInput
import ua.sviatkuzbyt.yourmath.domain.usecases.formula.GetFormulaUseCase
import ua.sviatkuzbyt.yourmath.other.createDatabase

class LoadFormulaTest {
    private lateinit var editFormulaRepository: EditFormulaRepositoryImpl
    private lateinit var formulasRepository: FormulasRepositoryImpl
    private lateinit var useCase: GetFormulaUseCase

    private lateinit var expectedFormula: FormulaContent
    private var formulaId = 0L

    @Before
    fun setup() {
        val db = createDatabase()

        val editDao = db.editFormulaDao()
        editFormulaRepository = EditFormulaRepositoryImpl(editDao)

        val formulaDao = db.formulaDao()
        formulasRepository = FormulasRepositoryImpl(formulaDao)

        useCase = GetFormulaUseCase(formulasRepository)

        fillDatabase()
    }

    private fun fillDatabase() {
        val formula = FormulaToAdd(
            name = "Formula test",
            description = "Description of the formula",
            code = "some code",
            position = 0,
            isNote = false
        )
        formulaId = editFormulaRepository.addFormula(formula)

        val inputData = DataInputToAdd(
            label = "Input Data",
            codeLabel = "Input Code",
            defaultData = null,
            formulaID = formulaId,
            position = 0
        )
        val inputId = editFormulaRepository.addInputData(inputData)

        expectedFormula = FormulaContent(
            info = FormulaInfo(
                label = formula.name,
                description = formula.description
            ),
            inputData = listOf(
                FormulaInput(
                    id = inputId,
                    label = inputData.label,
                    codeLabel = inputData.codeLabel,
                    defaultData = inputData.defaultData,
                    data = ""
                )
            ),
            resultData = listOf()
        )
    }

    @Test
    fun testLoadFormula() {
        val result = useCase.execute(formulaId)
        Assert.assertEquals(expectedFormula, result)
    }
}