package ua.sviatkuzbyt.yourmath.data.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Formula")
data class FormulaEntity(
    @PrimaryKey(autoGenerate = true) val formulaID: Long,
    val name: String,
    val description: String?,
    val code: String,
    val isPin: Boolean,
    val isNote: Boolean,
    val position: Int
)
