package ua.sviatkuzbyt.yourmath.data.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import ua.sviatkuzbyt.yourmath.data.database.entity.HistoryFormulaEntity
import ua.sviatkuzbyt.yourmath.data.database.entity.HistoryInputDataEntity
import ua.sviatkuzbyt.yourmath.data.database.entity.HistoryOutputDataEntity
import ua.sviatkuzbyt.yourmath.data.structures.history.FormulaInputWithValueData
import ua.sviatkuzbyt.yourmath.data.structures.history.FormulaResultWithValueData
import ua.sviatkuzbyt.yourmath.data.structures.history.HistoryListItemData

@Dao
interface HistoryDao{
    @Insert
    fun insertHistoryFormula(entity: HistoryFormulaEntity): Long

    @Insert
    fun insertHistoryInputData(entity: HistoryInputDataEntity)

    @Insert
    fun insertHistoryOutputData(entity: HistoryOutputDataEntity)

    @Query("""
        SELECT 
            hf.historyFormulaID as 'historyId', 
            hf.formulaID as 'formulaId', 
            f.name as 'name', 
            hid.value as 'valueInput', 
            hod.value as 'valueOutput',
            hf.date as 'date'
        FROM HistoryFormula hf 
            INNER JOIN Formula f ON hf.formulaID = f.formulaID 
            INNER JOIN HistoryInputData hid ON hf.historyFormulaID = hid.historyFormulaID 
            INNER JOIN HistoryOutputData hod ON hf.historyFormulaID = hod.historyFormulaID 
        GROUP BY hf.historyFormulaID
        ORDER BY hf.historyFormulaID DESC LIMIT :limit OFFSET :offset
    """)
    fun getHistoryItems(offset: Int, limit: Int): List<HistoryListItemData>

    @Query("""
        SELECT id.inputDataID, id.label, id.codeLabel, id.defaultData, hid.value FROM HistoryInputData hid
        INNER JOIN InputData id ON hid.inputDataID = id.inputDataID 
        WHERE hid.historyFormulaID = :historyID
    """)
    fun getInputData(historyID: Long): List<FormulaInputWithValueData>

    @Query("""
        SELECT od.outputDataID, od.label, od.codeLabel, hod.value FROM HistoryOutputData hod 
        INNER JOIN OutputData od ON hod.outputDataID = od.outputDataID
        WHERE hod.historyFormulaID = :historyID
    """)
    fun getOutputData(historyID: Long): List<FormulaResultWithValueData>

    @Query("DELETE FROM HistoryFormula")
    fun deleteAll()
}