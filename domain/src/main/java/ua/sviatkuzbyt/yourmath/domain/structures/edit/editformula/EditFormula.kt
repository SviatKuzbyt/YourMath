package ua.sviatkuzbyt.yourmath.domain.structures.edit.editformula

data class EditFormula(
    val info: EditFormulaInfo,
    val inputList: List<EditInput>,
    val resultList: List<EditResult>,
)
