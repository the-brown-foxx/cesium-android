package com.thebrownfoxx.cesium.ui.screens.accessors

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.ramcosta.composedestinations.annotation.Destination
import com.thebrownfoxx.cesium.application

@Destination
@Composable
fun Accessors() {
    val viewModel = viewModel { AccessorsViewModel(application.accessorService) }

    with(viewModel) {
        val searchQuery by searchQuery.collectAsStateWithLifecycle()
        val accessors by accessors.collectAsStateWithLifecycle()
        val selectedAccessor by selectedAccessor.collectAsStateWithLifecycle()
        val newAccessorName by newAccessorName.collectAsStateWithLifecycle()
        val accessorBeingDeleted by accessorBeingDeleted.collectAsStateWithLifecycle()

        AccessorsScreen(
            accessors = accessors ?: emptyList(),
            searchQuery = searchQuery,
            onSearchQueryChange = ::updateSearchQuery,
            selectedAccessor = selectedAccessor,
            onSelectAccessor = ::selectAccessor,
            newAccessorName = newAccessorName,
            onInitiateAddAccessor = ::initiateAddAccessor,
            onNewAccessorNameChange = ::updateNewAccessorName,
            onCancelAddAccessor = ::cancelAddAccessor,
            onConfirmAddAccessor = ::confirmAddAccessor,
            accessorBeingDeleted = accessorBeingDeleted,
            onInitiateDeleteAccessor = ::initiateDeleteAccessor,
            onCancelDeleteAccessor = ::cancelDeleteAccessor,
            onConfirmDeleteAccessor = ::confirmDeleteAccessor,
        )
    }
}