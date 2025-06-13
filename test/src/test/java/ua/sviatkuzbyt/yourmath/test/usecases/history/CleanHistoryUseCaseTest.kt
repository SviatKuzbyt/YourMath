package ua.sviatkuzbyt.yourmath.test.usecases.history

import org.junit.Assert.assertTrue
import org.junit.Test
import ua.sviatkuzbyt.yourmath.domain.usecases.history.CleanHistoryUseCase
import ua.sviatkuzbyt.yourmath.test.repositories.FakeHistoryRepository

class CleanHistoryUseCaseTest {

    @Test
    fun `test execute clears history`() {
        val fakeRepository = FakeHistoryRepository()
        val useCase = CleanHistoryUseCase(fakeRepository)

        useCase.execute()

        assertTrue(fakeRepository.cleanHistoryCalled)
    }
}
