package ua.sviatkuzbyt.yourmath.other

import androidx.room.Room
import androidx.test.platform.app.InstrumentationRegistry
import ua.sviatkuzbyt.yourmath.data.database.AppDatabase

fun createDatabase(): AppDatabase {
    val context = InstrumentationRegistry.getInstrumentation().targetContext
    return Room.inMemoryDatabaseBuilder(context, AppDatabase::class.java)
        .allowMainThreadQueries()
        .build()
}