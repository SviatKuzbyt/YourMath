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
        WHERE f.isNote=0
        GROUP BY hf.historyFormulaID
        ORDER BY hf.historyFormulaID DESC LIMIT :limit OFFSET :offset
    """)
    fun getHistoryItems(offset: Int, limit: Int): List<HistoryListItemData>

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
        WHERE f.isNote=0 AND hf.formulaID = :formulaID
        GROUP BY hf.historyFormulaID
        ORDER BY hf.historyFormulaID DESC LIMIT :limit OFFSET :offset
    """)
    fun getHistoryItemsByFormulaID(formulaID: Long, offset: Int, limit: Int): List<HistoryListItemData>

    @Query("""
SELECT 
    i.inputDataID,
    i.label,
    i.codeLabel,
    i.defaultData,
    i.position,
    h.value AS value
FROM 
    InputData i
LEFT JOIN 
    HistoryInputData h 
    ON i.inputDataID = h.inputDataID AND h.historyFormulaID = :historyID
WHERE 
    i.formulaID = :formulaID
ORDER BY 
    i.position;
    """)
    fun getInputData(formulaID: Long, historyID: Long): List<FormulaInputWithValueData>

    @Query("""
SELECT 
    o.outputDataID,
    o.label,
    o.codeLabel,
    o.position,
    h.value AS value
FROM 
    OutputData o
LEFT JOIN 
    HistoryOutputData h 
    ON o.outputDataID = h.outputDataID AND h.historyFormulaID = :historyID
WHERE 
    o.formulaID = :formulaID
ORDER BY 
    o.position;
    """)
    fun getOutputData(formulaID: Long, historyID: Long): List<FormulaResultWithValueData>

    @Query("DELETE FROM HistoryFormula")
    fun deleteAll()
}