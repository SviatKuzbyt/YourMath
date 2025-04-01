package ua.sviatkuzbyt.yourmath.data.database.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "InputData",
    foreignKeys = [
        ForeignKey(
            entity = FormulaEntity::class,
            parentColumns = ["formulaID"],
            childColumns = ["formulaID"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)

data class InputDataEntity(
    @PrimaryKey(autoGenerate = true) val inputDataID: Long,
    val label: String,
    val codeLabel: String,
    val defaultData: String? = null,
    val formulaID: Long
)