package ua.sviatkuzbyt.yourmath.data.database.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    tableName = "HistoryInputData",
    foreignKeys = [
        ForeignKey(
            entity = HistoryFormulaEntity::class,
            parentColumns = ["historyFormulaID"],
            childColumns = ["historyFormulaID"],
            onDelete = ForeignKey.CASCADE
        ),
        ForeignKey(
            entity = InputDataEntity::class,
            parentColumns = ["inputDataID"],
            childColumns = ["inputDataID"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class HistoryInputDataEntity(
    @PrimaryKey(autoGenerate = true) val historyInputDataID: Long,
    val value: String,
    val inputDataID: Long,
    val historyFormulaID: Long
)
