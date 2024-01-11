package com.thebrownfoxx.cesium.ui.screens.accessors

import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.twotone.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.thebrownfoxx.cesium.domain.Accessor
import com.thebrownfoxx.cesium.ui.components.SearchableLazyColumnScreen
import com.thebrownfoxx.cesium.ui.theme.AppTheme

@Composable
fun AccessorsScreen(
    accessors: List<Accessor>,
    searchQuery: String,
    onSearchQueryChange: (String) -> Unit,
    selectedAccessor: Accessor?,
    onSelectAccessor: (Accessor) -> Unit,
    newAccessorName: String?,
    onInitiateAddAccessor: () -> Unit,
    onNewAccessorNameChange: (String) -> Unit,
    onCancelAddAccessor: () -> Unit,
    onConfirmAddAccessor: () -> Unit,
    accessorBeingDeleted: Accessor?,
    onInitiateDeleteAccessor: (Accessor) -> Unit,
    onCancelDeleteAccessor: () -> Unit,
    onConfirmDeleteAccessor: () -> Unit,
    modifier: Modifier = Modifier,
) {
    SearchableLazyColumnScreen(
        title = "Accessors",
        searchQuery = searchQuery,
        onSearchQueryChange = onSearchQueryChange,
        floatingActionButton = {
            FloatingActionButton(onClick = onInitiateAddAccessor) {
                Icon(imageVector = Icons.TwoTone.Add, contentDescription = null)
            }
        },
        modifier = modifier,
    ) {
        items(
            items = accessors,
            key = { it.id!! },
        ) { accessor ->
            AccessorCard(
                accessor = accessor,
                expanded = accessor == selectedAccessor,
                onClick = { onSelectAccessor(accessor) },
                onDelete = { onInitiateDeleteAccessor(accessor) },
            )
        }
    }
    if (newAccessorName != null) {
        AddAccessorDialog(
            accessorName = newAccessorName,
            onAccessorNameChange = onNewAccessorNameChange,
            onCancel = onCancelAddAccessor,
            onDelete = onConfirmAddAccessor,
        )
    }
    if (accessorBeingDeleted != null) {
        DeleteAccessorDialog(
            accessor = accessorBeingDeleted,
            onCancel = onCancelDeleteAccessor,
            onDelete = onConfirmDeleteAccessor,
        )
    }
}

@Preview
@Composable
fun AccessorScreenPreview() {
    val accessors = listOf(
        Accessor(
            id = 1,
            name = "Jericho Diaz",
            totpSecret = "KRUGKIDROVUWG2ZA",
        ),
        Accessor(
            id = 2,
            name = "Justine Manalansan",
            totpSecret = "Pizza"
        ),
    )

    AppTheme {
        AccessorsScreen(
            accessors = accessors,
            searchQuery = "",
            onSearchQueryChange = {},
            selectedAccessor = accessors.first(),
            onSelectAccessor = {},
            newAccessorName = null,
            onInitiateAddAccessor = {},
            onNewAccessorNameChange = {},
            onCancelAddAccessor = {},
            onConfirmAddAccessor = {},
            accessorBeingDeleted = null,
            onInitiateDeleteAccessor = {},
            onCancelDeleteAccessor = {},
            onConfirmDeleteAccessor = {},
        )
    }
}