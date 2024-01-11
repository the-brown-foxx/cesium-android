package com.thebrownfoxx.cesium.ui.screens.accessors

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hamthelegend.enchantmentorder.extensions.combineToStateFlow
import com.hamthelegend.enchantmentorder.extensions.search
import com.thebrownfoxx.cesium.data.api.ApiResponse
import com.thebrownfoxx.cesium.data.api.totp.AccessorService
import com.thebrownfoxx.cesium.domain.Accessor
import com.thebrownfoxx.cesium.totp.decrypt
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class AccessorsViewModel(private val service: AccessorService): ViewModel() {
    private val _searchQuery = MutableStateFlow("")
    val searchQuery = _searchQuery.asStateFlow()

    val accessors = combineToStateFlow(
        service.getAll(),
        _searchQuery,
        scope = viewModelScope,
        initialValue = null,
    ) { accessors, searchQuery ->
        if (accessors is ApiResponse.Success) {
            accessors.data.map { exposedAccessor ->
                Accessor(
                    id = exposedAccessor.id,
                    name = exposedAccessor.name,
                    totpSecret = exposedAccessor.totpSecret.decrypt().value,
                )
            }.search(searchQuery) { it.name }
        } else null
    }

    private val _selectedAccessor = MutableStateFlow<Accessor?>(null)
    val selectedAccessor = _selectedAccessor.asStateFlow()

    private val _accessorBeingDeleted = MutableStateFlow<Accessor?>(null)
    val accessorBeingDeleted = _accessorBeingDeleted.asStateFlow()

    fun updateSearchQuery(query: String) {
        _searchQuery.update { query }
    }

    fun selectAccessor(accessor: Accessor) {
        _selectedAccessor.update { oldAccessor ->
            if (oldAccessor?.id == accessor.id) null else accessor
        }
    }

    fun initiateDeleteAccessor(accessor: Accessor) {
        _accessorBeingDeleted.value = accessor
    }

    fun cancelDeleteAccessor() {
        _accessorBeingDeleted.value = null
    }

    fun confirmDeleteAccessor() {
        viewModelScope.launch {
            val accessorBeingDeleted = _accessorBeingDeleted.value
            if (accessorBeingDeleted != null) {
                service.delete(accessorBeingDeleted.id!!)
            }
            _accessorBeingDeleted.value = null
        }
    }
}