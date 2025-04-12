package ua.sviatkuzbyt.yourmath.domain.structures.transfer

data class FileDataInput(
    val label: String,
    val codeLabel: String,
    val defaultData: String?,
    val position: Int
)
