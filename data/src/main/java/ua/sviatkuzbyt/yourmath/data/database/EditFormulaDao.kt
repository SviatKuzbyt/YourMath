package ua.sviatkuzbyt.yourmath.data.database

import androidx.room.Dao
import androidx.room.Query
import ua.sviatkuzbyt.yourmath.data.structures.editor.FormulaNameItemData

@Dao
interface EditFormulaDao {
    @Query("SELECT formulaID, name FROM Formula ORDER BY position")
    fun getFormulas(): List<FormulaNameItemData>

    @Query("DELETE FROM Formula WHERE formulaID = :formulaID")
    fun deleteFormula(formulaID: Long)

    @Query("DELETE FROM Formula")
    fun deleteAll()

    @Query("UPDATE Formula SET position=:position WHERE formulaID=:formulaID")
    fun updateFormulaPosition(formulaID: Long, position: Int)
}