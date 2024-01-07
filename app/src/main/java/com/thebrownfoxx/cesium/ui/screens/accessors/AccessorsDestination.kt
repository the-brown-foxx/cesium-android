package com.thebrownfoxx.cesium.ui.screens.accessors

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.ramcosta.composedestinations.annotation.Destination
import com.thebrownfoxx.cesium.data.api.totp.KtorAccessorService

@Destination
@Composable
fun Accessors() {
    val viewModel = viewModel { AccessorsViewModel(KtorAccessorService()) }

    with(viewModel) {
        val searchQuery by searchQuery.collectAsStateWithLifecycle()
        val accessors by accessors.collectAsStateWithLifecycle()
        val selectedAccessor by selectedAccessor.collectAsStateWithLifecycle()
        val accessorBeingDeleted by accessorBeingDeleted.collectAsStateWithLifecycle()

        AccessorsScreen(
            accessors = accessors ?: emptyList(),
            searchQuery = searchQuery,
            onSearchQueryChange = ::updateSearchQuery,
            selectedAccessor = selectedAccessor,
            onSelectAccessor = ::selectAccessor,
            accessorBeingDeleted = accessorBeingDeleted,
            onInitiateDeleteAccessor = ::initiateDeleteAccessor,
            onCancelDeleteAccessor = ::cancelDeleteAccessor,
            onConfirmDeleteAccessor = ::confirmDeleteAccessor,
        )
    }
}