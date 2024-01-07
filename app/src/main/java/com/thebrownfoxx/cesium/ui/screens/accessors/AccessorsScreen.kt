package com.thebrownfoxx.cesium.ui.screens.accessors

import androidx.compose.foundation.lazy.items
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
            accessorBeingDeleted = null,
            onInitiateDeleteAccessor = {},
            onCancelDeleteAccessor = {},
            onConfirmDeleteAccessor = {},
        )
    }
}