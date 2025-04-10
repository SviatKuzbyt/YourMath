package ua.sviatkuzbyt.yourmath.app.presenter.ui.elements.basic

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyItemScope
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.style.TextAlign
import ua.sviatkuzbyt.yourmath.app.R
import ua.sviatkuzbyt.yourmath.app.presenter.other.basic.EmptyScreenInfo
import ua.sviatkuzbyt.yourmath.app.presenter.ui.theme.AppSizes
import ua.sviatkuzbyt.yourmath.app.presenter.ui.theme.AppTheme

@Composable
fun LazyItemScope.EmptyScreenInList(
    info: EmptyScreenInfo,
    modifier: Modifier = Modifier
){
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
            .fillMaxWidth()
            .fillParentMaxHeight()
            .padding(horizontal = AppSizes.dp16)
    ) {
        Icon(
            imageVector = ImageVector.vectorResource(info.icon),
            contentDescription = stringResource(R.string.no_data),
            tint = AppTheme.colors.textSecondary,
            modifier = Modifier.size(AppSizes.dp32)
        )

        Spacer(Modifier.height(AppSizes.dp16))

        Text(
            text = stringResource(info.message),
            style = AppTheme.types.secondary,
            textAlign = TextAlign.Center
        )
    }
}