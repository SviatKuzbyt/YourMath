package ua.sviatkuzbyt.yourmath.domain.structures.edit.export

data class ExportFormulaItem(
    val name: String,
    val description: String?,
    val inputData: List<ExportDataInput>,
    val outputData: List<ExportDataOutput>,
    val code: String,
    val isNote: Boolean,
    val position: Int
)
