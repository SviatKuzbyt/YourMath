package ua.sviatkuzbyt.yourmath.test.repositories

import ua.sviatkuzbyt.yourmath.domain.repositories.EditFormulaRepository
import ua.sviatkuzbyt.yourmath.domain.structures.edit.FormulaNameItem
import ua.sviatkuzbyt.yourmath.domain.structures.edit.add.DataInputToAdd
import ua.sviatkuzbyt.yourmath.domain.structures.edit.add.DataOutputToAdd
import ua.sviatkuzbyt.yourmath.domain.structures.edit.add.FormulaToAdd
import ua.sviatkuzbyt.yourmath.domain.structures.edit.editformula.EditFormulaInfo
import ua.sviatkuzbyt.yourmath.domain.structures.edit.editformula.EditInput
import ua.sviatkuzbyt.yourmath.domain.structures.edit.editformula.EditResult
import ua.sviatkuzbyt.yourmath.domain.structures.edit.export.ExportDataInput
import ua.sviatkuzbyt.yourmath.domain.structures.edit.export.ExportDataOutput
import ua.sviatkuzbyt.yourmath.domain.structures.edit.export.FormulaToFormat

class FakeEditFormulaRepository: EditFormulaRepository {

    val fakeFormulas = listOf(
        FormulaNameItem(1, "Formula 1", false),
        FormulaNameItem(2, "Formula 2", false)
    )

    val fakeMoreFormulas = listOf(
        FormulaNameItem(1, "Formula 1", false),
        FormulaNameItem(2, "Formula 2", false),
        FormulaNameItem(3, "Formula 3", false),
        FormulaNameItem(4, "Formula 4", false)
    )

    val fakeFormulasToExport = listOf(
        FormulaToFormat(1, "Formula 1", "Code 1", "Description 1", false),
        FormulaToFormat(2, "Formula 2", "Code 2", "Description 2", false)
    )

    val fakeInputsToExport = listOf(
        ExportDataInput("Input 1", "input1", null, 1),
        ExportDataInput("Input 2", "input2", null, 2)
    )

    val fakeOutputsToExport = listOf(
        ExportDataOutput("Output 1", "output1", 1),
        ExportDataOutput("Output 2", "output2", 2)
    )

    val editFormulaInfo = EditFormulaInfo(
        1, "Formula 1", "Description 1", false, "code1"
    )

    val editInputs = listOf(
        EditInput(1, "Input 1", "input1", null),
        EditInput(2, "Input 2", "input2", null)
    )

    val editResults = listOf(
        EditResult(1, "Output 1", "output1"),
        EditResult(2, "Output 2", "output2")
    )

    val fakeFormulasToExportWithNote = listOf(
        FormulaToFormat(1, "Formula 1", "Code 1", "Description 1", false),
        FormulaToFormat(2, "Formula 2", "Code 2", "Description 2", false),
        FormulaToFormat(3, "Formula 3", "Code 3", "Description 3", true)
    )

    var isDeletedFormula = false
        private set

    var isDeletedAllFormulas = false
        private set

    var isSetFormulaPosition = false
        private set

    var isUpdatedPosition = false
        private set

    var isFormulaAdded = false
        private set

    var isInputDataAdded = false
        private set

    var isOutputDataAdded = false
        private set

    var isUpdatedFormulaLabel = false
        private set

    var isUpdatedFormulaDescription = false
        private set

    var isUpdatedInputTextLabel = false
        private set

    var isUpdatedInputCodeLabel = false
        private set

    var isUpdatedInputDefaultData = false
        private set

    var isUpdatedResultTextLabel = false
        private set

    var isUpdatedResultCodeLabel = false
        private set

    var isUpdatedCodeFormula = false
        private set

    var isDeletedInputData = false
        private set

    var isUpdatedInputDataPositionsAfterDeleting = false
        private set

    var isDeletedResultData = false
        private set

    var isUpdatedResultDataPositionsAfterDeleting = false
        private set

    var isSetInputDataPosition = false
        private set

