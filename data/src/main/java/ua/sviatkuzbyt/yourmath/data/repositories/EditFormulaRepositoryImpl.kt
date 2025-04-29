package ua.sviatkuzbyt.yourmath.data.repositories

import ua.sviatkuzbyt.yourmath.data.database.EditFormulaDao
import ua.sviatkuzbyt.yourmath.data.database.entity.FormulaEntity
import ua.sviatkuzbyt.yourmath.data.database.entity.InputDataEntity
import ua.sviatkuzbyt.yourmath.data.database.entity.OutputDataEntity
import ua.sviatkuzbyt.yourmath.data.structures.editformula.EditFormulaInfoData
import ua.sviatkuzbyt.yourmath.data.structures.editformula.EditInputData
import ua.sviatkuzbyt.yourmath.data.structures.editformula.EditResultData
import ua.sviatkuzbyt.yourmath.data.structures.editor.FormulaNameItemData
import ua.sviatkuzbyt.yourmath.data.structures.transfer.FileDataInputData
import ua.sviatkuzbyt.yourmath.data.structures.transfer.FileDataOutputData
import ua.sviatkuzbyt.yourmath.data.structures.transfer.FormulaToFormatData
import ua.sviatkuzbyt.yourmath.domain.repositories.EditFormulaRepository
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
import javax.inject.Inject

