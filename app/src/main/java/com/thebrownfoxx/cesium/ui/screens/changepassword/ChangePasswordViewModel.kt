package com.thebrownfoxx.cesium.ui.screens.changepassword

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.thebrownfoxx.cesium.data.api.ApiResponse
import com.thebrownfoxx.cesium.data.api.auth.AdminService
import com.thebrownfoxx.models.auth.ChangeCredentials
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class ChangePasswordViewModel(private val adminService: AdminService) : ViewModel() {
    private val _success = MutableSharedFlow<Unit>()
    val success = _success.asSharedFlow()

    private val _errors = MutableSharedFlow<String>()
    val errors = _errors.asSharedFlow()

    private val _oldPassword = MutableStateFlow("")
    val oldPassword = _oldPassword.asStateFlow()

    private val _newPassword = MutableStateFlow("")
    val newPassword = _newPassword.asStateFlow()

    private val _repeatPassword = MutableStateFlow("")
    val repeatPassword = _repeatPassword.asStateFlow()

    private val _loading = MutableStateFlow(false)
    val loading = _loading.asStateFlow()

    fun setOldPassword(oldPassword: String) {
        _oldPassword.value = oldPassword
    }

    fun setNewPassword(password: String) {
        _newPassword.value = password
    }

    fun setRepeatPassword(repeatPassword: String) {
        _repeatPassword.value = repeatPassword
    }

    fun changePassword() {
        _loading.value = true

        val oldPassword = oldPassword.value
        val newPassword = newPassword.value
        val repeatPassword = repeatPassword.value

        if (oldPassword.isBlank() || newPassword.isBlank() || repeatPassword.isBlank()) {
            viewModelScope.launch {
                _errors.emit("Please fill out all fields")
            }
            _loading.value = false
            return
        }

        if (newPassword != repeatPassword) {
            viewModelScope.launch {
                _errors.emit("Passwords do not match")
            }
            _loading.value = false
            return
        }

        viewModelScope.launch {
            val response = adminService.changePassword(
                ChangeCredentials(
                    oldPassword = oldPassword,
                    newPassword = newPassword,
                )
            )

            if (response is ApiResponse.Error) {
                _errors.emit(response.message)
            } else {
                _success.emit(Unit)
            }

            _loading.value = false
        }
    }
}