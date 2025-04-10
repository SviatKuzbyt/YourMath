package ua.sviatkuzbyt.yourmath.app.presenter.screens.editor

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.ripple
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.semantics.Role
import ua.sviatkuzbyt.yourmath.app.R
import ua.sviatkuzbyt.yourmath.app.presenter.ui.elements.basic.ScreenTopBar
import ua.sviatkuzbyt.yourmath.app.presenter.ui.elements.basic.text.SubTittleText
import ua.sviatkuzbyt.yourmath.app.presenter.ui.elements.basic.text.TittleText
import ua.sviatkuzbyt.yourmath.app.presenter.ui.theme.AppShapes
import ua.sviatkuzbyt.yourmath.app.presenter.ui.theme.AppSizes
import ua.sviatkuzbyt.yourmath.app.presenter.ui.theme.AppTheme

@Composable
fun EditorScreen(){
    EditorContent()
}

@Composable
fun EditorContent(){
    val listState = rememberLazyListState()

    Column(modifier = Modifier.fillMaxSize()) {
        ScreenTopBar(
            tittle = stringResource(R.string.editor),
            listState = listState,
            onBack = {}
        )

        LazyColumn(
            state = listState,
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(horizontal = AppSizes.dp16)
        ) {
            item{
                TittleText(R.string.editor)
            }

            item {
                ActionsItems(
                    onImportClick = {},
                    onExportClick = {},
                    onClearClick = {}
                )
            }

            item {
                SubTittleText(
                    textRes = R.string.formulas,
                    modifier = Modifier.padding(top = AppSizes.dp16)
                )
            }
        }
    }
}

@Composable
fun ActionsItems(
    onImportClick: () -> Unit,
    onExportClick: () -> Unit,
    onClearClick: () -> Unit
){
    Column(Modifier.fillMaxWidth()) {
        SubTittleText(R.string.actions)

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    color = AppTheme.colors.container,
                    shape = AppShapes.main
                )
                .padding(vertical = AppSizes.dp12)
        ) {
            ActionItem(
                iconRes = R.drawable.ic_import,
                textRes = R.string.import_formulas,
                onClick = onImportClick
            )
            ActionItem(
                iconRes = R.drawable.ic_export,
                textRes = R.string.export_formulas,
                onClick = onExportClick
            )
            ActionItem(
                iconRes = R.drawable.ic_delete_small,
                textRes = R.string.delete_formulas,
                onClick = onClearClick
            )
        }
    }
}

@Composable
fun ActionItem(
    @DrawableRes iconRes: Int,
    @StringRes textRes: Int,
    onClick: () -> Unit
){
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .height(AppSizes.dp48)
            .clickable(
                onClick = onClick,
                role = Role.Button,
                interactionSource = null,
                indication = ripple(
                    color = AppTheme.colors.textSecondary
                )
            )
            .padding(horizontal = AppSizes.dp20)
    ) {
        Icon(
            imageVector = ImageVector.vectorResource(iconRes),
            contentDescription = stringResource(R.string.icon_for_button),
            tint = AppTheme.colors.primary,
            modifier = Modifier.size(AppSizes.dp16)
        )
        Text(
            text = stringResource(textRes),
            style = AppTheme.types.basic,
            modifier = Modifier.padding(start = AppSizes.dp20)
        )
    }
}

