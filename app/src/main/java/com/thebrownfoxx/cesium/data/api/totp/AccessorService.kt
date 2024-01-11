package com.thebrownfoxx.cesium.data.api.totp

import com.thebrownfoxx.cesium.data.api.ApiResponse
import com.thebrownfoxx.models.totp.AccessorInfo
import com.thebrownfoxx.models.totp.SavedAccessor
import kotlinx.coroutines.flow.StateFlow

interface AccessorService {
    fun getAll(): StateFlow<ApiResponse<List<SavedAccessor>>?>
    suspend fun get(id: Int): ApiResponse<SavedAccessor?>
    suspend fun add(accessor: AccessorInfo): ApiResponse<SavedAccessor>
    suspend fun updateName(id: Int, name: String): ApiResponse<String>
    suspend fun refreshTotpSecret(id: Int): ApiResponse<String>
    suspend fun delete(id: Int): ApiResponse<String>
}