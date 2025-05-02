package ua.sviatkuzbyt.yourmath.app.presenter.ui.elements.basic

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.ScrollableTabRow
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import ua.sviatkuzbyt.yourmath.app.presenter.ui.elements.editformula.tabs.component.TabItem
import ua.sviatkuzbyt.yourmath.app.presenter.ui.theme.AppSizes
import ua.sviatkuzbyt.yourmath.app.presenter.ui.theme.AppTheme

@Composable
fun ScreenTabs(
    tabs: List<Int>,
    selectedTab: Int,
    onSelectTab: (Int) -> Unit,
){
    ScrollableTabRow(
        selectedTabIndex = selectedTab,
        modifier = Modifier.fillMaxWidth(),
        edgePadding = AppSizes.dp12,
        containerColor = AppTheme.colors.background,
        divider = {},
        indicator = {}
    ) {
        tabs.forEachIndexed { index, tab ->
            TabItem(
                selected = selectedTab == index,
                text = stringResource(tab),
                onClick = { onSelectTab(index) }
            )
        }
    }
}