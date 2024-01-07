package com.thebrownfoxx.cesium.data.datastore

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext

private val BASE_URL_KEY = stringPreferencesKey("base_url")

val Context.baseUrl: Flow<String?>
    get() = dataStore.data.map { it[BASE_URL_KEY] }

suspend fun Context.setBaseUrl(ipAddress: String, port: Int) {
    withContext(Dispatchers.IO) {
        dataStore.edit { it[BASE_URL_KEY] = "http://$ipAddress:$port" }
    }
}