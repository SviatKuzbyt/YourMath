package ua.sviatkuzbyt.yourmath.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import ua.sviatkuzbyt.yourmath.data.database.entity.FormulaEntity
import ua.sviatkuzbyt.yourmath.data.database.entity.HistoryFormulaEntity
import ua.sviatkuzbyt.yourmath.data.database.entity.HistoryInputDataEntity
import ua.sviatkuzbyt.yourmath.data.database.entity.HistoryOutputDataEntity
import ua.sviatkuzbyt.yourmath.data.database.entity.InputDataEntity
import ua.sviatkuzbyt.yourmath.data.database.entity.OutputDataEntity

@Database(
    entities = [
        FormulaEntity::class,
        InputDataEntity::class,
        OutputDataEntity::class,
        HistoryFormulaEntity::class,
        HistoryInputDataEntity::class,
        HistoryOutputDataEntity::class
    ],
    version = 1
)
abstract class AppDatabase: RoomDatabase() {
    abstract fun formulaDao(): FormulaDao
    abstract fun historyDao(): HistoryDao
}