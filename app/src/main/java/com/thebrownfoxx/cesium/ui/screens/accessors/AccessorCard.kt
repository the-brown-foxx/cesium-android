package com.thebrownfoxx.cesium.ui.screens.accessors

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.twotone.Delete
import androidx.compose.material.icons.twotone.ExpandLess
import androidx.compose.material.icons.twotone.ExpandMore
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.MaterialTheme.typography
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
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
import com.thebrownfoxx.components.VerticalSpacer
import com.thebrownfoxx.components.extension.rememberMutableStateOf

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AccessorCard(
    accessor: Accessor,
    expanded: Boolean,
    onClick: () -> Unit,
    onDelete: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val icon = if (expanded) Icons.TwoTone.ExpandLess else Icons.TwoTone.ExpandMore

    Card(
        onClick = onClick,
        modifier = modifier.fillMaxWidth(),
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                    text = accessor.name,
                    style = typography.titleMedium,
                    modifier = Modifier.weight(1f),
                )
                Icon(imageVector = icon, contentDescription = null)
            }
            AnimatedVisibility(visible = expanded) {
                Column {
                    VerticalSpacer(height = 16.dp)
                    CopyField(
                        label = "TOTP Secret",
                        value = accessor.totpSecret,
                    )
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
            expanded = false,
            onClick = {},
            onDelete = {},
            modifier = Modifier.padding(16.dp),
        )
    }
}

@PreviewLightDark
@PreviewDynamicColors
@PreviewFontScale
@Preview
@Composable
fun AccessorCardExpandedPreview() {
    AppTheme {
        AccessorCard(
            accessor = Accessor(
                name = "Jericho Diaz",
                totpSecret = "KRUGKIDROVUWG2ZA",
            ),
            expanded = true,
            onClick = {},
            onDelete = {},
            modifier = Modifier.padding(16.dp),
        )
    }
}

@PreviewLightDark
@PreviewDynamicColors
@PreviewFontScale
@Preview
@Composable
fun AccessorCardInteractivePreview() {
    var expanded by rememberMutableStateOf(false)

    AppTheme {
        AccessorCard(
            accessor = Accessor(
                name = "Jericho Diaz",
                totpSecret = "KRUGKIDROVUWG2ZA",
            ),
            expanded = expanded,
            onClick = { expanded = !expanded },
            onDelete = {},
            modifier = Modifier.padding(16.dp),
        )
    }
}