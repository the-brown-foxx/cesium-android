package com.thebrownfoxx.models.auth

import kotlinx.serialization.Serializable

@Serializable
data class Hash(val value: Base64, val salt: Base64)