package ua.sviatkuzbyt.yourmath.domain.repositories

import ua.sviatkuzbyt.yourmath.domain.structures.FormulaListItem

interface FormulasRepository {
    fun getFormulas(): List<FormulaListItem>
}