    var isSetResultDataPosition = false
        private set

    var updatedIsNote = false
        private set

    val position = 1
    val sizeOfTable = 2
    val newId = 3L

    override fun getFormulas(): List<FormulaNameItem> {
        return fakeFormulas
    }

    override fun getMoreFormulas(offset: Int): List<FormulaNameItem> {
        return fakeMoreFormulas
    }

    override fun deleteFormula(formulaID: Long) {
        isDeletedFormula = true
    }

    override fun deleteAllFormulas() {
        isDeletedAllFormulas = true
    }

    override fun setFormulaPosition(formulaID: Long, position: Int) {
        isSetFormulaPosition = true
    }

    override fun getFormulasToExport(): List<FormulaToFormat> {
        return fakeFormulasToExport
    }

    override fun getInputDataToExport(formulaID: Long): List<ExportDataInput> {
        return fakeInputsToExport
    }

    override fun getOutputDataToExport(formulaID: Long): List<ExportDataOutput> {
        return fakeOutputsToExport
    }

    override fun updatePositionsAfterDeleting(deletedPosition: Int) {
        isUpdatedPosition = true
    }

    override fun getPosition(formulaID: Long): Int {
        return position
    }

    override fun getTableSize(): Int {
        return sizeOfTable
    }

    override fun addFormula(formula: FormulaToAdd): Long {
        isFormulaAdded = true
        return newId
    }

    override fun addInputData(data: DataInputToAdd): Long {
        isInputDataAdded = true
        return newId
    }

    override fun addOutputData(data: DataOutputToAdd): Long {
        isOutputDataAdded = true
        return newId
    }

    override fun getEditFormulaInfo(formulaID: Long): EditFormulaInfo {
        return editFormulaInfo
    }

    override fun getEditInputs(formulaID: Long): List<EditInput> {
        return editInputs
    }

    override fun getEditResults(formulaID: Long): List<EditResult> {
        return editResults
    }

    override fun updateFormulaLabel(text: String, formulaID: Long) {
        isUpdatedFormulaLabel = true
    }

    override fun updateFormulaDescription(text: String, formulaID: Long) {
        isUpdatedFormulaDescription = true
    }

    override fun updateInputTextLabel(text: String, inputID: Long) {
        isUpdatedInputTextLabel = true
    }

    override fun updateInputCodeLabel(text: String, inputID: Long) {
        isUpdatedInputCodeLabel = true
    }

    override fun updateInputDefaultData(text: String, inputID: Long) {
        isUpdatedInputDefaultData = true
    }

    override fun updateResultTextLabel(text: String, outputID: Long) {
        isUpdatedResultTextLabel = true
    }

    override fun updateResultCodeLabel(text: String, outputID: Long) {
        isUpdatedResultCodeLabel = true
    }

    override fun updateCodeFormula(text: String, formulaID: Long) {
        isUpdatedCodeFormula = true
    }

    override fun deleteInputData(inputID: Long) {
        isDeletedInputData = true
    }

    override fun updateInputDataPositionsAfterDeleting(position: Int, formulaID: Long) {
        isUpdatedInputDataPositionsAfterDeleting = true
    }

    override fun deleteResultData(resultID: Long) {
        isDeletedResultData = true
    }

    override fun updateResultDataPositionsAfterDeleting(position: Int, formulaID: Long) {
        isUpdatedResultDataPositionsAfterDeleting = true
    }

    override fun setInputDataPosition(id: Long, index: Int) {
        isSetInputDataPosition = true
    }

    override fun setResultDataPosition(id: Long, index: Int) {
        isSetResultDataPosition = true
    }

    override fun getInputTableSize(formulaID: Long): Int {
        return sizeOfTable
    }

    override fun getResultTableSize(formulaID: Long): Int {
        return sizeOfTable
    }

    override fun setIsNote(note: Boolean, formulaID: Long) {
        updatedIsNote = true
    }

    override fun getFormulasWithNotesToExport(): List<FormulaToFormat> {
        return fakeFormulasToExportWithNote
    }
}