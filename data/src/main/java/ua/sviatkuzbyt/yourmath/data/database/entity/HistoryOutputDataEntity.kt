package ua.sviatkuzbyt.yourmath.data.database.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    tableName = "HistoryOutputData",
    foreignKeys = [
        ForeignKey(
            entity = HistoryFormulaEntity::class,
            parentColumns = ["historyFormulaID"],
            childColumns = ["historyFormulaID"],
            onDelete = ForeignKey.CASCADE
        ),
        ForeignKey(
            entity = OutputDataEntity::class,
            parentColumns = ["outputDataID"],
            childColumns = ["outputDataID"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class HistoryOutputDataEntity(
    @PrimaryKey(autoGenerate = true) val historyInputDataID: Long,
    val value: String,
    val outputDataID: Long,
    val historyFormulaID: Long
)
