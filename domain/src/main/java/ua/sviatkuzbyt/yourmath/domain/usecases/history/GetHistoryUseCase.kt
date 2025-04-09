package ua.sviatkuzbyt.yourmath.domain.usecases.history

import ua.sviatkuzbyt.yourmath.domain.repositories.HistoryRepository
import ua.sviatkuzbyt.yourmath.domain.structures.history.HistoryItem
import ua.sviatkuzbyt.yourmath.domain.structures.history.HistoryNoFormatItem

class GetHistoryUseCase(private val repository: HistoryRepository) {
    private val loadLimit = 25
    private var loadOffset = 0
    private var lastDate = 0L
    var isAllLoaded = false
        private set

    fun execute(loadFromStart: Boolean, formulaID: Long): List<HistoryItem>{
        if (loadFromStart){
            clearData()
        }

        val noFormatHistory = if (formulaID == 0L){
            repository.getHistoryItems(loadOffset, loadLimit)
        } else{
            repository.getHistoryByFormulaIDItems(formulaID, loadOffset, loadLimit)
        }

        if (noFormatHistory.size < loadLimit){
            isAllLoaded = true
        }

        return convertList(noFormatHistory)
    }

    private fun clearData(){
        loadOffset = 0
        lastDate = 0L
        isAllLoaded = false
    }

    private fun convertList(inputList: List<HistoryNoFormatItem>): List<HistoryItem>{
        val newList = mutableListOf<HistoryItem>()

        inputList.forEach {
            if (it.date != lastDate){
                lastDate = it.date
                newList.add(HistoryItem.Date("", it.date))
            }
            newList.add(convertToHistoryItemFormula(it))
        }

        loadOffset += loadLimit
        return newList
    }

    private fun convertToHistoryItemFormula(item: HistoryNoFormatItem): HistoryItem.Formula{
        return HistoryItem.Formula(
            name = item.name,
            inputOutputData = "${item.valueInput} / ${item.valueOutput}",
            formulaID = item.formulaId,
            historyID = item.historyId
        )
    }
}