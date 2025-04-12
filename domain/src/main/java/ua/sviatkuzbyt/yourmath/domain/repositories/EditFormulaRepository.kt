package ua.sviatkuzbyt.yourmath.domain.repositories

import ua.sviatkuzbyt.yourmath.domain.structures.editor.FormulaNameItem
import ua.sviatkuzbyt.yourmath.domain.structures.transfer.FileDataInput
import ua.sviatkuzbyt.yourmath.domain.structures.transfer.FileDataOutput
import ua.sviatkuzbyt.yourmath.domain.structures.transfer.FormulaToFormat

interface EditFormulaRepository {
    fun getFormulas(): List<FormulaNameItem>
    fun deleteFormula(formulaID: Long)
    fun deleteAllFormulas()
    fun setFormulaPosition(formulaID: Long, position: Int)
    fun getFormulasToExport(): List<FormulaToFormat>
    fun getInputDataToExport(formulaID: Long): List<FileDataInput>
    fun getOutputDataToExport(formulaID: Long): List<FileDataOutput>
}