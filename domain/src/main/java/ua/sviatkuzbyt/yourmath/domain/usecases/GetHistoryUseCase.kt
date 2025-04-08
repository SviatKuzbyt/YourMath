package ua.sviatkuzbyt.yourmath.domain.usecases

import ua.sviatkuzbyt.yourmath.domain.repositories.HistoryRepository
import ua.sviatkuzbyt.yourmath.domain.structures.history.HistoryListItem

class GetHistoryUseCase(private val repository: HistoryRepository) {
    fun execute(offset: Int, limit: Int): List<HistoryListItem>{
        return repository.getHistoryItems(offset, limit)
    }
}