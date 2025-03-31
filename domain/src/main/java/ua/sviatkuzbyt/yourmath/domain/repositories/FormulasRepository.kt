package ua.sviatkuzbyt.yourmath.domain.repositories

import ua.sviatkuzbyt.yourmath.domain.structures.FormulaItemWithPinned

interface FormulasRepository {
    fun getFormulas(): List<FormulaItemWithPinned>
    fun pinFormula(id: Long)
    fun unpinFormula(id: Long)
}