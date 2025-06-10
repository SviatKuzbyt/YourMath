package ua.sviatkuzbyt.yourmath.test.usecases.history

import org.junit.Assert
import org.junit.Before
import org.junit.Test
import ua.sviatkuzbyt.yourmath.test.repositories.FakeHistoryRepository
import ua.sviatkuzbyt.yourmath.domain.usecases.history.GetHistoryUseCase

class GetHistoryUseCaseTest{

    private lateinit var repository: FakeHistoryRepository
    private lateinit var useCase: GetHistoryUseCase

    @Before
    fun setup(){
        repository = FakeHistoryRepository()
        useCase = GetHistoryUseCase(repository)
    }

    @Test
    fun `Get all history`(){
        val result = useCase.execute(formulaID = 0, offset = 0, limit = 0)
        Assert.assertEquals(repository.fakeItems, result)
    }

    @Test
    fun `Get history by formula ID`(){
        val result = useCase.execute(formulaID = 1, offset = 0, limit = 0)
        Assert.assertEquals(repository.fakeFilterItems, result)
    }
}