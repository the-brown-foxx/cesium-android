package com.thebrownfoxx.cesium.ui.screens.accessors

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.twotone.Add
import androidx.compose.material.icons.twotone.Logout
import androidx.compose.material.icons.twotone.MoreVert
import androidx.compose.material.icons.twotone.Password
import androidx.compose.material.icons.twotone.Refresh
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SmallFloatingActionButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.thebrownfoxx.cesium.domain.Accessor
import com.thebrownfoxx.cesium.ui.components.SearchableLazyColumnScreen
import com.thebrownfoxx.cesium.ui.components.snackbar.SnackbarHost
import com.thebrownfoxx.cesium.ui.components.snackbar.rememberSnackbarHostState
import com.thebrownfoxx.cesium.ui.theme.AppTheme
import com.thebrownfoxx.components.VerticalSpacer
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow

@Composable
fun AccessorsScreen(
    accessors: List<Accessor>,
    searchQuery: String,
    onSearchQueryChange: (String) -> Unit,
//    selectedAccessor: Accessor?,
//    onSelectAccessor: (Accessor) -> Unit,
    newAccessorName: String?,
    onInitiateAddAccessor: () -> Unit,
    onNewAccessorNameChange: (String) -> Unit,
    onCancelAddAccessor: () -> Unit,
    onConfirmAddAccessor: () -> Unit,
    accessorToRefreshTotpSecret: Accessor?,
    onInitiateRefreshTotpSecret: (Accessor) -> Unit,
    onCancelRefreshTotpSecret: () -> Unit,
    onConfirmRefreshTotpSecret: () -> Unit,
    accessorToDelete: Accessor?,
    onInitiateDeleteAccessor: (Accessor) -> Unit,
    onCancelDeleteAccessor: () -> Unit,
    onConfirmDeleteAccessor: () -> Unit,
    moreActionsVisible: Boolean,
    onToggleMoreActionsVisible: () -> Unit,
    onRefresh: () -> Unit,
    onChangePassword: () -> Unit,
    onLogout: () -> Unit,
    errors: Flow<String>,
    modifier: Modifier = Modifier,
) {
    SearchableLazyColumnScreen(
        title = "Accessors",
        searchQuery = searchQuery,
        onSearchQueryChange = onSearchQueryChange,
        floatingActionButton = {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                SmallFloatingActionButton(
                    onClick = onToggleMoreActionsVisible,
                    containerColor = MaterialTheme.colorScheme.secondaryContainer,
                ) {
                    Icon(imageVector = Icons.TwoTone.MoreVert, contentDescription = null)
                }
                VerticalSpacer(height = 8.dp)
                AnimatedVisibility(visible = moreActionsVisible) {
                    Column {
                        SmallFloatingActionButton(
                            onClick = onLogout,
                            containerColor = MaterialTheme.colorScheme.errorContainer,
                        ) {
                            Icon(imageVector = Icons.TwoTone.Logout, contentDescription = null)
                        }
                        VerticalSpacer(height = 8.dp)
                        SmallFloatingActionButton(
                            onClick = onChangePassword,
                            containerColor = MaterialTheme.colorScheme.tertiaryContainer,
                        ) {
                            Icon(imageVector = Icons.TwoTone.Password, contentDescription = null)
                        }
                        VerticalSpacer(height = 8.dp)
                        SmallFloatingActionButton(
                            onClick = onRefresh,
                            containerColor = MaterialTheme.colorScheme.primaryContainer,
                        ) {
                            Icon(imageVector = Icons.TwoTone.Refresh, contentDescription = null)
                        }
                        VerticalSpacer(height = 8.dp)
                    }
                }
                FloatingActionButton(
                    onClick = onInitiateAddAccessor,
                    containerColor = MaterialTheme.colorScheme.primary,
                ) {
                    Icon(imageVector = Icons.TwoTone.Add, contentDescription = null)
                }
            }
        },
        snackbarHost = {
            SnackbarHost(rememberSnackbarHostState(errors))
        },
        modifier = modifier,
    ) {
        items(
            items = accessors,
            key = { it.id!! },
        ) { accessor ->
            AccessorCard(
                accessor = accessor,
//                expanded = accessor == selectedAccessor,
//                onClick = { onSelectAccessor(accessor) },
                onRefreshTotp = { onInitiateRefreshTotpSecret(accessor) },
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
    if (accessorToRefreshTotpSecret != null) {
        RefreshTotpSecretDialog(
            accessor = accessorToRefreshTotpSecret,
            onCancel = onCancelRefreshTotpSecret,
            onDelete = onConfirmRefreshTotpSecret,
        )
    }
    if (accessorToDelete != null) {
        DeleteAccessorDialog(
            accessor = accessorToDelete,
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
//            selectedAccessor = accessors.first(),
//            onSelectAccessor = {},
            newAccessorName = null,
            onInitiateAddAccessor = {},
            onNewAccessorNameChange = {},
            onCancelAddAccessor = {},
            onConfirmAddAccessor = {},
            accessorToRefreshTotpSecret = null,
            onInitiateRefreshTotpSecret = {},
            onCancelRefreshTotpSecret = {},
            onConfirmRefreshTotpSecret = {},
            accessorToDelete = null,
            onInitiateDeleteAccessor = {},
            onCancelDeleteAccessor = {},
            onConfirmDeleteAccessor = {},
            moreActionsVisible = false,
            onToggleMoreActionsVisible = {},
            onRefresh = {},
            onChangePassword = {},
            onLogout = {},
            errors = emptyFlow(),
        )
    }
}