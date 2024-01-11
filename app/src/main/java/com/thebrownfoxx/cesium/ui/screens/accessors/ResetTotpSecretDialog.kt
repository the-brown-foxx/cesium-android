package com.thebrownfoxx.cesium.ui.screens.accessors

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.twotone.Refresh
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.thebrownfoxx.cesium.domain.Accessor
import com.thebrownfoxx.components.FilledButton
import com.thebrownfoxx.components.TextButton

@Composable
fun RefreshTotpSecretDialog(
    accessor: Accessor,
    onCancel: () -> Unit,
    onDelete: () -> Unit,
    modifier: Modifier = Modifier,
) {
    AlertDialog(
        icon = { Icon(imageVector = Icons.TwoTone.Refresh, contentDescription = null) },
        title = { Text(text = "Refresh TOTP Secret") },
        text = {
            Text(
                text = "Are you sure you want to reset the TOTP of ${accessor.name}? " +
                        "They won't be able to login using TOTPs generated with their old secret."
            )
        },
        onDismissRequest = onCancel,
        dismissButton = { TextButton(text = "No", onClick = onCancel) },
        confirmButton = { FilledButton(text = "Yes", onClick = onDelete) },
        modifier = modifier,
    )
}