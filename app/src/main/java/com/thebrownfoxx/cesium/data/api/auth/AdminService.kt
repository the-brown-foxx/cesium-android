package com.thebrownfoxx.cesium.data.api.auth

import com.thebrownfoxx.cesium.CesiumApplication
import com.thebrownfoxx.cesium.data.api.ApiResponse
import com.thebrownfoxx.cesium.data.api.HttpRoutes
import com.thebrownfoxx.models.auth.ChangeCredentials
import com.thebrownfoxx.models.auth.Jwt
import com.thebrownfoxx.models.auth.LoginCredentials

interface AdminService {
    suspend fun setBaseUrl(ipAddress: String, port: Int)
    suspend fun login(credentials: LoginCredentials): ApiResponse<Jwt>
    suspend fun changePassword(credentials: ChangeCredentials): ApiResponse<String>
    suspend fun authenticate(): ApiResponse<String>
}