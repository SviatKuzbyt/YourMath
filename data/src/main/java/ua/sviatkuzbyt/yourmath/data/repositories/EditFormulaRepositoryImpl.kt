package ua.sviatkuzbyt.yourmath.data.repositories

import ua.sviatkuzbyt.yourmath.data.database.EditFormulaDao
import ua.sviatkuzbyt.yourmath.data.structures.editor.FormulaNameItemData
import ua.sviatkuzbyt.yourmath.data.structures.transfer.FileDataInputData
import ua.sviatkuzbyt.yourmath.data.structures.transfer.FileDataOutputData
import ua.sviatkuzbyt.yourmath.data.structures.transfer.FormulaToFormatData
import ua.sviatkuzbyt.yourmath.domain.repositories.EditFormulaRepository
import ua.sviatkuzbyt.yourmath.domain.structures.editor.FormulaNameItem
import ua.sviatkuzbyt.yourmath.domain.structures.transfer.FileDataInput
import ua.sviatkuzbyt.yourmath.domain.structures.transfer.FileDataOutput
import ua.sviatkuzbyt.yourmath.domain.structures.transfer.FormulaToFormat
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

    override fun getInputDataToExport(formulaID: Long): List<FileDataInput> {
        return editFormulaDao.getInputDataToExport(formulaID).map {
            mapFileDataInputDataDomain(it)
        }
    }

    override fun getOutputDataToExport(formulaID: Long): List<FileDataOutput> {
        return editFormulaDao.getOutputDataToExport(formulaID).map {
            mapFileDataOutputDataDomain(it)
        }
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

    private fun mapFileDataInputDataDomain(data: FileDataInputData): FileDataInput{
        return FileDataInput(
            label = data.label,
            codeLabel = data.codeLabel,
            defaultData = data.defaultData,
            position = data.position
        )
    }

    private fun mapFileDataOutputDataDomain(data: FileDataOutputData): FileDataOutput{
        return FileDataOutput(
            label = data.label,
            codeLabel = data.codeLabel,
            position = data.position
        )
    }
}