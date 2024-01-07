package com.thebrownfoxx.cesium.ui.screens.accessors

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.twotone.Delete
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.thebrownfoxx.cesium.domain.Accessor
import com.thebrownfoxx.components.FilledButton
import com.thebrownfoxx.components.TextButton

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DeleteAccessorDialog(
    accessor: Accessor,
    onCancel: () -> Unit,
    onDelete: () -> Unit,
    modifier: Modifier = Modifier,
) {
    AlertDialog(
        icon = { Icon(imageVector = Icons.TwoTone.Delete, contentDescription = null) },
        title = { Text(text = "Delete accessor") },
        text = { Text(text = "Are you sure you want to delete ${accessor.name}?") },
        onDismissRequest = onCancel,
        dismissButton = { TextButton(text = "No", onClick = onCancel) },
        confirmButton = { FilledButton(text = "Yes", onClick = onDelete) },
        modifier = modifier,
    )
}