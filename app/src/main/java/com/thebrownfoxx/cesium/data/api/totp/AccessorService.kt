package com.thebrownfoxx.cesium.data.api.totp

import com.thebrownfoxx.cesium.data.api.ApiResponse
import com.thebrownfoxx.models.totp.SavedAccessor
import com.thebrownfoxx.models.totp.UnsavedAccessor
import kotlinx.coroutines.flow.Flow

interface AccessorService {
    fun getAll(): Flow<ApiResponse<List<SavedAccessor>>>
    suspend fun get(id: Int): ApiResponse<SavedAccessor?>
    suspend fun add(accessor: UnsavedAccessor): ApiResponse<SavedAccessor>
    suspend fun updateName(id: Int, name: String): ApiResponse<String>
    suspend fun refreshTotpSecret(id: Int): ApiResponse<String>
    suspend fun delete(id: Int): ApiResponse<String>
}