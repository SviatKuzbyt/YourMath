package ua.sviatkuzbyt.yourmath.test.repositories

import ua.sviatkuzbyt.yourmath.domain.repositories.FormulasRepository
import ua.sviatkuzbyt.yourmath.domain.structures.formula.FormulaInfo
import ua.sviatkuzbyt.yourmath.domain.structures.formula.FormulaInput
import ua.sviatkuzbyt.yourmath.domain.structures.formula.FormulaResult
import ua.sviatkuzbyt.yourmath.domain.structures.history.FormulaFilterItem
import ua.sviatkuzbyt.yourmath.domain.structures.main.FormulaItemWithPinned

class FakeFormulasRepository: FormulasRepository {

    val formulas = listOf(
        FormulaItemWithPinned(1, "Formula 1", false, 1),
        FormulaItemWithPinned(2, "Formula 2", true, 2),
        FormulaItemWithPinned(3, "Formula 3", false, 3)
    )

    val searchFormulas = listOf(
        FormulaItemWithPinned(2, "Formula 2", true, 2)
    )

    val formulaInfo = FormulaInfo(
        label = "Formula 1",
        description = "Description of Formula 1"
    )

    val formulaInputs = listOf(
        FormulaInput(id = 1, label = "Input 1", codeLabel = "input1", defaultData = null, data = "data 1"),
        FormulaInput(id = 2, label = "Input 2", codeLabel = "input2", defaultData = null, data = "data 2")
    )

    val formulaCode = "code of formula 1"

    val formulaResults = listOf(
        FormulaResult(id = 1, label = "Output 1", codeLabel = "output1", data = "result 1")
    )

    val formulaFilters = listOf(
        FormulaFilterItem(formulaID = 1, name = "Formula 1", isSelected = false),
        FormulaFilterItem(formulaID = 2, name = "Formula 2", isSelected = true),
        FormulaFilterItem(formulaID = 3, name = "Formula 3", isSelected = false)
    )

    var isChangedPin = false
        private set

    override fun getFormulaWithPinnedList(): List<FormulaItemWithPinned> {
        return formulas
    }

    override fun changePinFormula(id: Long, isPin: Boolean) {
       isChangedPin = true
    }

    override fun searchFormulas(searchText: String): List<FormulaItemWithPinned> {
        return searchFormulas
    }

    override fun getFormulaInfo(formulaID: Long): FormulaInfo {
        return formulaInfo
    }

    override fun getFormulaInput(formulaID: Long): List<FormulaInput> {
        return formulaInputs
    }

    override fun getFormulaCode(formulaID: Long): String {
       return formulaCode
    }

    override fun getFormulaResult(formulaID: Long): List<FormulaResult> {
        return formulaResults
    }

    override fun getFormulaFilterList(): List<FormulaFilterItem> {
        return formulaFilters
    }
}