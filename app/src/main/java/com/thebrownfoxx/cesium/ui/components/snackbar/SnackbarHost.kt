package com.thebrownfoxx.cesium.ui.components.snackbar

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SwipeToDismiss
import androidx.compose.material3.SwipeToDismissValue
import androidx.compose.material3.rememberSwipeToDismissState
import androidx.compose.runtime.Composable

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SnackbarHost(state: SnackbarHostState) {
    SnackbarHost(hostState = state.snackbarHostState) { snackbarData ->
        val dismissState = rememberSwipeToDismissState(
            confirmValueChange = { value ->
                if (value != SwipeToDismissValue.Settled) {
                    state.hideSnackbarMessage()
                }
                value != SwipeToDismissValue.Settled
            }
        )

        SwipeToDismiss(
            state = dismissState,
            background = {},
            dismissContent = {
                Snackbar(snackbarData = snackbarData)
            },
        )
    }
}