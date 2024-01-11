package com.thebrownfoxx.cesium.ui.screens.login

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.thebrownfoxx.cesium.data.api.ApiResponse
import com.thebrownfoxx.cesium.data.api.auth.AdminService
import com.thebrownfoxx.cesium.data.datastore.baseUrl
import com.thebrownfoxx.models.auth.LoginCredentials
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class LoginViewModel(
    private val adminService: AdminService,
    application: Application,
) : AndroidViewModel(application) {
    private val _errors = MutableSharedFlow<String>()
    val errors = _errors.asSharedFlow()

    private val _loggedIn = MutableStateFlow(false)
    val loggedIn = _loggedIn.asStateFlow()

    private val _ipAddress = MutableStateFlow("")
    val ipAddress = _ipAddress.asStateFlow()

    private val _port = MutableStateFlow<Int?>(null)
    val port = _port.asStateFlow()

    private val _password = MutableStateFlow("")
    val password = _password.asStateFlow()

    private val _loading = MutableStateFlow(false)
    val loading = _loading.asStateFlow()

    init {
        viewModelScope.launch {
            application.baseUrl.collect {
                if (it != null) {
                    val (ipAddress, port) = it.removePrefix("http://").split(':')
                    _ipAddress.value = ipAddress
                    _port.value = port.toIntOrNull()
                    authenticate(showError = false)
                }
            }
        }
    }

    suspend fun authenticate(showError: Boolean = true) {
        withContext(Dispatchers.IO) {
            val response = adminService.authenticate()
            if (response is ApiResponse.Error) {
                if (showError) {
                    _errors.emit(response.message)
                }
                _loggedIn.value = false
                return@withContext
            }
            _loggedIn.value = true
        }
    }

    fun setIpAddress(ipAddress: String) {
        val chunks = ipAddress.split('.').filter { it.isNotEmpty() }
        if (
            ".." !in ipAddress &&
            ipAddress.filter { it == '.' }.length <= 3 &&
            chunks.all { it.toIntOrNull() != null } &&
            chunks.all { it.length <= 3 } &&
            chunks.size <= 4
        ) {
            _ipAddress.value = ipAddress
        }
    }

    fun setPort(port: String) {
        _port.update { oldPort ->
            if (port.isBlank()) null else port.toIntOrNull() ?: oldPort
        }
    }

    fun setPassword(password: String) {
        _password.value = password
    }

    fun login() {
        _loading.value = true
        val ipAddress = ipAddress.value
        val port = port.value
        val password = password.value

        if (ipAddress.isBlank() || port == null || password.isBlank()) {
            viewModelScope.launch {
                _errors.emit("Please fill out all fields")
            }
            return
        }
        viewModelScope.launch {
            adminService.setBaseUrl(ipAddress, port)
            adminService.login(LoginCredentials(password))
            authenticate()
            _loading.value = false
        }
    }
}