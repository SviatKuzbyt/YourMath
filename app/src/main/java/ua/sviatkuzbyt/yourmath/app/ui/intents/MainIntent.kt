package ua.sviatkuzbyt.yourmath.app.ui.intents

import ua.sviatkuzbyt.yourmath.domain.structures.FormulaItem

sealed class MainIntent {
    data class PinFormula(val formula: FormulaItem): MainIntent()
    data class UnPinFormula(val formula: FormulaItem): MainIntent()
}