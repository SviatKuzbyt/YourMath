package ua.sviatkuzbyt.yourmath.domain.structures.transfer

data class ExportFormulaItem(
    val name: String,
    val description: String?,
    val inputData: List<ExportDataInput>,
    val outputData: List<ExportDataOutput>,
    val code: String,
    val position: Int
)
