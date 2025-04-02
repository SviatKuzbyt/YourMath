package ua.sviatkuzbyt.yourmath.app.presenter.controllers.main

import ua.sviatkuzbyt.yourmath.domain.structures.FormulaItem

sealed class MainIntent {
    data class PinFormula(val formula: FormulaItem): MainIntent()
    data class UnPinFormula(val formula: FormulaItem): MainIntent()
    data object CloseDialog: MainIntent()
    data class ChangeSearchText(val newText: String): MainIntent()
}