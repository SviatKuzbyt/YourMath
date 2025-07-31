package ua.sviatkuzbyt.yourmath.data.database

import androidx.room.Dao
import androidx.room.Query
import kotlinx.coroutines.flow.Flow
import ua.sviatkuzbyt.yourmath.data.structures.formula.FormulaInfoData
import ua.sviatkuzbyt.yourmath.data.structures.formula.FormulaInputData
import ua.sviatkuzbyt.yourmath.data.structures.formula.FormulaResultData
import ua.sviatkuzbyt.yourmath.data.structures.history.FormulaFilterItemData
import ua.sviatkuzbyt.yourmath.data.structures.main.FormulaItemWithPinnedData

@Dao
interface FormulaDao {
    //TODO: remove in future
    @Query("SELECT formulaID, name, isPin, position FROM Formula WHERE isNote=0 ORDER BY position")
    fun getFormulasOld(): List<FormulaItemWithPinnedData>

    @Query("SELECT formulaID, name, isPin, position FROM Formula WHERE isNote=0 ORDER BY position")
    fun flowFormulas(): Flow<List<FormulaItemWithPinnedData>>

    @Query("UPDATE Formula SET isPin=:isPin WHERE formulaID=:id")
    fun changePinFormula(id: Long, isPin: Boolean)

    //TODO: remove in future
    @Query(
        "SELECT formulaID, name, isPin, position " +
        "FROM Formula " +
        "WHERE isNote=0 AND name LIKE :searchText ORDER BY position"
    )
    fun searchFormulas(searchText: String): List<FormulaItemWithPinnedData>

    @Query("SELECT name, description FROM Formula WHERE formulaID = :formulaID LIMIT 1")
    fun getFormulaInfo(formulaID: Long): FormulaInfoData

    @Query(
        "SELECT inputDataID, label, codeLabel, defaultData " +
        "FROM InputData WHERE formulaID = :formulaID ORDER BY position"
    )
    fun getFormulaInputData(formulaID: Long): List<FormulaInputData>

    @Query("SELECT code FROM Formula WHERE formulaID = :formulaID LIMIT 1")
    fun getFormulaCode(formulaID: Long): String

    @Query(
        "SELECT outputDataID, label, codeLabel " +
        "FROM OutputData " +
        "WHERE formulaID = :formulaID ORDER BY position")
    fun getOutputData(formulaID: Long): List<FormulaResultData>

    @Query("SELECT formulaID, name FROM Formula WHERE isNote=0 ORDER BY position")
    fun getFormulaFilterItemList(): List<FormulaFilterItemData>
}