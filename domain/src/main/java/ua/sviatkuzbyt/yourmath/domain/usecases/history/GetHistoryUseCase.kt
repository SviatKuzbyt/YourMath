package ua.sviatkuzbyt.yourmath.domain.usecases.history

import ua.sviatkuzbyt.yourmath.domain.repositories.HistoryRepository
import ua.sviatkuzbyt.yourmath.domain.structures.history.HistoryNoFormatItem

class GetHistoryUseCase(private val repository: HistoryRepository) {
    fun execute(formulaID: Long, offset: Int, limit: Int): List<HistoryNoFormatItem>{
        return if (formulaID == 0L){
            repository.getHistoryItems(offset, limit)
        } else{
            repository.getHistoryByFormulaIDItems(formulaID, offset, limit)
        }
    }
}