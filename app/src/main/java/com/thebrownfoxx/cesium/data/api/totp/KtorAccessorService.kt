package com.thebrownfoxx.cesium.data.api.totp

import android.app.Application
import com.thebrownfoxx.cesium.data.api.ApiResponse
import com.thebrownfoxx.cesium.data.api.HttpRoutes
import com.thebrownfoxx.cesium.data.api.tryApiCall
import com.thebrownfoxx.cesium.data.datastore.jwt
import com.thebrownfoxx.models.totp.SavedAccessor
import com.thebrownfoxx.models.totp.UnsavedAccessor
import io.ktor.client.HttpClient
import io.ktor.client.request.bearerAuth
import io.ktor.client.request.delete
import io.ktor.client.request.get
import io.ktor.client.request.patch
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

// TODO: Remove default client
class KtorAccessorService(
    application: Application,
    private val client: HttpClient,
    private val routes: HttpRoutes,
) : AccessorService {
    private val scope = CoroutineScope(Dispatchers.IO)

    private val jwt = application.jwt.stateIn(scope, SharingStarted.Eagerly, null)
    private val accessors = MutableStateFlow<ApiResponse<List<SavedAccessor>>?>(null)
    override fun getAll(): StateFlow<ApiResponse<List<SavedAccessor>>?> {
        return accessors.asStateFlow()
    }

    private suspend fun refreshAccessors() {
        accessors.value = withContext(Dispatchers.IO) {
            tryApiCall<List<SavedAccessor>> {
                routes.accessors?.let { path ->
                    client.get(path) {
                        bearerAuth(jwt.value?.value.orEmpty())
                    }
                }
            }
        }
    }

    init {
        scope.launch {
            routes.baseUrl.collect {
                refreshAccessors()
            }
        }
    }

    override suspend fun get(id: Int): ApiResponse<SavedAccessor?> {
        return withContext(Dispatchers.IO) {
            tryApiCall<SavedAccessor?> {
                routes.getAccessor(id)?.let { path ->
                    client.get(path) {
                        bearerAuth(jwt.value?.value.orEmpty())
                    }
                }
            }
        }
    }

    override suspend fun add(accessor: UnsavedAccessor): ApiResponse<SavedAccessor> {
        return withContext(Dispatchers.IO) {
            tryApiCall<SavedAccessor> {
                routes.addAccessor?.let { path ->
                    client.post(path) {
                        bearerAuth(jwt.value?.value.orEmpty())
                        setBody(accessor)
                    }
                }
            }
        }
    }

    override suspend fun updateName(id: Int, name: String): ApiResponse<String> {
        return withContext(Dispatchers.IO) {
            tryApiCall<String> {
                routes.updateAccessorName(id)?.let { path ->
                    client.patch(path) {
                        bearerAuth(jwt.value?.value.orEmpty())
                        setBody(name)
                    }
                }
            }
        }
    }

    override suspend fun refreshTotpSecret(id: Int): ApiResponse<String> {
        return withContext(Dispatchers.IO) {
            tryApiCall<String> {
                routes.refreshAccessorTotpSecret(id)?.let { path ->
                    client.patch(path) {
                        bearerAuth(jwt.value?.value.orEmpty())
                    }
                }
            }
        }
    }

    override suspend fun delete(id: Int): ApiResponse<String> {
        return withContext(Dispatchers.IO) {
            tryApiCall<String> {
                routes.deleteAccessor(id)?.let { path ->
                    client.delete(path) {
                        bearerAuth(jwt.value?.value.orEmpty())
                    }
                }
            }
        }
    }
}