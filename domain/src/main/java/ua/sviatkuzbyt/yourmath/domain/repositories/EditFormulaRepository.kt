package ua.sviatkuzbyt.yourmath.domain.repositories

import ua.sviatkuzbyt.yourmath.domain.structures.main.FormulaItem

interface EditFormulaRepository {
    fun getFormulas(): List<FormulaItem>
    fun deleteFormula(formulaID: Long)
    fun deleteAllFormulas()
    fun setFormulaPosition(formulaID: Long, position: Int)
}