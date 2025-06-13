package ua.sviatkuzbyt.yourmath.test.usecases.transfer

import org.junit.Assert.assertTrue
import org.junit.Test
import ua.sviatkuzbyt.yourmath.domain.usecases.transfer.ExportUseCase
import ua.sviatkuzbyt.yourmath.test.repositories.FakeEditFormulaRepository
import ua.sviatkuzbyt.yourmath.test.repositories.FakeFileRepository
import ua.sviatkuzbyt.yourmath.test.repositories.FakeJsonRepository

class ExportUseCaseTest {
    @Test
    fun `test export formulas`() {
        val editFormulaRepository = FakeEditFormulaRepository()
        val jsonRepository = FakeJsonRepository()
        val fileRepository = FakeFileRepository()
        val useCase = ExportUseCase(
            editFormulaRepository,
            jsonRepository,
            fileRepository
        )

        useCase.execute("formuls.json", true)
        assertTrue(fileRepository.isFileWritten)
    }
}