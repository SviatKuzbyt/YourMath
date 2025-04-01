package ua.sviatkuzbyt.yourmath.data.database.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    tableName = "HistoryFormula",
    foreignKeys = [
        ForeignKey(
            entity = FormulaEntity::class,
            parentColumns = ["formulaID"],
            childColumns = ["formulaID"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class HistoryFormulaEntity(
    @PrimaryKey(autoGenerate = true) val historyFormulaID: Long,
    val date: Long,
    val formulaID: Long
)