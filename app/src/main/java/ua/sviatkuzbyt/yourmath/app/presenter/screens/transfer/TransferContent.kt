package ua.sviatkuzbyt.yourmath.app.presenter.screens.transfer

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import ua.sviatkuzbyt.yourmath.app.R
import ua.sviatkuzbyt.yourmath.app.presenter.controllers.transfer.TransferIntent
import ua.sviatkuzbyt.yourmath.app.presenter.controllers.transfer.TransferState
import ua.sviatkuzbyt.yourmath.app.presenter.ui.elements.basic.CheckboxBlue
import ua.sviatkuzbyt.yourmath.app.presenter.ui.elements.basic.Container
import ua.sviatkuzbyt.yourmath.app.presenter.ui.elements.basic.button.ButtonLarge
import ua.sviatkuzbyt.yourmath.app.presenter.ui.elements.basic.dialog.DialogButton
import ua.sviatkuzbyt.yourmath.app.presenter.ui.elements.basic.text.TittleText
import ua.sviatkuzbyt.yourmath.app.presenter.ui.theme.AppSizes
import ua.sviatkuzbyt.yourmath.app.presenter.ui.theme.AppTheme

@Composable
fun TransferContent(
    screenState: TransferState,
    onIntent: (TransferIntent) -> Unit
){
    Column(Modifier.fillMaxSize().padding(AppSizes.dp16)) {
        Icon(
            imageVector = ImageVector.vectorResource(screenState.icon),
            contentDescription = stringResource(screenState.tittle),
            tint = AppTheme.colors.textPrimary,
            modifier = Modifier.size(AppSizes.dp32)
        )

        TittleText(
            textRes = screenState.tittle,
            padding = PaddingValues(top = AppSizes.dp16, bottom = AppSizes.dp8)
        )

        Column(modifier = Modifier.weight(1f).verticalScroll(rememberScrollState())) {
            Text(
                text = stringResource(screenState.content),
                style = AppTheme.types.basic,
                modifier = Modifier.fillMaxWidth()
            )

            screenState.isExportNotes?.let { isExportNotes ->
                IsNoteCheckbox(
                    isNote = isExportNotes,
                    onChangeNote = { onIntent(TransferIntent.SetExportNotes(it)) }
                )
            }
        }

        if (screenState.isContinueButton){
            ButtonLarge(R.string.start) {
                onIntent(TransferIntent.Continue)
            }
        } else if(screenState.isExitButton){
            ButtonLarge(R.string.exit) {
                onIntent(TransferIntent.Exit)
            }
        }

        if (screenState.isCanselButton){
            DialogButton(
                textRes = R.string.cancel,
                modifier = Modifier.fillMaxWidth().padding(top = AppSizes.dp8),
                onClick = { onIntent(TransferIntent.Exit) }
            )
        }
    }
}

@Composable
private fun IsNoteCheckbox(
    isNote: Boolean,
    onChangeNote: (Boolean) -> Unit
){
    Spacer(Modifier.size(AppSizes.dp16))
    Container{
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clickable(
                    onClick = { onChangeNote(!isNote) },
                    indication = null,
                    interactionSource = null
                )
                .padding(vertical = AppSizes.dp8)
                .padding(start = AppSizes.dp8, end = AppSizes.dp16),
            verticalAlignment = Alignment.CenterVertically
        )  {

            CheckboxBlue(
                checked = isNote,
                onCheckedChange = onChangeNote
            )

            Spacer(Modifier.size(AppSizes.dp8))

            Text(
                text = stringResource(R.string.export_notes),
                style = AppTheme.types.basic
            )
        }
    }
}