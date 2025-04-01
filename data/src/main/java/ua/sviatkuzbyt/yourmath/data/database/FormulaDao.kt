package ua.sviatkuzbyt.yourmath.data.database

import androidx.room.Dao
import androidx.room.Query
import ua.sviatkuzbyt.yourmath.data.structures.FormulaItemData
import ua.sviatkuzbyt.yourmath.domain.structures.FormulaItemWithPinned

@Dao
interface FormulaDao {
    @Query("SELECT formulaID, name, isPin, position FROM Formula")
    fun getFormulas(): List<FormulaItemData>

    @Query("UPDATE Formula SET isPin=:isPin WHERE formulaID=:id")
    fun changePinFormula(id: Long, isPin: Boolean)

    @Query("SELECT formulaID, name, isPin, position FROM Formula WHERE name LIKE :searchText")
    fun searchFormulas(searchText: String): List<FormulaItemData>
}