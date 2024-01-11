package com.thebrownfoxx.cesium.ui.screens.accessors

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.twotone.Delete
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.thebrownfoxx.components.FilledButton
import com.thebrownfoxx.components.TextButton

@Composable
fun AddAccessorDialog(
    accessorName: String,
    onAccessorNameChange: (String) -> Unit,
    onCancel: () -> Unit,
    onDelete: () -> Unit,
    modifier: Modifier = Modifier,
) {
    AlertDialog(
        icon = { Icon(imageVector = Icons.TwoTone.Delete, contentDescription = null) },
        title = { Text(text = "Add accessor") },
        text = {
            TextField(
                value = accessorName,
                onValueChange = onAccessorNameChange,
                label = { Text(text = "Name") },
                singleLine = true,
            )
        },
        onDismissRequest = onCancel,
        dismissButton = { TextButton(text = "No", onClick = onCancel) },
        confirmButton = { FilledButton(text = "Yes", onClick = onDelete) },
        modifier = modifier,
    )
}