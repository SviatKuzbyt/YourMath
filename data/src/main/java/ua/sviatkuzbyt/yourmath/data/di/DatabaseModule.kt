package ua.sviatkuzbyt.yourmath.data.di

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import ua.sviatkuzbyt.yourmath.data.database.AppDatabase
import ua.sviatkuzbyt.yourmath.data.database.EditFormulaDao
import ua.sviatkuzbyt.yourmath.data.database.FormulaDao
import ua.sviatkuzbyt.yourmath.data.database.HistoryDao
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): AppDatabase {
        return Room.databaseBuilder(
            context = context,
            klass = AppDatabase::class.java,
            name = "YourmathDatabase"
        )
            .createFromAsset("YourmathDatabaseDev.db")
            .build()
    }

    @Provides
    fun provideFormulaDao(database: AppDatabase): FormulaDao {
        return database.formulaDao()
    }

    @Provides
    fun provideHistoryDao(database: AppDatabase): HistoryDao {
        return database.historyDao()
    }

    @Provides
    fun provideEditFormulaDao(database: AppDatabase): EditFormulaDao {
        return database.editFormulaDao()
    }
}