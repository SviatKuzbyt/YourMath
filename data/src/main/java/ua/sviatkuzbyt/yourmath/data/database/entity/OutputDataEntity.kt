package ua.sviatkuzbyt.yourmath.data.database.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "OutputData",
    foreignKeys = [
        ForeignKey(
            entity = FormulaEntity::class,
            parentColumns = ["formulaID"],
            childColumns = ["formulaID"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)

data class OutputDataEntity(
    @PrimaryKey(autoGenerate = true) val outputDataID: Long,
    val label: String,
    val codeLabel: String,
    val formulaID: Long
)