package ua.sviatkuzbyt.yourmath.app.presenter.other.basic

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import ua.sviatkuzbyt.yourmath.app.R

data class EmptyScreenInfo(
    @StringRes val message: Int,
    @DrawableRes val icon: Int
){
    companion object{
        fun noFormulas() = EmptyScreenInfo(
            message = R.string.no_formulas,
            icon = R.drawable.ic_no_formulas
        )
        fun noSearchResult() = EmptyScreenInfo(
            message = R.string.no_search_result,
            icon = R.drawable.ic_no_results
        )
        fun noItemsInHistory() = EmptyScreenInfo(
            message = R.string.no_history,
            icon = R.drawable.ic_no_history
        )
        fun noFilterResultHistory() = EmptyScreenInfo(
            message = R.string.no_history_by_filter,
            icon = R.drawable.ic_no_filters
        )

    }
}