package ua.sviatkuzbyt.yourmath.domain.usecases.file

data class FileFormulaItem(
    val name: String,
    val description: String,
    val inputData: List<FileInputData>,
    val outputData: List<FileInputData>,
    val code: String,
    val position: Int
)
