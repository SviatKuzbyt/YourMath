package ua.sviatkuzbyt.yourmath.test

import androidx.test.platform.app.InstrumentationRegistry
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import ua.sviatkuzbyt.yourmath.data.repositories.EditFormulaRepositoryImpl
import ua.sviatkuzbyt.yourmath.data.repositories.FormulasRepositoryImpl
import ua.sviatkuzbyt.yourmath.data.repositories.JsonRepositoryImpl
import ua.sviatkuzbyt.yourmath.data.repositories.PythonRepositoryImpl
import ua.sviatkuzbyt.yourmath.domain.structures.edit.add.DataOutputToAdd
import ua.sviatkuzbyt.yourmath.domain.structures.edit.add.FormulaToAdd
import ua.sviatkuzbyt.yourmath.domain.structures.formula.FormulaInput
import ua.sviatkuzbyt.yourmath.domain.structures.formula.FormulaResult
import ua.sviatkuzbyt.yourmath.domain.usecases.formula.MathFormulaUseCase
import ua.sviatkuzbyt.yourmath.other.createDatabase

class MathFormulaTest {
    private lateinit var formulasRepository: FormulasRepositoryImpl
    private lateinit var pythonRepository: PythonRepositoryImpl
    private lateinit var jsonRepository: JsonRepositoryImpl
    private lateinit var editFormulaRepository: EditFormulaRepositoryImpl
    private lateinit var useCase: MathFormulaUseCase

    private var formulaId = 0L
    private lateinit var inputs: List<FormulaInput>
    private lateinit var expectedResults: List<FormulaResult>

    @Before
    fun setup() {
        val context = InstrumentationRegistry.getInstrumentation().targetContext
        val db = createDatabase()

        formulasRepository = FormulasRepositoryImpl(db.formulaDao())
        pythonRepository = PythonRepositoryImpl(context)
        jsonRepository = JsonRepositoryImpl()
        editFormulaRepository = EditFormulaRepositoryImpl(db.editFormulaDao())

        useCase = MathFormulaUseCase(
            formulasRepository = formulasRepository,
            pythonRepository = pythonRepository,
            jsonRepository = jsonRepository
        )

        fillData()
    }

    private fun fillData() {
        val a = 5
        val b = a * 2

        val formula = FormulaToAdd(
            name = "Formula test",
            description = "Description of the formula",
            code = "a = int(input['a']);result['b'] = a * 2",
            position = 0,
            isNote = false
        )

        formulaId = editFormulaRepository.addFormula(formula)

        inputs = listOf(
            FormulaInput(
                id = 1,
                label = "a",
                codeLabel = "a",
                defaultData = null,
                data = a.toString()
            )
        )

        val emptyResult = DataOutputToAdd(
            label = "b",
            codeLabel = "b",
            formulaID = formulaId,
            position = 0
        )
        val resultId = editFormulaRepository.addOutputData(emptyResult)

        expectedResults = listOf(
            FormulaResult(
                id = resultId,
                label = "b",
                codeLabel = "b",
                data = b.toString()
            )
        )
    }

    @Test
    fun testMathFormula() {
        val result = useCase.execute(formulaId, inputs)
        Assert.assertEquals(expectedResults, result)
    }
}