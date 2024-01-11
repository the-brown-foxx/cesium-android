package com.thebrownfoxx.cesium.data.api.auth

import android.app.Application
import com.hamthelegend.enchantmentorder.extensions.toStateFlow
import com.thebrownfoxx.cesium.data.api.ApiResponse
import com.thebrownfoxx.cesium.data.api.HttpRoutes
import com.thebrownfoxx.cesium.data.api.tryApiCall
import com.thebrownfoxx.cesium.data.datastore.jwt
import com.thebrownfoxx.cesium.data.datastore.setJwt
import com.thebrownfoxx.models.auth.ChangeCredentials
import com.thebrownfoxx.models.auth.Jwt
import com.thebrownfoxx.models.auth.LoginCredentials
import io.ktor.client.HttpClient
import io.ktor.client.request.bearerAuth
import io.ktor.client.request.get
import io.ktor.client.request.patch
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.contentType
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class KtorAdminService(
    private val application: Application,
    private val client: HttpClient,
    private val routes: HttpRoutes,
): AdminService {
    private val serviceScope = CoroutineScope(Dispatchers.IO)

    private val jwt = application.jwt.toStateFlow(scope = serviceScope, initialValue = null)

    override suspend fun setBaseUrl(ipAddress: String, port: Int) {
        routes.setBaseUrl(ipAddress, port)
    }

    override suspend fun login(credentials: LoginCredentials): ApiResponse<Jwt> {
        return withContext(Dispatchers.IO) {
            val jwt = tryApiCall<Jwt> {
                routes.login?.let { path ->
                    client.post(path) {
                        contentType(ContentType.Application.Json)
                        setBody(credentials)
                    }
                }
            }
            if (jwt is ApiResponse.Success) {
                application.setJwt(jwt.data)
            }
            jwt
        }
    }

    override suspend fun changePassword(credentials: ChangeCredentials): ApiResponse<String> {
        return withContext(Dispatchers.IO) {
            tryApiCall {
                routes.password?.let { path ->
                    client.patch(path) {
                        contentType(ContentType.Application.Json)
                        setBody(credentials)
                    }
                }
            }
        }
    }

    override suspend fun authenticate(): ApiResponse<String> {
        return withContext(Dispatchers.IO) {
            tryApiCall {
                val jwt = jwt.value
                routes.authenticate?.let { path ->
                    client.get(path) {
                        bearerAuth(jwt?.value.orEmpty())
                    }
                }
            }
        }
    }
}