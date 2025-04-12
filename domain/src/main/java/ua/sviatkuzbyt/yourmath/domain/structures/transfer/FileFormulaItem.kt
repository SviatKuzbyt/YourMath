package ua.sviatkuzbyt.yourmath.domain.structures.transfer

data class FileFormulaItem(
    val name: String,
    val description: String?,
    val inputData: List<FileDataInput>,
    val outputData: List<FileDataOutput>,
    val code: String,
    val position: Int
)
