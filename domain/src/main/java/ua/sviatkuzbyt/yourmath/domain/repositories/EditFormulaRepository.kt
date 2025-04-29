package ua.sviatkuzbyt.yourmath.domain.repositories

import ua.sviatkuzbyt.yourmath.domain.structures.editformula.EditFormula
import ua.sviatkuzbyt.yourmath.domain.structures.editformula.EditFormulaInfo
import ua.sviatkuzbyt.yourmath.domain.structures.editformula.EditInput
import ua.sviatkuzbyt.yourmath.domain.structures.editformula.EditResult
import ua.sviatkuzbyt.yourmath.domain.structures.editor.FormulaNameItem
import ua.sviatkuzbyt.yourmath.domain.structures.transfer.ExportDataInput
import ua.sviatkuzbyt.yourmath.domain.structures.transfer.ExportDataOutput
import ua.sviatkuzbyt.yourmath.domain.structures.transfer.FormulaToFormat
import ua.sviatkuzbyt.yourmath.domain.structures.transfer.ImportedDataInput
import ua.sviatkuzbyt.yourmath.domain.structures.transfer.ImportedDataOutput
import ua.sviatkuzbyt.yourmath.domain.structures.transfer.ImportedFormula

interface EditFormulaRepository {
    fun getFormulas(): List<FormulaNameItem>
    fun getMoreFormulas(offset: Int): List<FormulaNameItem>
    fun deleteFormula(formulaID: Long)
    fun deleteAllFormulas()
    fun setFormulaPosition(formulaID: Long, position: Int)
    fun getFormulasToExport(): List<FormulaToFormat>
    fun getInputDataToExport(formulaID: Long): List<ExportDataInput>
    fun getOutputDataToExport(formulaID: Long): List<ExportDataOutput>
    fun updatePositionsAfterDeleting(deletedPosition: Int)
    fun getPosition(formulaID: Long): Int

    fun getTableSize(): Int
    fun addImportedFormulaAndGetID(formula: ImportedFormula): Long
    fun addImportedInputData(data: ImportedDataInput)
    fun addImportedOutputData(data: ImportedDataOutput)

    fun getEditFormulaInfo(formulaID: Long): EditFormulaInfo
    fun getEditInputs(formulaID: Long): List<EditInput>
    fun getEditResults(formulaID: Long): List<EditResult>

    fun updateFormulaLabel(text: String, formulaID: Long)
    fun updateFormulaDescription(text: String, formulaID: Long)
    fun updateInputTextLabel(text: String, inputID: Long)
    fun updateInputCodeLabel(text: String, inputID: Long)
    fun updateInputDefaultData(text: String, inputID: Long)
    fun updateResultTextLabel(text: String, outputID: Long)
    fun updateResultCodeLabel(text: String, outputID: Long)
    fun updateCodeFormula(text: String, formulaID: Long)
    fun deleteInputData(inputID: Long)
    fun updateInputDataPositionsAfterDeleting(position: Int, formulaID: Long)
    fun deleteResultData(resultID: Long)
    fun updateResultDataPositionsAfterDeleting(position: Int, formulaID: Long)
    fun setInputDataPosition(id: Long, index: Int)
    fun setResultDataPosition(id: Long, index: Int)
}