class EditFormulaRepositoryImpl @Inject constructor(
    private val editFormulaDao: EditFormulaDao
) : EditFormulaRepository {

    override fun getFormulas(): List<FormulaNameItem> {
        return editFormulaDao.getFormulas().map {
            mapToFormulaNameItemDomain(it)
        }
    }

    override fun getMoreFormulas(offset: Int): List<FormulaNameItem> {
        return editFormulaDao.getFormulas(offset).map {
            mapToFormulaNameItemDomain(it)
        }
    }

    override fun deleteFormula(formulaID: Long) {
        editFormulaDao.deleteFormula(formulaID)
    }

    override fun deleteAllFormulas() {
        editFormulaDao.deleteAll()
    }

    override fun setFormulaPosition(formulaID: Long, position: Int) {
        editFormulaDao.updateFormulaPosition(formulaID, position)
    }

    override fun getFormulasToExport(): List<FormulaToFormat> {
        return editFormulaDao.getFormulasToExport().map {
            mapFormulaToFormatToDomain(it)
        }
    }

    override fun getInputDataToExport(formulaID: Long): List<ExportDataInput> {
        return editFormulaDao.getInputDataToExport(formulaID).map {
            mapFileDataInputDataDomain(it)
        }
    }

    override fun getOutputDataToExport(formulaID: Long): List<ExportDataOutput> {
        return editFormulaDao.getOutputDataToExport(formulaID).map {
            mapFileDataOutputDataDomain(it)
        }
    }

    override fun updatePositionsAfterDeleting(deletedPosition: Int) {
       editFormulaDao.updatePositionsAfterDeleting(deletedPosition)
    }

    override fun getPosition(formulaID: Long): Int {
        return editFormulaDao.getPosition(formulaID)
    }

    override fun getTableSize(): Int {
        return editFormulaDao.getSize()
    }

    override fun addImportedFormulaAndGetID(formula: ImportedFormula): Long {
        val formulaEntity = FormulaEntity(
            formulaID = 0,
            name = formula.name,
            description = formula.description,
            code = formula.code,
            isPin = false,
            position = formula.position
        )

        return editFormulaDao.addFormula(formulaEntity)
    }

    override fun addImportedInputData(data: ImportedDataInput) {
        val inputDataEntity = InputDataEntity(
            inputDataID = 0,
            label = data.label,
            codeLabel = data.codeLabel,
            defaultData = data.defaultData,
            position = data.position,
            formulaID = data.formulaID
        )

        editFormulaDao.addInputData(inputDataEntity)
    }

    override fun addImportedOutputData(data: ImportedDataOutput) {
        val outputDataEntity = OutputDataEntity(
            outputDataID = 0,
            label = data.label,
            codeLabel = data.codeLabel,
            position = data.position,
            formulaID = data.formulaID
        )

        editFormulaDao.addOutputData(outputDataEntity)
    }

    override fun getEditFormulaInfo(formulaID: Long): EditFormulaInfo {
        return mapEditFormulaInfoToDomain(editFormulaDao.getEditFormulaInfo(formulaID))
    }

    override fun getEditInputs(formulaID: Long): List<EditInput> {
        return editFormulaDao.getEditInputs(formulaID).map {
            mapEditInputToDomain(it)
        }
    }

    override fun getEditResults(formulaID: Long): List<EditResult> {
        return editFormulaDao.getEditResults(formulaID).map {
            mapEditResultToDomain(it)
        }
    }

    override fun updateFormulaLabel(text: String, formulaID: Long) {
        editFormulaDao.updateFormulaLabel(text, formulaID)
    }

    override fun updateFormulaDescription(text: String, formulaID: Long) {
        editFormulaDao.updateFormulaDescription(text, formulaID)
    }

    override fun updateInputTextLabel(text: String, inputID: Long) {
        editFormulaDao.updateInputTextLabel(text, inputID)
    }

    override fun updateInputCodeLabel(text: String, inputID: Long) {
        editFormulaDao.updateInputCodeLabel(text, inputID)
    }

    override fun updateInputDefaultData(text: String, inputID: Long) {
        editFormulaDao.updateInputDefaultData(text, inputID)
    }

    override fun updateResultTextLabel(text: String, outputID: Long) {
        editFormulaDao.updateResultTextLabel(text, outputID)
    }

    override fun updateResultCodeLabel(text: String, outputID: Long) {
        editFormulaDao.updateResultCodeLabel(text, outputID)
    }

    override fun updateCodeFormula(text: String, formulaID: Long) {
        editFormulaDao.updateCodeFormula(text, formulaID)
    }

    override fun deleteInputData(inputID: Long) {
        editFormulaDao.deleteInputData(inputID)
    }

    override fun updateInputDataPositionsAfterDeleting(position: Int, formulaID: Long) {
        editFormulaDao.updateInputDataPositionsAfterDeleting(position, formulaID)
    }

    override fun deleteResultData(resultID: Long) {
        editFormulaDao.deleteResultData(resultID)
    }

    override fun updateResultDataPositionsAfterDeleting(position: Int, formulaID: Long) {
        editFormulaDao.updateResultDataPositionsAfterDeleting(position, formulaID)
    }

    override fun setInputDataPosition(id: Long, index: Int) {
        editFormulaDao.setInputDataPosition(id, index)
    }

    override fun setResultDataPosition(id: Long, index: Int) {
        editFormulaDao.setResultDataPosition(id, index)
    }

    private fun mapEditResultToDomain(item: EditResultData): EditResult{
        return EditResult(item.outputDataID, item.label, item.codeLabel)
    }

    private fun mapEditInputToDomain(item: EditInputData): EditInput{
        return EditInput(item.inputDataID, item.label, item.codeLabel, item.defaultData)
    }

    private fun mapEditFormulaInfoToDomain(info: EditFormulaInfoData): EditFormulaInfo {
        return EditFormulaInfo(info.formulaID, info.name, info.description, info.code)
    }

    private fun mapToFormulaNameItemDomain(item: FormulaNameItemData): FormulaNameItem{
        return FormulaNameItem(item.formulaID, item.name)
    }

    private fun mapFormulaToFormatToDomain(data: FormulaToFormatData): FormulaToFormat{
        return FormulaToFormat(
            formulaID = data.formulaID,
            name = data.name,
            description = data.description,
            code = data.code,
            position = data.position
        )
    }

    private fun mapFileDataInputDataDomain(data: FileDataInputData): ExportDataInput{
        return ExportDataInput(
            label = data.label,
            codeLabel = data.codeLabel,
            defaultData = data.defaultData,
            position = data.position
        )
    }

    private fun mapFileDataOutputDataDomain(data: FileDataOutputData): ExportDataOutput{
        return ExportDataOutput(
            label = data.label,
            codeLabel = data.codeLabel,
            position = data.position
        )
    }
}