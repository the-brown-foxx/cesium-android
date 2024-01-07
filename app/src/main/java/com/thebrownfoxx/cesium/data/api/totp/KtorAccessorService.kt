package com.thebrownfoxx.cesium.data.api.totp

import com.thebrownfoxx.cesium.data.api.HttpRoutes
import com.thebrownfoxx.cesium.data.api.newHttpClient
import com.thebrownfoxx.cesium.data.api.ApiResponse
import com.thebrownfoxx.models.totp.SavedAccessor
import com.thebrownfoxx.models.totp.UnsavedAccessor
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.http.HttpStatusCode
import io.ktor.util.InternalAPI
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch

// TODO: Remove default client
class KtorAccessorService(private val client: HttpClient = newHttpClient()): AccessorService {
        private val accessors = MutableSharedFlow<ApiResponse<List<SavedAccessor>>>()

        private suspend fun updateAccessors() {
//            val httpResponse = client.get(HttpRoutes().accessors)
//            if (httpResponse.status == HttpStatusCode.OK) {
//                accessors.emit(ApiResponse.Success(httpResponse.body()))
//            } else {
//                accessors.emit(ApiResponse.Error(httpResponse.status.description))
//            }
        }

        override fun getAll(): Flow<ApiResponse<List<SavedAccessor>>> {
            return accessors
                .asSharedFlow()
                .also { CoroutineScope(Dispatchers.IO).launch { updateAccessors() } }
        }

        override suspend fun get(id: Int): ApiResponse<SavedAccessor?> {
            TODO("Not yet implemented")
        }

        @OptIn(InternalAPI::class)
        override suspend fun add(accessor: UnsavedAccessor): ApiResponse<SavedAccessor> {
            TODO()
//            return client.post(HttpRoutes.accessors) {
//                contentType(ContentType.Application.Json)
//                body = accessor
//            }.body<SavedAccessor>()
//                .also { updateAccessors() }
        }

        override suspend fun updateName(id: Int, name: String): ApiResponse<String> {
            TODO("Not yet implemented")
        }

        override suspend fun refreshTotpSecret(id: Int): ApiResponse<String> {
            TODO("Not yet implemented")
        }

        override suspend fun delete(id: Int): ApiResponse<String> {
            TODO()
//            return (client.delete(HttpRoutes.deleteAccessor(id)).status == HttpStatusCode.OK)
//                .also { updateAccessors() }
        }
    }