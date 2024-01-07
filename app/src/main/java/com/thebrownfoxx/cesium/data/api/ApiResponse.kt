package com.thebrownfoxx.cesium.data.api

sealed class ApiResponse<T> {
    data class Success<T>(val data: T): ApiResponse<T>()
    data class Error<T>(val message: String): ApiResponse<T>()

    fun <R> map(transform: (T) -> R) = when (this) {
        is Success -> Success(transform(data))
        is Error -> Error(message)
    }
}