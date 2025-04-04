package ua.sviatkuzbyt.yourmath.domain.repositories

import ua.sviatkuzbyt.yourmath.domain.structures.formula.FormulaInfo
import ua.sviatkuzbyt.yourmath.domain.structures.formula.InputDataFormula
import ua.sviatkuzbyt.yourmath.domain.structures.formula.ResultDataFormula
import ua.sviatkuzbyt.yourmath.domain.structures.main.FormulaItemWithPinned

interface FormulasRepository {
    fun getFormulas(): List<FormulaItemWithPinned>
    fun changePinFormula(id: Long, isPin: Boolean)
    fun searchFormulas(searchText: String): List<FormulaItemWithPinned>
    fun getFormulaInfo(formulaID: Long): FormulaInfo
    fun getInputDataFormula(formulaID: Long): List<InputDataFormula>
    fun getFormulaCode(formulaID: Long): String
    fun getEmptyResultDataFormula(formulaID: Long): List<ResultDataFormula>
}