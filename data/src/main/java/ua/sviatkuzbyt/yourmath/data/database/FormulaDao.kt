package ua.sviatkuzbyt.yourmath.data.database

import androidx.room.Dao
import androidx.room.Query
import ua.sviatkuzbyt.yourmath.data.structures.formula.FormulaInfoData
import ua.sviatkuzbyt.yourmath.data.structures.formula.InputDataFormulaData
import ua.sviatkuzbyt.yourmath.data.structures.main.FormulaItemData

@Dao
interface FormulaDao {
    @Query("SELECT formulaID, name, isPin, position FROM Formula")
    fun getFormulas(): List<FormulaItemData>

    @Query("UPDATE Formula SET isPin=:isPin WHERE formulaID=:id")
    fun changePinFormula(id: Long, isPin: Boolean)

    @Query("SELECT formulaID, name, isPin, position FROM Formula WHERE name LIKE :searchText")
    fun searchFormulas(searchText: String): List<FormulaItemData>

    @Query("SELECT name, description FROM Formula WHERE formulaID = :formulaID LIMIT 1")
    fun getFormulaInfo(formulaID: Long): FormulaInfoData

    @Query("SELECT inputDataID, label, defaultData FROM InputData WHERE formulaID = :formulaID")
    fun getFormulaInputData(formulaID: Long): List<InputDataFormulaData>
}