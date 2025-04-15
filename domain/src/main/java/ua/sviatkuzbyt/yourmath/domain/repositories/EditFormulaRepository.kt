package ua.sviatkuzbyt.yourmath.domain.repositories

import ua.sviatkuzbyt.yourmath.domain.structures.editor.FormulaNameItem
import ua.sviatkuzbyt.yourmath.domain.structures.transfer.ExportDataInput
import ua.sviatkuzbyt.yourmath.domain.structures.transfer.ExportDataOutput
import ua.sviatkuzbyt.yourmath.domain.structures.transfer.FormulaToFormat
import ua.sviatkuzbyt.yourmath.domain.structures.transfer.ImportedDataInput
import ua.sviatkuzbyt.yourmath.domain.structures.transfer.ImportedDataOutput
import ua.sviatkuzbyt.yourmath.domain.structures.transfer.ImportedFormula

interface EditFormulaRepository {
    fun getFormulas(): List<FormulaNameItem>
    fun deleteFormula(formulaID: Long)
    fun deleteAllFormulas()
    fun setFormulaPosition(formulaID: Long, position: Int)
    fun getFormulasToExport(): List<FormulaToFormat>
    fun getInputDataToExport(formulaID: Long): List<ExportDataInput>
    fun getOutputDataToExport(formulaID: Long): List<ExportDataOutput>

    fun getTableSize(): Int
    fun addImportedFormulaAndGetID(formula: ImportedFormula): Long
    fun addImportedInputData(data: ImportedDataInput)
    fun addImportedOutputData(data: ImportedDataOutput)
}