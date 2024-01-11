package com.thebrownfoxx.cesium.ui.screens.accessors

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hamthelegend.enchantmentorder.extensions.combineToStateFlow
import com.hamthelegend.enchantmentorder.extensions.search
import com.thebrownfoxx.cesium.data.api.ApiResponse
import com.thebrownfoxx.cesium.data.api.auth.AdminService
import com.thebrownfoxx.cesium.data.api.totp.AccessorService
import com.thebrownfoxx.cesium.domain.Accessor
import com.thebrownfoxx.cesium.totp.decrypt
import com.thebrownfoxx.models.totp.AccessorInfo
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class AccessorsViewModel(
    private val service: AccessorService,
    private val adminService: AdminService,
): ViewModel() {
    private val _errors = MutableSharedFlow<String>()
    val errors = _errors.asSharedFlow()

    private val _searchQuery = MutableStateFlow("")
    val searchQuery = _searchQuery.asStateFlow()

    val accessors = combineToStateFlow(
        service.getAll(),
        _searchQuery,
        scope = viewModelScope,
        initialValue = null,
    ) { accessorsResponse, searchQuery ->
        if (accessorsResponse is ApiResponse.Success) {
            accessorsResponse.data.map { exposedAccessor ->
                Accessor(
                    id = exposedAccessor.id,
                    name = exposedAccessor.name,
                    totpSecret = exposedAccessor.totpSecret.decrypt().value,
                )
            }.search(searchQuery) { it.name }
        } else {
            viewModelScope.launch {
                _errors.emit((accessorsResponse as ApiResponse.Error).message)
            }
            null
        }
    }

//    private val _selectedAccessor = MutableStateFlow<Accessor?>(null)
//    val selectedAccessor = _selectedAccessor.asStateFlow()

    private val _newAccessorName = MutableStateFlow<String?>(null)
    val newAccessorName = _newAccessorName.asStateFlow()

    private val _accessorToRefreshTotpSecret = MutableStateFlow<Accessor?>(null)
    val accessorToRefreshTotpSecret = _accessorToRefreshTotpSecret.asStateFlow()

    private val _accessorToDelete = MutableStateFlow<Accessor?>(null)
    val accessorToDelete = _accessorToDelete.asStateFlow()

    private val _moreActionsVisible = MutableStateFlow(false)
    val moreActionsVisible = _moreActionsVisible.asStateFlow()

    fun updateSearchQuery(query: String) {
        _searchQuery.update { query }
    }

//    fun selectAccessor(accessor: Accessor) {
//        _selectedAccessor.update { oldAccessor ->
//            if (oldAccessor?.id == accessor.id) null else accessor
//        }
//    }

    fun initiateAddAccessor() {
        _newAccessorName.value = ""
    }

    fun updateNewAccessorName(name: String) {
        _newAccessorName.value = name
    }

    fun cancelAddAccessor() {
        _newAccessorName.value = null
    }

    fun confirmAddAccessor() {
        viewModelScope.launch {
            val newAccessorName = _newAccessorName.value
            if (newAccessorName != null) {
                val response = service.add(AccessorInfo(name = newAccessorName))
                if (response is ApiResponse.Error) {
                    _errors.emit(response.message)
                }
            }
            _newAccessorName.value = null
        }
    }

    fun initiateRefreshTotpSecret(accessor: Accessor) {
        _accessorToRefreshTotpSecret.value = accessor
    }

    fun cancelRefreshTotpSecret() {
        _accessorToRefreshTotpSecret.value = null
    }

    fun confirmRefreshTotpSecret() {
        viewModelScope.launch {
            val accessorToRefreshTotpSecret = _accessorToRefreshTotpSecret.value
            if (accessorToRefreshTotpSecret != null) {
                val response = service.refreshTotpSecret(accessorToRefreshTotpSecret.id!!)
                if (response is ApiResponse.Error) {
                    _errors.emit(response.message)
                }
            }
            _accessorToRefreshTotpSecret.value = null
        }
    }

    fun initiateDeleteAccessor(accessor: Accessor) {
        _accessorToDelete.value = accessor
    }

    fun cancelDeleteAccessor() {
        _accessorToDelete.value = null
    }

    fun confirmDeleteAccessor() {
        viewModelScope.launch {
            val accessorBeingDeleted = _accessorToDelete.value
            if (accessorBeingDeleted != null) {
                service.delete(accessorBeingDeleted.id!!)
            }
            _accessorToDelete.value = null
        }
    }

    fun toggleMoreActionsVisible() {
        _moreActionsVisible.update { !it }
    }

    fun refreshAccessors() {
        viewModelScope.launch {
            service.refreshAccessors()
        }
    }

    fun logout() {
        viewModelScope.launch {
            adminService.logout()
        }
    }
}