package ua.sviatkuzbyt.yourmath.app.presenter.other

import ua.sviatkuzbyt.yourmath.domain.structures.history.HistoryListItem
import java.time.Instant
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import javax.inject.Inject

class HistoryListManager @Inject constructor() {

    var loadOffset = 0
        private set
    var allDataIsLoaded = false
        private set

    val loadLimit = 25
    private var lastDate = ""
    private var dateID = 1

    fun convertList(inputList: List<HistoryListItem>): List<HistoryItem>{
        val newList = mutableListOf<HistoryItem>()

        inputList.forEach {
            val date = formatDate(it.date)
            if (date != lastDate){
                lastDate = date
                newList.add(HistoryItem.Date(
                    dateID,
                    dateStr = date
                ))
                dateID ++
            }
            newList.add(convertToHistoryItemFormula(it))
        }

        if (inputList.size < loadLimit){
            allDataIsLoaded = true
        } else{
            loadOffset += loadLimit
        }

        return newList
    }

    private fun formatDate(time: Long): String{
        return Instant.ofEpochMilli(time)
            .atZone(ZoneId.systemDefault())
            .format(DateTimeFormatter.ofPattern("dd MMM yyyy"))
    }

    private fun convertToHistoryItemFormula(item: HistoryListItem): HistoryItem.Formula{
        return HistoryItem.Formula(
            name = item.name,
            inputOutputData = "${item.valueInput} / ${item.valueOutput}",
            formulaID = item.formulaId,
            historyID = item.historyId
        )
    }
}