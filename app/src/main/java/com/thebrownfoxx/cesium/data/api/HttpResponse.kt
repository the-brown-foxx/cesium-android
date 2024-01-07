package com.thebrownfoxx.cesium.data.api

import io.ktor.client.call.body
import io.ktor.client.statement.HttpResponse
import io.ktor.http.HttpStatusCode

suspend inline fun <reified T> tryApiCall(block: () -> HttpResponse?): ApiResponse<T> {
    return try {
        with(block()) {
            if (this == null) {
                return ApiResponse.Error("Host address not set")
            }
            if (status != HttpStatusCode.OK) {
                var error = status.description
                val body = body<String?>()
                if (!body.isNullOrEmpty()) {
                    error += ": $body"
                }
                return ApiResponse.Error(error)
            }
            return ApiResponse.Success(body())
        }
    } catch (e: Exception) {
        ApiResponse.Error(e.message ?: "Unknown error")
    }
}