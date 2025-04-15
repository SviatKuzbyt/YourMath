package ua.sviatkuzbyt.yourmath.app.presenter.navigation

import kotlinx.serialization.Serializable

@Serializable
data object MainRoute

@Serializable
data class FormulaRoute(
    val formulaID: Long,
    val historyID: Long? = null
)

@Serializable
data object HistoryRoute

@Serializable
data object EditorRoute

@Serializable
data class FormulaEditRoute(val formulaID: Long)

@Serializable
data object ExportRoute

@Serializable
data object ImportRoute