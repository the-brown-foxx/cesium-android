package com.thebrownfoxx.cesium.ui.screens.login

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.thebrownfoxx.cesium.application
import com.thebrownfoxx.cesium.ui.screens.destinations.AccessorsDestination

@Destination(start = true)
@Composable
fun Login(
    navigator: DestinationsNavigator,
    viewModel: LoginViewModel = viewModel {
        LoginViewModel(
            application.adminService,
            application,
        )
    },
) {
    with(viewModel) {
        val loggedIn by loggedIn.collectAsStateWithLifecycle()
        val ipAddress by ipAddress.collectAsStateWithLifecycle()
        val port by port.collectAsStateWithLifecycle()
        val password by password.collectAsStateWithLifecycle()
        val loading by loading.collectAsStateWithLifecycle()

        LaunchedEffect(loggedIn) {
            authenticate(showError = false)
            if (loggedIn) navigator.navigate(AccessorsDestination)
        }

        LoginScreen(
            ipAddress = ipAddress,
            onIpAddressChange = ::setIpAddress,
            port = port,
            onPortChange = ::setPort,
            password = password,
            onPasswordChange = ::setPassword,
            loading = loading,
            onLogin = ::login,
            errors = errors,
        )
    }
}