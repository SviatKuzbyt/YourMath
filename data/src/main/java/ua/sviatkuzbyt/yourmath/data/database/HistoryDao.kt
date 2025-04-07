package ua.sviatkuzbyt.yourmath.data.database

import androidx.room.Dao
import androidx.room.Insert
import ua.sviatkuzbyt.yourmath.data.database.entity.HistoryFormulaEntity
import ua.sviatkuzbyt.yourmath.data.database.entity.HistoryInputDataEntity
import ua.sviatkuzbyt.yourmath.data.database.entity.HistoryOutputDataEntity

@Dao
interface HistoryDao{
    @Insert
    fun insertHistoryFormula(entity: HistoryFormulaEntity): Long

    @Insert
    fun insertHistoryInputData(entity: HistoryInputDataEntity)

    @Insert
    fun insertHistoryOutputData(entity: HistoryOutputDataEntity)
}