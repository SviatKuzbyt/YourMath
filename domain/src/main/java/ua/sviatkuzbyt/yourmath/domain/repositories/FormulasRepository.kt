package ua.sviatkuzbyt.yourmath.domain.repositories

import ua.sviatkuzbyt.yourmath.domain.structures.formula.FormulaInfo
import ua.sviatkuzbyt.yourmath.domain.structures.formula.FormulaInput
import ua.sviatkuzbyt.yourmath.domain.structures.formula.FormulaResult
import ua.sviatkuzbyt.yourmath.domain.structures.main.FormulaItemWithPinned

interface FormulasRepository {
    fun getFormulas(): List<FormulaItemWithPinned>
    fun changePinFormula(id: Long, isPin: Boolean)
    fun searchFormulas(searchText: String): List<FormulaItemWithPinned>
    fun getFormulaInfo(formulaID: Long): FormulaInfo
    fun getFormulaInput(formulaID: Long): List<FormulaInput>
    fun getFormulaCode(formulaID: Long): String
    fun getFormulaResult(formulaID: Long): List<FormulaResult>
}