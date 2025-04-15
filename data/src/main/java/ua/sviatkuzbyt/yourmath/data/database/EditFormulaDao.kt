package ua.sviatkuzbyt.yourmath.data.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import ua.sviatkuzbyt.yourmath.data.database.entity.FormulaEntity
import ua.sviatkuzbyt.yourmath.data.database.entity.InputDataEntity
import ua.sviatkuzbyt.yourmath.data.database.entity.OutputDataEntity
import ua.sviatkuzbyt.yourmath.data.structures.editor.FormulaNameItemData
import ua.sviatkuzbyt.yourmath.data.structures.transfer.FileDataInputData
import ua.sviatkuzbyt.yourmath.data.structures.transfer.FileDataOutputData
import ua.sviatkuzbyt.yourmath.data.structures.transfer.FormulaToFormatData

@Dao
interface EditFormulaDao {
    @Query("SELECT formulaID, name FROM Formula ORDER BY position")
    fun getFormulas(): List<FormulaNameItemData>

    @Query("SELECT formulaID, name FROM Formula ORDER BY position LIMIT ${Int.MAX_VALUE} OFFSET :offset")
    fun getFormulas(offset: Int): List<FormulaNameItemData>

    @Query("DELETE FROM Formula WHERE formulaID = :formulaID")
    fun deleteFormula(formulaID: Long)

    @Query("DELETE FROM Formula")
    fun deleteAll()

    @Query("UPDATE Formula SET position=:position WHERE formulaID=:formulaID")
    fun updateFormulaPosition(formulaID: Long, position: Int)

    @Query("SELECT formulaID, name, description, code, position FROM Formula ORDER BY position")
    fun getFormulasToExport(): List<FormulaToFormatData>

    @Query("SELECT label, codeLabel, defaultData, position FROM InputData WHERE formulaID = :formulaID ORDER BY position")
    fun getInputDataToExport(formulaID: Long): List<FileDataInputData>

    @Query("SELECT label, codeLabel, position FROM OutputData WHERE formulaID = :formulaID ORDER BY position")
    fun getOutputDataToExport(formulaID: Long): List<FileDataOutputData>

    @Query("SELECT COUNT(*) FROM Formula")
    fun getSize(): Int

    @Insert
    fun addFormula(formula: FormulaEntity): Long

    @Insert
    fun addInputData(data: InputDataEntity)

    @Insert
    fun addOutputData(data: OutputDataEntity)
}