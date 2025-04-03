package ua.sviatkuzbyt.yourmath.domain.structures.formula

data class Formula(
    val info: FormulaInfo,
    val inputData: List<InputDataFormula>,
    val resultData: List<ResultDataFormula>
)