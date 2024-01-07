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

    private var baseUrl = application.baseUrl
        .toStateFlow(scope = serviceScope, initialValue = null)

    val accessors get() = "$baseUrl/accessors"
    fun deleteAccessor(id: Int) = "$baseUrl/accessors/delete/$id"

    private val adminBaseUrl get() = baseUrl.value?.let { "$it/admin" }
    val login get() = adminBaseUrl?.let { "$it/login" }
    val password get() = adminBaseUrl?.let { "$it/password" }
    val authenticate get() = adminBaseUrl?.let { "$it/authenticate" }

    suspend fun setBaseUrl(ipAddress: String, port: Int) {
        withContext(Dispatchers.IO) {
            application.setBaseUrl(ipAddress, port)
        }
    }
}