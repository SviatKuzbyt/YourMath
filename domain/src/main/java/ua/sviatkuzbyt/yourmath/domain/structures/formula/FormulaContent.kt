package ua.sviatkuzbyt.yourmath.domain.structures.formula

data class FormulaContent(
    val info: FormulaInfo,
    val inputData: List<FormulaInput>,
    val resultData: List<FormulaResult>
)