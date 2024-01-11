package com.thebrownfoxx.cesium.ui.screens.accessors

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.twotone.Delete
import androidx.compose.material.icons.twotone.Refresh
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.MaterialTheme.typography
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewDynamicColors
import androidx.compose.ui.tooling.preview.PreviewFontScale
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import com.thebrownfoxx.cesium.domain.Accessor
import com.thebrownfoxx.cesium.ui.components.CopyField
import com.thebrownfoxx.cesium.ui.theme.AppTheme
import com.thebrownfoxx.components.FilledButton
import com.thebrownfoxx.components.IconButton
import com.thebrownfoxx.components.VerticalSpacer

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AccessorCard(
    accessor: Accessor,
//    expanded: Boolean,
//    onClick: () -> Unit,
    onDelete: () -> Unit,
    onRefreshTotp: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Card(
//        onClick = onClick,
        modifier = modifier.fillMaxWidth(),
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = accessor.name,
                style = typography.titleMedium,
                modifier = Modifier.padding(horizontal = 16.dp),
            )
            VerticalSpacer(height = 16.dp)
            CopyField(
                label = "ID",
                value = "${accessor.id}",
            )
            VerticalSpacer(height = 16.dp)
            CopyField(
                label = "TOTP Secret",
                value = accessor.totpSecret,
            ) {
                IconButton(
                    imageVector = Icons.TwoTone.Refresh,
                    contentDescription = null,
                    onClick = onRefreshTotp,
                )
            }
            VerticalSpacer(height = 8.dp)
            FilledButton(
                icon = Icons.TwoTone.Delete,
                text = "Delete",
                onClick = onDelete,
                colors = ButtonDefaults.buttonColors(
                    containerColor = colorScheme.error,
                    contentColor = colorScheme.onError,
                ),
                modifier = Modifier.fillMaxWidth(),
            )
        }
    }
}

@PreviewLightDark
@PreviewDynamicColors
@PreviewFontScale
@Preview
@Composable
fun AccessorCardPreview() {
    AppTheme {
        AccessorCard(
            accessor = Accessor(
                name = "Jericho Diaz",
                totpSecret = "KRUGKIDROVUWG2ZA",
            ),
//            expanded = false,
//            onClick = {},
            onRefreshTotp = {},
            onDelete = {},
            modifier = Modifier.padding(16.dp),
        )
    }
}