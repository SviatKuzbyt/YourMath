package ua.sviatkuzbyt.yourmath.domain.usecases.history

import ua.sviatkuzbyt.yourmath.domain.repositories.HistoryRepository

class CleanHistoryUseCase(private val repository: HistoryRepository) {
    fun execute(){
        repository.cleanHistory()
    }
}