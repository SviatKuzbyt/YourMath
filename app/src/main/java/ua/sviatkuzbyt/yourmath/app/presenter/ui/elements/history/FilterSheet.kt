package ua.sviatkuzbyt.yourmath.app.presenter.ui.elements.history

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.SheetState
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
import ua.sviatkuzbyt.yourmath.app.presenter.ui.elements.basic.text.SubTittleText
import ua.sviatkuzbyt.yourmath.app.presenter.ui.theme.AppShapes
import ua.sviatkuzbyt.yourmath.app.presenter.ui.theme.AppSizes
import ua.sviatkuzbyt.yourmath.app.presenter.ui.theme.AppTheme
import ua.sviatkuzbyt.yourmath.domain.structures.history.FormulaFilterItem

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FilterSheet(
    state: SheetState,
    filterList: List<FormulaFilterItem>,
    onSelect: (Long) -> Unit,
    onClose: () -> Unit
){
    ModalBottomSheet (
        sheetState = state,
        onDismissRequest = onClose,
        containerColor = AppTheme.colors.container,
        dragHandle = {
            SheetDrag()
        },
        shape = AppShapes.sheet
    ) {
        LazyColumn(
            modifier = Modifier.fillMaxWidth(),
            contentPadding = PaddingValues(start = AppSizes.dp16, end = AppSizes.dp16, bottom = AppSizes.dp16)
        ) {
            item {
                SubTittleText(R.string.filer)
            }
            itemsIndexed(filterList){ position, filter ->
                Row(verticalAlignment = Alignment.CenterVertically) {
                    val text =
                        if (position == 0) stringResource(R.string.all_formulas)
                        else filter.name

                    FilterRadioItem(
                        text = text,
                        isSelect = filter.isSelected,
                        onClick = {
                            onSelect(filter.formulaID)
                        }
                    )
                }
            }
        }
    }
}

@Composable
private fun SheetDrag(){
    Icon(
        imageVector = ImageVector.vectorResource(R.drawable.ic_drag),
        contentDescription = stringResource(R.string.drag),
        tint = AppTheme.colors.textSecondary,
        modifier = Modifier.padding(vertical = AppSizes.dp16)
    )
}

@Composable
private fun FilterRadioItem(
    text: String,
    isSelect: Boolean,
    onClick: () -> Unit
){
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .clickable(
                onClick = onClick,
                role = Role.RadioButton,
                interactionSource = null,
                indication = ripple(color = AppTheme.colors.textSecondary)
            )
    ) {
        RadioIcon(isSelect)
        Text(
            text = text,
            style = AppTheme.types.basic
        )
    }
}

@Composable
fun RadioIcon(isSelect: Boolean){
    val iconRes =
        if (isSelect) R.drawable.ic_radio_selected
        else R.drawable.ic_radio

    val descriptionRes =
        if (isSelect) R.string.selected
        else R.string.no_selected

    val color =
        if(isSelect) AppTheme.colors.primary
        else AppTheme.colors.textPrimary

    Icon(
        imageVector = ImageVector.vectorResource(iconRes),
        contentDescription = stringResource(descriptionRes),
        tint = color,
        modifier = Modifier.size(AppSizes.dp48)
    )
}