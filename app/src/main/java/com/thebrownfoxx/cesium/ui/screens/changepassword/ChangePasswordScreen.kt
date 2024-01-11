package com.thebrownfoxx.cesium.ui.screens.changepassword

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.twotone.ArrowBack
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.thebrownfoxx.cesium.ui.components.snackbar.SnackbarHost
import com.thebrownfoxx.cesium.ui.components.snackbar.rememberSnackbarHostState
import com.thebrownfoxx.cesium.ui.theme.AppTheme
import com.thebrownfoxx.components.FilledButton
import com.thebrownfoxx.components.IconButton
import com.thebrownfoxx.components.VerticalSpacer
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow

@Composable
fun ChangePasswordScreen(
    oldPassword: String,
    onOldPasswordChange: (String) -> Unit,
    newPassword: String,
    onNewPasswordChange: (String) -> Unit,
    repeatPassword: String,
    onRepeatPasswordChange: (String) -> Unit,
    onConfirm: () -> Unit,
    onNavigateUp: () -> Unit,
    loading: Boolean,
    errors: Flow<String>,
    modifier: Modifier = Modifier,
) {
    val snackbarHostState = rememberSnackbarHostState(messages = errors)

    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) },
        contentWindowInsets = WindowInsets.safeDrawing,
        modifier = modifier,
    ) { contentPadding ->
        Column(
            verticalArrangement = Arrangement.Center,
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(16.dp)
                .padding(contentPadding),
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                IconButton(
                    imageVector = Icons.TwoTone.ArrowBack,
                    contentDescription = "Back button",
                    onClick = onNavigateUp,
                )
                Text(
                    text = "Change Password",
                    style = MaterialTheme.typography.titleLarge,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.weight(1f),
                )
                Spacer(modifier = Modifier.size(48.dp))
            }
            VerticalSpacer(height = 16.dp)
            TextField(
                label = { Text(text = "Old Password") },
                value = oldPassword,
                onValueChange = onOldPasswordChange,
                visualTransformation = PasswordVisualTransformation(),
                singleLine = true,
                modifier = Modifier.fillMaxWidth(),
            )
            VerticalSpacer(height = 16.dp)
            TextField(
                label = { Text(text = "New Password") },
                value = newPassword,
                onValueChange = onNewPasswordChange,
                visualTransformation = PasswordVisualTransformation(),
                singleLine = true,
                modifier = Modifier.fillMaxWidth(),
            )
            VerticalSpacer(height = 16.dp)
            TextField(
                label = { Text(text = "Repeat Password") },
                value = repeatPassword,
                onValueChange = onRepeatPasswordChange,
                visualTransformation = PasswordVisualTransformation(),
                singleLine = true,
                modifier = Modifier.fillMaxWidth(),
            )
            AnimatedVisibility(visible = loading) {
                LinearProgressIndicator(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 24.dp),
                )
            }
            VerticalSpacer(height = 16.dp)
            FilledButton(
                text = "Save",
                onClick = onConfirm,
                enabled = !loading,
                modifier = Modifier.fillMaxWidth(),
            )
        }
    }
}

@Preview
@Composable
fun ChangePasswordScreenPreview() {
    AppTheme {
        ChangePasswordScreen(
            oldPassword = "",
            onOldPasswordChange = {},
            newPassword = "",
            onNewPasswordChange = {},
            repeatPassword = "",
            onRepeatPasswordChange = {},
            onConfirm = {},
            onNavigateUp = {},
            loading = false,
            errors = emptyFlow(),
        )
    }
}