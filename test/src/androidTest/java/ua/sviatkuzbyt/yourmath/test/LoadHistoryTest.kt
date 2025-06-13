package ua.sviatkuzbyt.yourmath.test

import org.junit.Assert
import org.junit.Before
import org.junit.Test
import ua.sviatkuzbyt.yourmath.data.repositories.EditFormulaRepositoryImpl
import ua.sviatkuzbyt.yourmath.data.repositories.HistoryRepositoryImpl
import ua.sviatkuzbyt.yourmath.domain.structures.edit.add.DataInputToAdd
import ua.sviatkuzbyt.yourmath.domain.structures.edit.add.DataOutputToAdd
import ua.sviatkuzbyt.yourmath.domain.structures.edit.add.FormulaToAdd
import ua.sviatkuzbyt.yourmath.domain.structures.history.HistoryNoFormatItem
import ua.sviatkuzbyt.yourmath.domain.usecases.history.GetHistoryUseCase
import ua.sviatkuzbyt.yourmath.other.createDatabase

class LoadHistoryTest {
    private lateinit var editFormulaRepository: EditFormulaRepositoryImpl
    private lateinit var historyRepository: HistoryRepositoryImpl
    private lateinit var useCase: GetHistoryUseCase
    private lateinit var expectedItems: List<HistoryNoFormatItem>

    @Before
    fun setup() {
        val db = createDatabase()

        val editDao = db.editFormulaDao()
        editFormulaRepository = EditFormulaRepositoryImpl(editDao)

        val historyDao = db.historyDao()
        historyRepository = HistoryRepositoryImpl(historyDao)

        useCase = GetHistoryUseCase(historyRepository)
        fillDatabase()
    }

    private fun fillDatabase() {
        val date = 1L
        val historyInput = "2"
        val historyOutput = "3"

        val formula = FormulaToAdd(
            name = "Formula test",
            description = "Description of the formula",
            code = "some code",
            position = 0,
            isNote = false
        )
        val formulaId = editFormulaRepository.addFormula(formula)

        val inputData = DataInputToAdd(
            label = "Input Data",
            codeLabel = "Input Code",
            defaultData = null,
            formulaID = formulaId,
            position = 0
        )
        val inputId = editFormulaRepository.addInputData(inputData)

        val outputToAdd = DataOutputToAdd(
            label = "Output Data",
            codeLabel = "Output Code",
            formulaID = formulaId,
            position = 0
        )
        val outputId = editFormulaRepository.addOutputData(outputToAdd)

        val historyId = historyRepository.addHistoryFormulaAndGetID(date, formulaId)
        historyRepository.addHistoryInputData(historyInput, inputId, historyId)
        historyRepository.addHistoryOutputData(historyOutput, outputId, historyId)

        expectedItems = listOf(
            HistoryNoFormatItem(
                historyId = historyId,
                formulaId = formulaId,
                name = formula.name,
                date = date,
                valueInput = historyInput,
                valueOutput = historyOutput
            )
        )
    }

    @Test
    fun testLoadHistory() {
        val resultItems = useCase.execute(0, 0, 1)
        Assert.assertEquals(expectedItems, resultItems)
    }
}