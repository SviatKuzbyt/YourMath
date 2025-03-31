package ua.sviatkuzbyt.yourmath.app.ui.states

import ua.sviatkuzbyt.yourmath.app.ui.other.ErrorData
import ua.sviatkuzbyt.yourmath.domain.structures.PinUnpinFormulaItems

enum class ContentOnScreen {
    Content, NoFormulas, NoSearchResult
}

data class MainState(
    val searchText: String = "",
    val formulas: PinUnpinFormulaItems = PinUnpinFormulaItems(listOf(), listOf()),
    val contentOnScreen: ContentOnScreen = ContentOnScreen.Content,
    val errorMessage: ErrorData? = null
)
