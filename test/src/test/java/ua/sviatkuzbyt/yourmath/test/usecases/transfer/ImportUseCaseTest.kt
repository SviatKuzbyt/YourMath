package ua.sviatkuzbyt.yourmath.test.usecases.transfer

import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import ua.sviatkuzbyt.yourmath.domain.structures.history.FormulaFilterItem
import ua.sviatkuzbyt.yourmath.domain.usecases.history.GetFiltersUseCase
import ua.sviatkuzbyt.yourmath.domain.usecases.transfer.ExportUseCase
import ua.sviatkuzbyt.yourmath.domain.usecases.transfer.ImportUseCase
import ua.sviatkuzbyt.yourmath.test.repositories.FakeEditFormulaRepository
import ua.sviatkuzbyt.yourmath.test.repositories.FakeFileRepository
import ua.sviatkuzbyt.yourmath.test.repositories.FakeFormulasRepository
import ua.sviatkuzbyt.yourmath.test.repositories.FakeJsonRepository

class ImportUseCaseTest {
    @Test
    fun `test import formulas`() {
        val editFormulaRepository = FakeEditFormulaRepository()
        val jsonRepository = FakeJsonRepository()
        val fileRepository = FakeFileRepository()
        val useCase = ImportUseCase(
            editFormulaRepository,
            jsonRepository,
            fileRepository
        )

        useCase.execute("formuls.json")
        assertTrue(editFormulaRepository.isFormulaAdded)
        assertTrue(editFormulaRepository.isInputDataAdded)
        assertTrue(editFormulaRepository.isOutputDataAdded)
    }
}