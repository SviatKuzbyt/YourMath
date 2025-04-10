package ua.sviatkuzbyt.yourmath.data.database

import androidx.room.Dao
import androidx.room.Query
import ua.sviatkuzbyt.yourmath.data.structures.editor.FormulaItemData

@Dao
interface EditFormulaDao {
    @Query("SELECT formulaID, name, position FROM Formula")
    fun getFormulas(): List<FormulaItemData>
}