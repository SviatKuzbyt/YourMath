package ua.sviatkuzbyt.yourmath.app.presenter.controllers.formulas

import ua.sviatkuzbyt.yourmath.app.presenter.other.ErrorData

data class FormulasState(
    val tittle: String = "",
    val description: String = "",
    val listInputData: List<String> = listOf(),
    val listResultData: List<String> = listOf(),
    val errorMessage: ErrorData? = null,
)
