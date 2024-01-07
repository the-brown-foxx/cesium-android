package com.thebrownfoxx.cesium.data.datastore

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import com.thebrownfoxx.models.auth.Jwt
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext

private val JWT_KEY = stringPreferencesKey("jwt")

val Context.jwt: Flow<Jwt?>
    get() = dataStore.data.map { it[JWT_KEY]?.let { jwtValue -> Jwt(jwtValue) } }

suspend fun Context.setJwt(jwt: Jwt) {
    withContext(Dispatchers.IO) {
        dataStore.edit { it[JWT_KEY] = jwt.value }
    }
}