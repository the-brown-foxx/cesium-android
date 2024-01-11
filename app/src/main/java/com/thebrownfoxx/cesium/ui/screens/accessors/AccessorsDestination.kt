package com.thebrownfoxx.cesium.ui.screens.accessors

import android.app.Activity
import androidx.activity.compose.BackHandler
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.thebrownfoxx.cesium.application
import com.thebrownfoxx.cesium.ui.screens.destinations.ChangePasswordDestination

@Destination
@Composable
fun Accessors(navigator: DestinationsNavigator) {
    val activity = (LocalContext.current as? Activity)

    BackHandler {
        activity?.moveTaskToBack(true)
    }

    val viewModel = viewModel {
        AccessorsViewModel(
            application.accessorService,
            application.adminService,
        )
    }

    with(viewModel) {
        val searchQuery by searchQuery.collectAsStateWithLifecycle()
        val accessors by accessors.collectAsStateWithLifecycle()
//        val selectedAccessor by selectedAccessor.collectAsStateWithLifecycle()
        val newAccessorName by newAccessorName.collectAsStateWithLifecycle()
        val accessorToRefreshTotpSecret by accessorToRefreshTotpSecret.collectAsStateWithLifecycle()
        val accessorToDelete by accessorToDelete.collectAsStateWithLifecycle()
        val moreActionsVisible by moreActionsVisible.collectAsStateWithLifecycle()

        AccessorsScreen(
            accessors = accessors ?: emptyList(),
            searchQuery = searchQuery,
            onSearchQueryChange = ::updateSearchQuery,
//            selectedAccessor = selectedAccessor,
//            onSelectAccessor = ::selectAccessor,
            newAccessorName = newAccessorName,
            onInitiateAddAccessor = ::initiateAddAccessor,
            onNewAccessorNameChange = ::updateNewAccessorName,
            onCancelAddAccessor = ::cancelAddAccessor,
            onConfirmAddAccessor = ::confirmAddAccessor,
            accessorToRefreshTotpSecret = accessorToRefreshTotpSecret,
            onInitiateRefreshTotpSecret = ::initiateRefreshTotpSecret,
            onCancelRefreshTotpSecret = ::cancelRefreshTotpSecret,
            onConfirmRefreshTotpSecret = ::confirmRefreshTotpSecret,
            accessorToDelete = accessorToDelete,
            onInitiateDeleteAccessor = ::initiateDeleteAccessor,
            onCancelDeleteAccessor = ::cancelDeleteAccessor,
            onConfirmDeleteAccessor = ::confirmDeleteAccessor,
            moreActionsVisible = moreActionsVisible,
            onToggleMoreActionsVisible = ::toggleMoreActionsVisible,
            onRefresh = ::refreshAccessors,
            onChangePassword = { navigator.navigate(ChangePasswordDestination) },
            onLogout = {
                logout()
                navigator.navigateUp()
            },
            errors = errors,
        )
    }
}