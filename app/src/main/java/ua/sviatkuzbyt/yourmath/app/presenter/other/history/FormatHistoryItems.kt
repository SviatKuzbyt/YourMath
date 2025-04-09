package ua.sviatkuzbyt.yourmath.app.presenter.other.history

import ua.sviatkuzbyt.yourmath.domain.structures.history.HistoryNoFormatItem
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import javax.inject.Inject

class FormatHistoryItems @Inject constructor() {
    val loadLimit = 25
    var loadOffset = 0
        private set

    private var lastDate = 0L
    private val formatter = DateTimeFormatter.ofPattern("dd MMM yyyy")

    fun convertList(inputList: List<HistoryNoFormatItem>): List<HistoryItem>{
        val newList = mutableListOf<HistoryItem>()

        inputList.forEach {
            if (it.date != lastDate){
                lastDate = it.date
                newList.add(HistoryItem.Date(formatDate(it.date), it.date))
            }
            newList.add(convertToHistoryItemFormula(it))
        }
        loadOffset += loadLimit
        return newList
    }

    fun clear(){
        loadOffset = 0
        lastDate = 0L
    }

    private fun formatDate(date: Long): String{
        val localDate = LocalDate.ofEpochDay(date)
        val formattedDate = localDate.format(formatter)
        return formattedDate
    }

    private fun convertToHistoryItemFormula(item: HistoryNoFormatItem): HistoryItem.Formula {
        return HistoryItem.Formula(
            name = item.name,
            inputOutputData = "${item.valueInput} / ${item.valueOutput}",
            formulaID = item.formulaId,
            historyID = item.historyId
        )
    }
}