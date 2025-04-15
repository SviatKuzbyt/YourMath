package ua.sviatkuzbyt.yourmath.data.repositories

import ua.sviatkuzbyt.yourmath.data.database.EditFormulaDao
import ua.sviatkuzbyt.yourmath.data.database.entity.FormulaEntity
import ua.sviatkuzbyt.yourmath.data.database.entity.InputDataEntity
import ua.sviatkuzbyt.yourmath.data.database.entity.OutputDataEntity
import ua.sviatkuzbyt.yourmath.data.structures.editor.FormulaNameItemData
import ua.sviatkuzbyt.yourmath.data.structures.transfer.FileDataInputData
import ua.sviatkuzbyt.yourmath.data.structures.transfer.FileDataOutputData
import ua.sviatkuzbyt.yourmath.data.structures.transfer.FormulaToFormatData
import ua.sviatkuzbyt.yourmath.domain.repositories.EditFormulaRepository
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