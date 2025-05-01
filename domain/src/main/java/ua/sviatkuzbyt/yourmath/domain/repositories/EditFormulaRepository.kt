package ua.sviatkuzbyt.yourmath.domain.repositories

import ua.sviatkuzbyt.yourmath.domain.structures.editformula.EditFormulaInfo
import ua.sviatkuzbyt.yourmath.domain.structures.editformula.EditInput
import ua.sviatkuzbyt.yourmath.domain.structures.editformula.EditResult
import ua.sviatkuzbyt.yourmath.domain.structures.editor.FormulaNameItem
import ua.sviatkuzbyt.yourmath.domain.structures.transfer.ExportDataInput
import ua.sviatkuzbyt.yourmath.domain.structures.transfer.ExportDataOutput
import ua.sviatkuzbyt.yourmath.domain.structures.transfer.FormulaToFormat
import ua.sviatkuzbyt.yourmath.domain.structures.transfer.DataInputToAdd
import ua.sviatkuzbyt.yourmath.domain.structures.transfer.DataOutputToAdd
import ua.sviatkuzbyt.yourmath.domain.structures.transfer.FormulaToAdd

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
    fun addFormula(formula: FormulaToAdd): Long
    fun addInputData(data: DataInputToAdd): Long
    fun addOutputData(data: DataOutputToAdd): Long

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
    fun getInputTableSize(formulaID: Long): Int
    fun getResultTableSize(formulaID: Long): Int
    fun setIsNote(note: Boolean, formulaID: Long)
}