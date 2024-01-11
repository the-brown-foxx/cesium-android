package com.thebrownfoxx.cesium.data.api

import android.app.Application
import com.hamthelegend.enchantmentorder.extensions.toStateFlow
import com.thebrownfoxx.cesium.data.datastore.baseUrl
import com.thebrownfoxx.cesium.data.datastore.setBaseUrl
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class HttpRoutes(private val application: Application) {
    private val serviceScope = CoroutineScope(Dispatchers.IO)

    val baseUrl = application.baseUrl
        .toStateFlow(scope = serviceScope, initialValue = null)

    private val adminBaseUrl get() = baseUrl.value?.let { "$it/admin" }
    val login get() = adminBaseUrl?.let { "$it/login" }
    val password get() = adminBaseUrl?.let { "$it/password" }
    val authenticate get() = adminBaseUrl?.let { "$it/authenticate" }

    private val accessorsBaseUrl get() = baseUrl.value?.let { "$it/accessors" }

    val accessors get() = accessorsBaseUrl
    fun getAccessor(id: Int) = accessorsBaseUrl?.let { "$it/$id" }
    val addAccessor get() = accessorsBaseUrl
    fun updateAccessorName(id: Int) = getAccessor(id)?.let { "$it/$id/name" }
    fun refreshAccessorTotpSecret(id: Int) = accessorsBaseUrl?.let { "$it/$id/totp-secret" }
    fun deleteAccessor(id: Int) = accessorsBaseUrl?.let { "$it/$id" }

    suspend fun setBaseUrl(ipAddress: String, port: Int) {
        withContext(Dispatchers.IO) {
            application.setBaseUrl(ipAddress, port)
        }
    }
}