package ua.sviatkuzbyt.yourmath.test.usecases.formula

import org.junit.Assert.assertEquals
import org.junit.Test
import ua.sviatkuzbyt.yourmath.domain.usecases.formula.MathFormulaUseCase
import ua.sviatkuzbyt.yourmath.test.repositories.FakeFormulasRepository
import ua.sviatkuzbyt.yourmath.test.repositories.FakeJsonRepository
import ua.sviatkuzbyt.yourmath.test.repositories.FakePythonRepository

class MathFormulaUseCaseTest {

    @Test
    fun `test execute performs mathematical operation`() {
        val formulaRepository = FakeFormulasRepository()
        val pythonRepository = FakePythonRepository()
        val jsonRepository = FakeJsonRepository()

        val useCase = MathFormulaUseCase(
            formulasRepository = formulaRepository,
            pythonRepository = pythonRepository,
            jsonRepository = jsonRepository
        )

        val result = useCase.execute(1, formulaRepository.formulaInputs)

        val expected = formulaRepository.formulaResults.map {
            it.copy(data = jsonRepository.inputMap[it.codeLabel])
        }
        assertEquals(expected, result)
    }
}
