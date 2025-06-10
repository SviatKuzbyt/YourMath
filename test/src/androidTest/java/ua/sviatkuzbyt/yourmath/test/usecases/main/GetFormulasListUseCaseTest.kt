package ua.sviatkuzbyt.yourmath.test.usecases.main

import androidx.room.Room
import androidx.test.platform.app.InstrumentationRegistry
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import ua.sviatkuzbyt.yourmath.data.database.AppDatabase
import ua.sviatkuzbyt.yourmath.data.database.EditFormulaDao
import ua.sviatkuzbyt.yourmath.data.database.FormulaDao
import ua.sviatkuzbyt.yourmath.data.database.entity.FormulaEntity
import ua.sviatkuzbyt.yourmath.data.repositories.FormulasRepositoryImpl
import ua.sviatkuzbyt.yourmath.domain.structures.main.FormulaItem
import ua.sviatkuzbyt.yourmath.domain.structures.main.SplitFormulaItems
import ua.sviatkuzbyt.yourmath.domain.usecases.main.GetFormulasListUseCase
import ua.sviatkuzbyt.yourmath.domain.usecases.main.SplitFormulaItemsUseCase

class GetFormulasListUseCaseTest {
    private lateinit var db: AppDatabase
    private lateinit var useCase: GetFormulasListUseCase

    private val expectFormulas = SplitFormulaItems(
        pins = listOf(),
        unpins = listOf(
            FormulaItem(1, "Product", 1),
            FormulaItem(2, "Square", 2),
            FormulaItem(3, "Compound interest", 3)
        )
    )

    @Before
    fun setup() {
        val context = InstrumentationRegistry.getInstrumentation().targetContext
        db = Room.inMemoryDatabaseBuilder(context, AppDatabase::class.java)
            .allowMainThreadQueries()
            .build()

        val formulaDao = db.formulaDao()
        val repository = FormulasRepositoryImpl(formulaDao)
        val splitFormulas = SplitFormulaItemsUseCase()

        useCase = GetFormulasListUseCase(repository, splitFormulas)
    }

    private fun fillDB(dao: EditFormulaDao){
        expectFormulas.unpins.forEach {
            dao.addFormula(FormulaEntity(
                formulaID = it.id,
                name = it.name,
                description = null,
                code = "",
                isPin = false,
                isNote = false,
                position = it.position
            ))
        }
    }

    @Test
    fun getFormulasTest() = runBlocking {
        fillDB(db.editFormulaDao())
        val formulas = useCase.execute()
        Assert.assertEquals(expectFormulas, formulas)
    }

    @After
    fun tearDown() {
        db.close()
    }
}