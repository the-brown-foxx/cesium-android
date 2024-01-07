package com.thebrownfoxx.cesium.data.api

import io.ktor.client.HttpClient
import io.ktor.client.engine.android.Android
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.logging.Logging
import io.ktor.serialization.kotlinx.json.json

fun newHttpClient() = HttpClient(Android) {
    install(Logging)
    install(ContentNegotiation) {
        json()
    }
}